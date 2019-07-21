package mx.com.itson.helper;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import mx.com.itson.helper.Modelos.Oferta;
import mx.com.itson.helper.Modelos.Proveedor;
import mx.com.itson.helper.Modelos.RecyclerAdapterOferta;
import mx.com.itson.helper.Modelos.Servicio;

public class Solicitud_Fecha extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {


    FirebaseDatabase database;
    DatabaseReference myRef ;
    Proveedor proveedor;
    Calendar calendar = Calendar.getInstance();
    TextView nombre, descripcionProveedor, fecha, hora;
    ImageView imagenProveedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitud__fecha);
        final String idProveedor = getIntent().getStringExtra("id");

        nombre = findViewById(R.id.nombreCrearSolicitud);
        descripcionProveedor = findViewById(R.id.descripcionCrearSolicitud);
        imagenProveedor = findViewById(R.id.imgCrearSolicitud);
        fecha = findViewById(R.id.lblFecha);
        hora = findViewById(R.id.lblHora);




        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("proveedores");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


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
                        new DownLoadImageTask(imagenProveedor).execute(proveedor.getImagen());






                    }


                }




            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Hello", "Failed to read value.", error.toException());
            }
        });

    }



    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = +dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
        if(calendar != null){
            calendar.set(year, monthOfYear, dayOfMonth);
        }
        fecha.setText(date);
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String time = hourOfDay+":"+minute;
        if (calendar != null){
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, second);


        }
        hora.setText(time);
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

    public void abrirDate(View view){
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                Solicitud_Fecha.this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );

        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }
    public void abrirTime(View view){
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(Solicitud_Fecha.this,
        now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), false);

        dpd.setVersion(TimePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
        dpd.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    public void registrarServicio(View view){
        database = FirebaseDatabase.getInstance();
        int r = new Random().nextInt(1000);
        myRef = database.getReference("servicios/" + r);
        EditText descripcionN = (EditText)findViewById(R.id.descripcionNecesidades);

        if (descripcionN.getText().toString().trim().isEmpty()){
            Toast.makeText(getApplicationContext(), "Escriba una descripción", Toast.LENGTH_SHORT).show();
        }
        else{
            Servicio servicio = new Servicio();
            servicio.setId(r);
            servicio.setDescripcionNecesidades(descripcionN.getText().toString().trim());
            servicio.setAceptado(false);
            servicio.setCompletado(false);
            servicio.setIdProveedor(proveedor.getIdUsuarioProveedor());
            servicio.setIdUsuarioCliente(2);


            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd:hh:mm");
            servicio.setFechaOrden(format1.format(Calendar.getInstance().getTime()));
            servicio.setFechaServicio(format1.format(calendar.getTime()));
            try {
                Task<Void> task = myRef.setValue(servicio);

                Toast.makeText(getApplicationContext(), "Se ha agregado su solicitud de servicio.", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, Principal.class);
                startActivity(intent);
                finish();
            } catch (Exception e){

            }



        }

    }
}
