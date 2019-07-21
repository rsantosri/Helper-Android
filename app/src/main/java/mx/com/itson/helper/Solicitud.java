package mx.com.itson.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.itson.helper.Modelos.Categoria;
import mx.com.itson.helper.Modelos.Oferta;
import mx.com.itson.helper.Modelos.Proveedor;
import mx.com.itson.helper.Modelos.RecyclerAdapterOferta;
import mx.com.itson.helper.Modelos.RecyclerAdapterProveedor;

public class Solicitud extends AppCompatActivity {


    FirebaseDatabase database;
    DatabaseReference myRef ;
    Proveedor proveedor;
    List<Oferta> list;
    RecyclerView recycle;

    TextView nombre, descripcionProveedor, costoPromedio;
    ImageView imagenProveedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud);
        final String idProveedor = getIntent().getStringExtra("id");

        nombre = findViewById(R.id.nombreProveedor);
        descripcionProveedor = findViewById(R.id.descripcionProveedor);
        costoPromedio = findViewById(R.id.costoProveedor);
        imagenProveedor = findViewById(R.id.imagenProveedor);

        recycle = (RecyclerView) findViewById(R.id.recyclerOfertas);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("proveedores");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                list = new ArrayList<Oferta>();
                for(DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){

                    Proveedor value = dataSnapshot1.getValue(Proveedor.class);
                    if (value.getIdUsuarioProveedor() == Integer.parseInt(idProveedor)){
                        proveedor = new Proveedor();
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

                        nombre.setText(proveedor.getNombre());
                        descripcionProveedor.setText(proveedor.getDescripcion());
                        costoPromedio.setText("" + proveedor.getCostoPromedio());
                        new DownLoadImageTask(imagenProveedor).execute(proveedor.getImagen());


                        for (int i = 0; i < proveedor.getOferta().size(); i++) {
                            if (proveedor.getOferta().get(i)!=null){
                                list.add(proveedor.getOferta().get(i));
                            }

                        }



                    }


                }

                RecyclerAdapterOferta recyclerAdapter = new RecyclerAdapterOferta(list,Solicitud.this);
                RecyclerView.LayoutManager recyce = new GridLayoutManager(Solicitud.this,1);
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

    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){
                e.printStackTrace();
            }
            return logo;
        }


        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }


    public void solicitarOnClick(View view){

        Intent intent = new Intent(this, Solicitud_Fecha.class);
        intent.putExtra("id",""+proveedor.getIdUsuarioProveedor());
        startActivity(intent);

    }
}
