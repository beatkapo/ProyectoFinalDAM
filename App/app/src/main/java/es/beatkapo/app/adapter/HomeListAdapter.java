package es.beatkapo.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.stream.Collectors;

import es.beatkapo.app.ProductoActivity;
import es.beatkapo.app.R;
import es.beatkapo.app.model.Ingrediente;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.model.Alergeno;
import es.beatkapo.app.response.ImageResponse;
import es.beatkapo.app.service.GetImage;
import es.beatkapo.app.util.Utilidades;

/**
 * Adapter para la lista de productos en la pantalla principal.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder> {
    private List<Producto> productos;
    private LayoutInflater inflater;
    private Context context;

    /**
     * Constructor para inicializar el adaptador con una lista de productos y el contexto.
     * @param productos Lista de productos.
     * @param context Contexto de la aplicación.
     */
    public HomeListAdapter(List<Producto> productos, Context context) {
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     * Infla el diseño para los elementos de la lista.
     * @param parent Vista padre.
     * @param viewType Tipo de vista.
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    @NonNull
    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.products_list_element, null);
        return new HomeListAdapter.ViewHolder(view);
    }

    /**
     * Vincula los datos del producto al ViewHolder.
     * @param holder ViewHolder.
     * @param position Posición del elemento en la lista.
     */
    @Override
    public void onBindViewHolder(@NonNull HomeListAdapter.ViewHolder holder, int position) {
        holder.bindData(productos.get(position));
    }

    /**
     * Devuelve la cantidad de productos en la lista.
     * @return Cantidad de productos.
     */
    @Override
    public int getItemCount() {
        return productos.size();
    }

    /**
     * ViewHolder para los elementos del RecyclerView.
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre, descripcion, precio;
        LinearLayout cardView;
        LinearLayout alergenosLayout;

        /**
         * Constructor para inicializar los componentes de la vista.
         * @param itemView Vista del elemento.
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.cardView_img);
            nombre = itemView.findViewById(R.id.cardView_title);
            descripcion = itemView.findViewById(R.id.cardView_description);
            precio = itemView.findViewById(R.id.cardView_price);
            cardView = itemView.findViewById(R.id.layoutCardview);
            alergenosLayout = itemView.findViewById(R.id.cardView_layoutAllergens);
        }

        /**
         * Vincula los datos del producto a los componentes de la vista.
         * @param producto Producto a mostrar.
         */
        public void bindData(Producto producto) {
            Log.e("HomeListAdapter", "Producto: " + producto.getNombre());
            nombre.setText(producto.getNombre());
            descripcion.setText(producto.getDescripcionCorta());

            // Formatear precio a 2 decimales
            String precioFormateado = String.format("%.2f€", producto.getPrecio());
            precio.setText(precioFormateado);

            // Cargar imagen del producto si está disponible
            if (producto.getImagen() != null) {
                Utilidades.cargarImagen(producto, imagen);
            }

            // Configurar el clic en la tarjeta para abrir la actividad del producto
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductoActivity.class);
                intent.putExtra("idProducto", producto.getId());
                context.startActivity(intent);
            });

            // Añadir un ImageView por cada alergeno en el layout alergenosLayout
            List<Ingrediente> ingredientes = producto.getIngredientes();
            List<Alergeno> alergenos = ingredientes.stream()
                    .map(Ingrediente::getAlergenos)
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
            for (Alergeno alergeno : alergenos) {
                ImageView imageView = new ImageView(context);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                int idImagen = Utilidades.getAlergenoImage(alergeno.getId());
                if (idImagen > -1) {
                    imageView.setImageResource(idImagen);
                    alergenosLayout.addView(imageView);
                }
            }
        }
    }
}
