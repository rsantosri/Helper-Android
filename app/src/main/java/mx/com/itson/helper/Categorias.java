package mx.com.itson.helper;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.itson.helper.Modelos.Categoria;
import mx.com.itson.helper.Modelos.Proveedor;
import mx.com.itson.helper.Modelos.RecyclerAdapterCategoria;
import mx.com.itson.helper.Modelos.RecyclerAdapterProveedor;

public class Categorias extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Proveedor> list;
    RecyclerView recycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorias);
        final String categoria = getIntent().getStringExtra("id");



        recycle = (RecyclerView) findViewById(R.id.recyclerCategoria);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("proveedores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                list = new ArrayList<Proveedor>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Proveedor value = dataSnapshot1.getValue(Proveedor.class);
                    if (value.getIdCategoria() == Integer.parseInt(categoria)){
                        Proveedor proveedor = new Proveedor();
                        int id = value.getIdUsuarioProveedor();
                        String descripcion = value.getDescripcion();
                        String imagen = value.getImagen();
                        double costo = value.getCostoPromedio();

                        proveedor.setIdUsuarioProveedor(id);
                        proveedor.setDescripcion(descripcion);
                        proveedor.setImagen(imagen);
                        proveedor.setCostoPromedio(costo);
                        proveedor.setIdCategoria(value.getIdCategoria());
                        proveedor.setOferta(value.getOferta());
                        proveedor.setNombre(value.getNombre());
                        list.add(proveedor);
                    }


                }

                RecyclerAdapterProveedor recyclerAdapter = new RecyclerAdapterProveedor(list,Categorias.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(Categorias.this,1);
                /// RecyclerView.LayoutManager recyce = new LinearLayoutManager(MainActivity.this);
                // recycle.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                recycle.setLayoutManager(recyce);
                recycle.setItemAnimator( new DefaultItemAnimator());
                recycle.setAdapter(recyclerAdapter);


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });




    }

}
