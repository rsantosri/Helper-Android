package mx.com.itson.helper;

import android.os.Bundle;

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

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.itson.helper.Modelos.Categoria;
import mx.com.itson.helper.Modelos.RecyclerAdapterCategoria;

public class buscar extends Fragment {


    FirebaseDatabase database;
    DatabaseReference myRef ;
    List<Categoria> list;
    RecyclerView recycle;

    public buscar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buscar, container, false);
        recycle = (RecyclerView) view.findViewById(R.id.recyclerBuscar);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("categorias");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                list = new ArrayList<Categoria>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Categoria value = dataSnapshot1.getValue(Categoria.class);
                    Categoria categoria = new Categoria();
                    int id = value.getId();
                    String nombre = value.getNombre();
                    String imagen = value.getImagen();
                    categoria.setId(id);
                    categoria.setNombre(nombre);
                    categoria.setImagen(imagen);
                    list.add(categoria);

                }

                RecyclerAdapterCategoria recyclerAdapter = new RecyclerAdapterCategoria(list,getActivity());
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


        // Display the view
        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        ((Principal) getActivity()).setActionBarTitle(getString(R.string.title_search));


    }


}
