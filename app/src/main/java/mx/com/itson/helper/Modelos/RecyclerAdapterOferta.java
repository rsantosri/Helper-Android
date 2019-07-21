package mx.com.itson.helper.Modelos;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import mx.com.itson.helper.R;
import mx.com.itson.helper.Solicitud;

public class RecyclerAdapterOferta extends RecyclerView.Adapter<RecyclerAdapterOferta.MyHolder>{


    List<Oferta> list;
    Context context;

    public RecyclerAdapterOferta(List<Oferta> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerAdapterOferta.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.oferta_rows, viewGroup, false);
        RecyclerAdapterOferta.MyHolder myHolder = new RecyclerAdapterOferta.MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterOferta.MyHolder myHolder, int i) {
        Oferta mylist = list.get(i);
        myHolder.nombre.setText("" + mylist.getNombre());
        myHolder.costo.setText("" + mylist.getCosto());
        new RecyclerAdapterOferta.DownLoadImageTask(myHolder.image).execute(mylist.getImagen());
    }

    @Override
    public int getItemCount() {
        int arr = 0;

        try {
            if (list.size() == 0) {

                arr = 0;

            } else {

                arr = list.size();
            }

        } catch (Exception e) {


        }

        return arr;

    }




    class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView nombre, costo;
        ImageView image;
        Button button;


        public MyHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.nombreOferta);
            costo = (TextView) itemView.findViewById(R.id.costoOferta);
            image = (ImageView) itemView.findViewById(R.id.imagenOferta);
            context = itemView.getContext();

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnProveedor){
                Intent intent = new Intent(context, Solicitud.class);
                context.startActivity(intent);
            }
        }
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
}
