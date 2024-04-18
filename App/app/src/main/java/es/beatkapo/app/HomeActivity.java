package es.beatkapo.app;

import static es.beatkapo.app.util.Utilidades.ADMIN;
import static es.beatkapo.app.util.Utilidades.HOME;
import static es.beatkapo.app.util.Utilidades.OPINIONS;
import static es.beatkapo.app.util.Utilidades.ORDERS;
import static es.beatkapo.app.util.Utilidades.SETTINGS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.stream.Collectors;

import es.beatkapo.app.adapter.HomeListAdapter;
import es.beatkapo.app.model.Producto;
import es.beatkapo.app.model.Categoria;
import es.beatkapo.app.model.TipoUsuario;
import es.beatkapo.app.model.Usuario;
import es.beatkapo.app.response.CategoriasResponse;
import es.beatkapo.app.response.ProductosResponse;
import es.beatkapo.app.response.UsuarioResponse;
import es.beatkapo.app.service.GetAccount;
import es.beatkapo.app.service.GetCategoriasService;
import es.beatkapo.app.service.GetProductosService;
import es.beatkapo.app.util.Utilidades;

public class HomeActivity extends BaseActivity{

    TextView name, email;
    private ImageButton btnMenu;
    private LinearLayout homeLayout;

    private List<Categoria> categorias;
    private List<Producto> productos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
        //initializeComponents();
    }
    @Override
    protected void initializeComponents() {
        btnMenu = findViewById(R.id.menuButton_home);

        homeLayout = findViewById(R.id.homeLayout);

        loadData();
        super.initializeComponents();

        Log.e("HomeActivity", "Data loaded");
    }



    private void loadData(){
        GetProductosService service = new GetProductosService();
        service.getProductos(response -> {
            if(response == null){
                // Mostrar mensaje de error
                Log.e("HomeActivity", "Response is null");
            }else{
                // Cargar los productos en la vista

                productos = ((ProductosResponse) response).getProductos();
                loadCategories();
            }
        }, ex -> {
            // Mostrar mensaje de error
            Log.e("HomeActivity", "Error al cargar los productos", ex);
        });
    }
    private void loadCategories() {
        GetCategoriasService service = new GetCategoriasService();
        service.getCategorias(response -> {
            if(response == null) {
                // Mostrar mensaje de error
                Log.e("HomeActivity", "Response is null");

            }else {
                categorias = ((CategoriasResponse) response).getCategorias();
                // Cargar las categorías en la vista

                addRecyclerViews();
            }
        }, ex -> {
            // Mostrar mensaje de error
            Log.e("HomeActivity", "Error al cargar las categorías", ex);
        });
    }

    private void addRecyclerViews() {
        // Añadir los recyclerviews a la vista
        for (Categoria categoria : categorias) {
            // Crear un recyclerview y un textview por cada categoría
            TextView textView = new TextView(this);
            textView.setText(categoria.getNombre());
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setTextSize(20);
            homeLayout.addView(textView);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this);

            RecyclerView recyclerView = new RecyclerView(this);
            recyclerView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            List<Producto> productosCategoria = productos.stream()
                    .filter(producto -> producto.getCategoria().getId().equals(categoria.getId()))
                    .collect(Collectors.toList());
            HomeListAdapter adapter = new HomeListAdapter(productosCategoria, this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(layoutManager);
            homeLayout.addView(recyclerView);
            adapter.notifyDataSetChanged();


        }
    }





}