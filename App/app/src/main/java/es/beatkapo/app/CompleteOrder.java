package es.beatkapo.app;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.response.Response;
import es.beatkapo.app.service.SendOrder;
import es.beatkapo.app.util.Utilidades;

public class CompleteOrder extends BaseActivity{
    LinearLayout layoutProductos;
    Button completeOrderButton;
    EditText direccion;
    CheckBox card, cash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_complete_order);
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        layoutProductos = findViewById(R.id.layoutProductos);
        completeOrderButton = findViewById(R.id.completeOrderButton);
        direccion = findViewById(R.id.direccionEditText);
        direccion.setText(user.getDireccion1()==null?"":user.getDireccion1());
        card = findViewById(R.id.cardCheck);
        cash = findViewById(R.id.cashCheck);
        rellenarPedido();

    }
    public void setVisibility(boolean visible){

    }
    private void rellenarPedido() {
        TextView numeroProductos = findViewById(R.id.numeroProductosTextView);

        numeroProductos.setText(pedido.getCantidadProductos() + " producto"+(pedido.getCantidadProductos()==1?"":"s"));
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
            mas.setOnClickListener(v -> {
                l.setCantidad(l.getCantidad()+1);
                int nCantidad = pedido.getCantidadProductos();
                cantidad.setText("x"+ l.getCantidad());
                precio.setText(String.format("%.2f", l.getPrecio()) + "€");
                numeroProductos.setText(nCantidad + " producto"+(nCantidad==1?"":"s"));
            });
            menos.setOnClickListener(v -> {
                if(l.getCantidad()>1){
                    pedido.removeLinea(l);
                    l.setCantidad(l.getCantidad()-1);
                    pedido.addProducto(l.getProducto(), l.getCantidad());
                    int cantidadProductos = pedido.getCantidadProductos();
                    cantidad.setText("x"+ l.getCantidad());
                    precio.setText(String.format("%.2f", l.getPrecio()) + "€");
                    numeroProductos.setText(cantidadProductos + " producto"+(pedido.getCantidadProductos()==1?"":"s"));
                    Utilidades.savePedido(pedido, this);
                }else{
                    pedido.removeLinea(l);
                    layoutProductos.removeView(view);
                    numeroProductos.setText(pedido.getCantidadProductos() + " producto"+(pedido.getCantidadProductos()==1?"":"s"));
                }
            });
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

    public void completeOrder(View view){
        //Verificar los campos
        if(fieldsVerified()){
            pedido.setDireccion(direccion.getText().toString());
            SendOrder sendOrder = new SendOrder();

            sendOrder.sendOrder(pedido, response -> {
                if (response == null) {
                    // Mostrar mensaje de error
                    Log.e("CompleteOrder", "Response is null");
                    Utilidades.showAlert(this, getString(R.string.internalErrorTitle), getString(R.string.responseError_login), getString(R.string.accept), (d, w) -> {
                        finish();
                    }, null, null);
                } else {
                    Response r = (Response) response;
                    if(r.isError()){
                        // Mostrar mensaje de error
                        Utilidades.showAlert(this, getString(R.string.internalErrorTitle), r.getMessage(), getString(R.string.accept), (d, w) -> {
                            finish();
                        }, null, null);
                        return;
                    }else{
                        // Mostrar mensaje de éxito
                        Utilidades.showAlert(this, getString(R.string.orderSuccessTitle), getString(R.string.orderSuccessMessage), getString(R.string.accept), (d, w) -> {
                            pedido = new Pedido();
                            Utilidades.savePedido(pedido, this);
                            finish();

                        }, null, null);
                    }
                }
            }, error -> {
                System.out.println("Error al enviar el pedido");
            });
        }else{
            Utilidades.showAlert(this, getString(R.string.errorCompleteOrderTitle), getString(R.string.errorCompleteOrder), getString(R.string.accept), null, null, null);
        }


    }

    private boolean fieldsVerified() {
        if(direccion.getText().toString().isEmpty()){
            return false;
        }
        if(!card.isChecked() && !cash.isChecked()){
            return false;
        }
        return true;
    }
}