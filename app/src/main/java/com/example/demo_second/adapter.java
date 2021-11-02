package com.example.demo_second;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class adapter extends ArrayAdapter<MovieModel> {

    private final Context context;
    private final int movie_layoutID;

    public adapter(@NonNull Context context, int movie_layoutID, @NonNull List<MovieModel> movies) {
        super(context, movie_layoutID, movies);

        this.context = context;
        this.movie_layoutID = movie_layoutID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(movie_layoutID, parent, false);

        }

        TextView tvTitle = convertView.findViewById(R.id.tv_title);
        TextView tvDate = convertView.findViewById(R.id.tv_release_date);
        ImageView ivCover = convertView.findViewById(R.id.iv_cover);

        MovieModel movieModel = getItem(position);

        tvTitle.setText(movieModel.getTitle());
        tvDate.setText(movieModel.getRelease_date());

        Glide.with(context).load(movieModel.getImg_path()).into(ivCover);


        return convertView;
    }
}
