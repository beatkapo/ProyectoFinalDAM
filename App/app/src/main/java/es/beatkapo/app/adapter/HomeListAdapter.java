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
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.model.Alergeno;
import es.beatkapo.app.util.Utilidades;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{
    private List<Producto> productos;
    private LayoutInflater inflater;
    private Context context;

    public HomeListAdapter(List<Producto> productos, Context context) {
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public HomeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_element, null);
        return new HomeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeListAdapter.ViewHolder holder, int position) {
        holder.bindData(productos.get(position));
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imagen;
        TextView nombre, descripcion, precio;
        LinearLayout cardView;
        LinearLayout alergenosLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.cardView_img);
            nombre = itemView.findViewById(R.id.cardView_title);
            descripcion = itemView.findViewById(R.id.cardView_description);
            precio = itemView.findViewById(R.id.cardView_price);
            cardView = itemView.findViewById(R.id.layoutCardview);
            alergenosLayout = itemView.findViewById(R.id.cardView_layoutAllergens);
        }

        public void bindData(Producto producto) {
            Log.e("HomeListAdapter", "Producto: " + producto.getNombre());
            nombre.setText(producto.getNombre());
            descripcion.setText(producto.getDescripcionCorta());
            precio.setText(String.valueOf(producto.getPrecio()));
            if(producto.getImagen() != null){
                Bitmap bitmap = Utilidades.base64ToBitmap(producto.getImagen());
                imagen.setImageBitmap(bitmap);
            }
            cardView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductoActivity.class);
                intent.putExtra("idProducto", producto.getId());
                context.startActivity(intent);
            });
            //Añadir un ImageView por cada alergeno en el layout alergenosLayout
            List<Alergeno> alergenos = producto.getIngredientes().stream()
                    .map(ingrediente -> ingrediente.getAlergenos())
                    .flatMap(List::stream)
                    .distinct()
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
}
