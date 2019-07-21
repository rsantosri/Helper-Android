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
import mx.com.itson.helper.Categorias;
import mx.com.itson.helper.R;

public class RecyclerAdapterCategoria extends RecyclerView.Adapter<RecyclerAdapterCategoria.MyHolder>{

    List<Categoria> list;
    Context context;

    public RecyclerAdapterCategoria(List<Categoria> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerAdapterCategoria.MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.categoria_rows, viewGroup, false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    Button onclick;
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterCategoria.MyHolder myHolder, int i) {
        Categoria mylist = list.get(i);
        myHolder.id.setText("" + mylist.getId());
        new DownLoadImageTask(myHolder.image).execute(mylist.getImagen());
        myHolder.button.setText(mylist.getNombre());
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
        TextView id;
        ImageView image;
        Button button;


        public MyHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.idCategoria);
            image = (ImageView) itemView.findViewById(R.id.imgCategoria);
            button = (Button) itemView.findViewById(R.id.btnCategoria);
            context = itemView.getContext();
            button.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.btnCategoria){
                Intent intent = new Intent(context, Categorias.class);
                intent.putExtra("id",id.getText());
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

