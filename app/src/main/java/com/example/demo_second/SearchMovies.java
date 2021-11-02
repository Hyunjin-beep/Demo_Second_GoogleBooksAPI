package com.example.demo_second;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class SearchMovies extends AppCompatActivity {

    EditText et_search;
    Button btn_search;
    private static String movie_db_url = "https://api.themoviedb.org/3/search/movie?api_key=a7dab8c476fb31214a42e7867fa023b2&query=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movies);

        et_search = findViewById(R.id.et_search);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryString = et_search.getText().toString();
                Log.d("55", movie_db_url + et_search.getText().toString());

                SearchMovies.ParseDBURL parseDBURL = new SearchMovies.ParseDBURL();
                parseDBURL.execute(movie_db_url + queryString);
            }
        });
    }

    private class ParseDBURL extends AsyncTask<String, String, String> {
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        ListView lv_movie = findViewById(R.id.lv_Listview);
        String img_path_url = "https://image.tmdb.org/t/p/w500";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            StringBuilder current = new StringBuilder();
            HttpsURLConnection connection = null;
            InputStreamReader inputStreamReader;
            try{
                URL url = new URL(params[0]);
                connection = (HttpsURLConnection) url.openConnection();

                InputStream stream = connection.getInputStream();
                inputStreamReader = new InputStreamReader(stream);

                int data = inputStreamReader.read();
                while(data != -1){
                    current.append((char) data);
                    data = inputStreamReader.read();
                }
                return current.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                if(connection != null){
                    connection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject obj = new JSONObject(s);
                JSONArray movies = obj.getJSONArray("results");

                for(int i=0; i< movies.length(); i++){
                    JSONObject object = movies.getJSONObject(i);
                    MovieModel model = new MovieModel();
                    String title = object.getString("title");
                    String date = object.getString("release_date");
                    String img_path = img_path_url + object.getString("poster_path");

                    model.setTitle(title);
                    model.setRelease_date(date);
                    model.setImg_path(img_path);

                    movieModels.add(model);

                }

                adapter adapter = new adapter(SearchMovies.this, R.layout.movies_list, movieModels);
                lv_movie.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}