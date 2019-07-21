package mx.com.itson.helper;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.itson.helper.Modelos.Proveedor;
import mx.com.itson.helper.Modelos.RecyclerAdapterProveedor;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class home extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Proveedor> list;
    RecyclerView recycle;


    public home() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recycle = (RecyclerView) view.findViewById(R.id.recyclerHome);
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

                RecyclerAdapterProveedor recyclerAdapter = new RecyclerAdapterProveedor(list,getActivity());
                RecyclerView.LayoutManager recyce = new GridLayoutManager(getActivity(),1);
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

        return view;

    }

    @Override
    public void onResume(){
        super.onResume();
        ((Principal) getActivity()).setActionBarTitle(getString(R.string.app_name));
    }
}
