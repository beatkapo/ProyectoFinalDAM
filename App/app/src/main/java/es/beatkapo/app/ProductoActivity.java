package es.beatkapo.app;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;
import java.util.stream.Collectors;

import es.beatkapo.app.model.Alergeno;
import es.beatkapo.app.model.Ingrediente;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.response.ImageResponse;
import es.beatkapo.app.response.ProductoResponse;
import es.beatkapo.app.service.GetImage;
import es.beatkapo.app.service.GetProductoById;
import es.beatkapo.app.util.Utilidades;

public class ProductoActivity extends BaseActivity {
    private Producto producto;
    private int cantidad;
    private String idProducto;
    private ImageView imagen;
    private TextView nombre,precio,descripcion, cantidadProducto, ingredientes;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private LinearLayout alergenosLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_product);
        super.onCreate(savedInstanceState);

        //initializeComponents();
    }

    public void initializeComponents() {
        super.initializeComponents();
        cantidad = 1;
        idProducto = getIntent().getStringExtra("idProducto");
        ingredientes = findViewById(R.id.ingredientesTextView);
        nombre = findViewById(R.id.productName);
        precio = findViewById(R.id.productPrice);
        descripcion = findViewById(R.id.productDescription);
        cantidadProducto = findViewById(R.id.cantidad_textView);
        cantidadProducto.setText(String.valueOf(cantidad));
        imagen = findViewById(R.id.productImage);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.nestedScrollView);
        alergenosLayout = findViewById(R.id.alergenosLayout);
        setVisibility(true);
        loadProducto();
    }

    private void loadProducto() {
        GetProductoById service = new GetProductoById();
        service.getProductoById(idProducto, response -> {
            ProductoResponse dto = (ProductoResponse) response;
            producto = dto.getProducto();
            nombre.setText(producto.getNombre());

            String precioString = String.format("%.2f€", producto.getPrecio());
            precio.setText(precioString);
            descripcion.setText(producto.getDescripcion());
            cantidadProducto.setText(String.valueOf(cantidad));
            rellenarAlergenos(alergenosLayout);
            rellenarIngredientes(producto);
            GetImage serviceImage = new GetImage();
            serviceImage.getImage(producto.getId(), responseImage -> {
                if (responseImage == null) {
                    // Mostrar mensaje de error
                    Log.e("GetImage", "ImageResponse is null");
                } else {
                    // Guardar Base64 en el producto
                    String image = ((ImageResponse) responseImage).getImage();
                    producto.setImagen(image);
                    Bitmap bitmap = Utilidades.base64ToBitmap(producto.getImagen(), 150, 150);
                    imagen.setImageBitmap(bitmap);
                }
                setVisibility(false);
            }, ex -> {
                // Mostrar mensaje de error
                Log.e("GetImage", "Error al cargar la imagen", ex);
            });

        }, error -> {
            Utilidades.showAlert(this, getString(R.string.internalErrorTitle), error.getMessage(), getString(R.string.accept), (d,w) ->{
                finish();
            }, null, null);
        });

    }

    private void rellenarIngredientes(Producto producto) {
        List<Ingrediente> ingredientesList = producto.getIngredientes();
        StringBuilder sb = new StringBuilder();
        for (Ingrediente ingrediente : ingredientesList) {
            //Separar los ingredientes por comas y si es el ultimo una "y"
            sb.append(ingrediente.getNombre());
            if(ingredientesList.indexOf(ingrediente)==ingredientesList.size()-1){
                sb.append(".");
            }else if (ingredientesList.indexOf(ingrediente)==ingredientesList.size()-2){
                sb.append(" y ");
            }else{
                sb.append(", ");
            }
        }
        ingredientes.setText(sb.toString());
    }

    private void setVisibility(boolean isLoading){
        if(isLoading){
            progressBar.setVisibility(ProgressBar.VISIBLE);
            scrollView.setVisibility(NestedScrollView.INVISIBLE);
        }else{
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            scrollView.setVisibility(NestedScrollView.VISIBLE);
        }
    }
    public void mas(View view){
        cantidad++;
        cantidadProducto.setText(String.valueOf(cantidad));
    }
    public void menos(View view){
        if(cantidad > 1){
            cantidad--;
            cantidadProducto.setText(String.valueOf(cantidad));
        }
    }
    public void addToCart(View view){
        pedido.addProducto(producto, cantidad);
        Utilidades.savePedido(pedido, this);
        cantidad = 1;
        cantidadProducto.setText(String.valueOf(cantidad));
        actualizarCantidadCarrito();
        Toast.makeText(this, getString(R.string.productAdded), Toast.LENGTH_SHORT).show();
    }

    private void rellenarAlergenos(LinearLayout alergenosLayout){
        //Añadir un ImageView por cada alergeno en el layout alergenosLayout
        List<Ingrediente> ingredientes = producto.getIngredientes();

        List<Alergeno> alergenos = ingredientes.stream()
                .map(Ingrediente::getAlergenos)
                .flatMap(List::stream)
                .collect(Collectors.toList());
        for (Alergeno alergeno : alergenos) {
            ImageView imageView = new ImageView(context);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            int idImagen = Utilidades.getAlergenoImage(alergeno.getId());
            if(idImagen > -1){
                imageView.setImageResource(idImagen);
                alergenosLayout.addView(imageView);
            }
        }
    }

}