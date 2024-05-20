package es.beatkapo.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import es.beatkapo.app.model.Producto;
import es.beatkapo.app.response.ProductoResponse;
import es.beatkapo.app.service.GetProductoById;
import es.beatkapo.app.util.Utilidades;

public class ProductoActivity extends BaseActivity {
    private Producto producto;
    private int cantidad;
    private String idProducto;
    private ImageView imagen;
    private TextView nombre,precio,descripcion, cantidadProducto;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;

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
        nombre = findViewById(R.id.productName);
        precio = findViewById(R.id.productPrice);
        descripcion = findViewById(R.id.productDescription);
        cantidadProducto = findViewById(R.id.cantidad_textView);
        cantidadProducto.setText(String.valueOf(cantidad));
        imagen = findViewById(R.id.productImage);
        progressBar = findViewById(R.id.progressBar);
        scrollView = findViewById(R.id.nestedScrollView);
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
            setVisibility(false);
        }, error -> {
            Utilidades.showAlert(this, getString(R.string.internalErrorTitle), error.getMessage(), getString(R.string.accept), (d,w) ->{
                finish();
            }, null, null);
        });
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

}