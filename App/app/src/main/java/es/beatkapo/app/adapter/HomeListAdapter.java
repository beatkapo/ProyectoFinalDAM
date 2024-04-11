package es.beatkapo.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.beatkapo.app.R;
import es.beatkapo.app.dto.ProductoDTO;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.util.Utilidades;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.ViewHolder>{
    private List<ProductoDTO> productos;
    private LayoutInflater inflater;
    private Context context;

    public HomeListAdapter(List<ProductoDTO> productos, Context context) {
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.cardView_img);
            nombre = itemView.findViewById(R.id.cardView_title);
            descripcion = itemView.findViewById(R.id.cardView_description);
            precio = itemView.findViewById(R.id.cardView_price);
        }

        public void bindData(ProductoDTO producto) {
            nombre.setText(producto.getNombre());
            descripcion.setText(producto.getDescripcionCorta());
            precio.setText(String.valueOf(producto.getPrecio()));
            if(producto.getImagen() != null){
                Bitmap bitmap = Utilidades.base64ToBitmap(producto.getImagen());
                imagen.setImageBitmap(bitmap);
            }
        }
    }
}
