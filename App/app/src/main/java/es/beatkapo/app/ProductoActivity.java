package es.beatkapo.app;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import es.beatkapo.app.model.Producto;
import es.beatkapo.app.response.ProductoResponse;
import es.beatkapo.app.service.GetProductoById;
import es.beatkapo.app.util.Utilidades;

public class ProductoActivity extends AppCompatActivity {
    private Producto producto;
    private int cantidad;
    private String idProducto;
    private ImageView imagen;
    private TextView nombre,precio,descripcion, cantidadProducto;
    private ProgressBar progressBar;
    private NestedScrollView scrollView;
    private FrameLayout frameLayout;
    private ExtendedFloatingActionButton floatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.coordinatorLayout_producto), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializeComponents();
    }

    private void initializeComponents() {
        idProducto = getIntent().getStringExtra("idProducto");
        nombre = findViewById(R.id.productName);
        precio = findViewById(R.id.productPrice);
        descripcion = findViewById(R.id.productDescription);
        cantidadProducto = findViewById(R.id.cantidad_textView);
        imagen = findViewById(R.id.productImage);
        progressBar = findViewById(R.id.progressBarProduto);
        scrollView = findViewById(R.id.scrollViewProducto);
        frameLayout = findViewById(R.id.frameLayoutProducto);
        floatingButton = findViewById(R.id.addToCartButton);
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
            frameLayout.setVisibility(FrameLayout.INVISIBLE);
            floatingButton.setVisibility(ExtendedFloatingActionButton.INVISIBLE);
        }else{
            progressBar.setVisibility(ProgressBar.INVISIBLE);
            scrollView.setVisibility(NestedScrollView.VISIBLE);
            frameLayout.setVisibility(FrameLayout.VISIBLE);
            floatingButton.setVisibility(ExtendedFloatingActionButton.VISIBLE);
        }
    }

}