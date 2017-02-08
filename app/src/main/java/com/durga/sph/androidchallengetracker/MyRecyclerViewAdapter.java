package com.durga.sph.androidchallengetracker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.durga.sph.androidchallengetracker.orm.TrackerQuestion;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by root on 12/28/16.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context m_context;
    List<TrackerQuestion> my_recipesList;

    public MyRecyclerViewAdapter(Context context, List<TrackerQuestion> recipesList){
        m_context = context;
        my_recipesList = recipesList;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView m_textView;
        ImageView m_imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            m_textView = (TextView) itemView.findViewById(R.id.textView);
            m_imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int type)
    {
        View view = LayoutInflater.from(m_context).inflate(R.layout.cell_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final TrackerQuestion recipe = my_recipesList.get(position);
        holder.m_textView.setText(recipe.title);
        //new loadBimapTask(new WeakReference<Context>(m_context), holder.m_imageView).execute("http://www.tomopop.com/ul/16769-550x-header.jpg");
    }

    public int getItemCount() {
        return my_recipesList != null ? my_recipesList.size() : 0;
    }

    public void updateAdapter(List<TrackerQuestion> questions){
        my_recipesList.addAll(questions);
        notifyDataSetChanged();
    }

    public class loadBimapTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView imgView;
        // WeakReference<Context> contextRef;
        Context context;
        public loadBimapTask(WeakReference<Context> contextRef, ImageView imageView){
            imgView = imageView;
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... paths) {
            URL url = null;
            Bitmap bitmap = null;
            try {
                url = new URL(paths[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                connection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // if(contextRef.get() != null && imgView != null && bitmap != null) {
            if(context != null && imgView != null && bitmap != null) {
                imgView.setImageBitmap(bitmap);
            }
        }
    }
}
