package es.beatkapo.app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import es.beatkapo.app.model.Ingrediente;
import es.beatkapo.app.model.LineaPedido;
import es.beatkapo.app.model.Pedido;
import es.beatkapo.app.response.PedidosResponse;
import es.beatkapo.app.service.GetPedidos;

public class OrdersActivity extends BaseActivity{
    private LinearLayout ordersLayout, noOrdersLayout;
    private List<Pedido> pedidos;
    LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_orders);
        super.onCreate(savedInstanceState);

    }
    @Override
    protected void initializeComponents() {
        super.initializeComponents();
        ordersLayout = findViewById(R.id.ordersLayout);
        noOrdersLayout = findViewById(R.id.noOrdersLayout);
        obtenerPedidos();
    }
    private void obtenerPedidos(){
        Log.e("OrdersActivity", "Obteniendo pedidos");
        GetPedidos getPedidos = new GetPedidos();
        getPedidos.getProductos(response -> {
            Log.e("OrdersActivity", "Respuesta obtenida");
            if(response ==null){
                Log.e("OrdersActivity", "Error al obtener pedidos. Response is null");
            }else{
                Log.e("OrdersActivity", "Pedidos obtenidos correctamente");
                PedidosResponse pedidosResponse = (PedidosResponse) response;
                pedidos = pedidosResponse.getPedidos();
                if(pedidos.isEmpty()){
                    Log.e("OrdersActivity", "No hay pedidos");
                    setVisibility(false);
                }else{
                    Log.e("OrdersActivity", "Hay pedidos");
                    cargarPedidos();
                    setVisibility(true);
                }
            }
        }, ex ->{
            Log.e("OrdersActivity", "Error al obtener pedidos. Failed");
            ex.printStackTrace();
        });
    }

    private void cargarPedidos() {
        Log.e("OrdersActivity", "Cargando pedidos");
        inflater = LayoutInflater.from(this);
        for(Pedido pedido: pedidos){
            View orderView = inflater.inflate(R.layout.order_item, null);
            TextView orderName = orderView.findViewById(R.id.nombrePedido);
            TextView orderDate = orderView.findViewById(R.id.fechaPedido);
            TextView orderPrice = orderView.findViewById(R.id.precioPedido);
            ImageView orderImage = orderView.findViewById(R.id.imgPedido);
            StringBuilder sb = new StringBuilder();
            List<LineaPedido> lineas = pedido.getLineas();
            for (LineaPedido linea: lineas){
                sb.append("x"+linea.getCantidad()+" "+linea.getProducto().getNombre());
                if(lineas.indexOf(linea)==lineas.size()-1){
                    sb.append(".");
                }else if (lineas.indexOf(linea)==lineas.size()-2){
                    sb.append(" y ");
                }else{
                    sb.append(", ");
                }
            }
            orderName.setText(sb.toString());
            orderDate.setText(pedido.getFecha());
            orderPrice.setText(String.format("%.2f€", pedido.getTotal()));
            // orderImage.setImageResource(Utilidades.getImagenPedido(pedido);
            orderView.setOnClickListener(v -> {
                //Intent intent = new Intent(this, OrderDetailActivity.class);
                //intent.putExtra("pedidoSelected", pedido);
                //startActivity(intent);
            });
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 5, 5, 5);
            orderView.setLayoutParams(params);
            ordersLayout.addView(orderView);
        }
    }

    private void setVisibility(boolean hasOrders) {// Tiene pedidos?
        if(hasOrders){//Si tiene pedidos
            ordersLayout.setVisibility(LinearLayout.VISIBLE);
            noOrdersLayout.setVisibility(LinearLayout.GONE);
        }else{//Si no tiene pedidos
            ordersLayout.setVisibility(LinearLayout.GONE);
            noOrdersLayout.setVisibility(LinearLayout.VISIBLE);
        }
    }
}