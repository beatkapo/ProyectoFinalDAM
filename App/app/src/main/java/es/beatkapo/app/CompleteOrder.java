package es.beatkapo.app;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import es.beatkapo.app.model.LineaPedido;

public class CompleteOrder extends BaseActivity{
    LinearLayout layoutProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complete_order);
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        layoutProductos = findViewById(R.id.layoutProductos);
        rellenarPedido();

    }

    private void rellenarPedido() {
        TextView numeroProductos = findViewById(R.id.numeroProductosTextView);

        numeroProductos.setText(pedido.getCantidadProductos() + " producto"+(pedido.getCantidadProductos()>1?"s":""));
        LayoutInflater inflater = LayoutInflater.from(this);
        for(LineaPedido l: pedido.getLineas()){
            View view = inflater.inflate(R.layout.linea_pedido, null);
            TextView nombre = view.findViewById(R.id.nombreLineaPedido);
            TextView cantidad = view.findViewById(R.id.cantidadLineaPedido);
            TextView precio = view.findViewById(R.id.precioLineaPedido);
            ImageView imagen = view.findViewById(R.id.imgLineaPedido);
            ImageButton mas = view.findViewById(R.id.masButtonLinea);
            ImageButton menos = view.findViewById(R.id.menosButtonLinea);
            nombre.setText(l.getProducto().getNombre());
            cantidad.setText("x"+ l.getCantidad());
            //Formatear el precio a 2 decimales
            precio.setText(String.format("%.2f", l.getPrecio()) + "€");
            //imagen.setImageResource(l.getProducto().getImagen());

            // Crear nuevos LayoutParams con los márgenes deseados
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMargins(10, 10, 10, 10);  // Establecer los márgenes (en píxeles)

            // Aplicar los LayoutParams a la vista
            view.setLayoutParams(layoutParams);
            layoutProductos.addView(view);
        }
    }
}