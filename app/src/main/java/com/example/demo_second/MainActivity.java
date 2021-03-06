package com.example.demo_second;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    // 1 - Link for popular movie
    private static String movie_db_url = "https://api.themoviedb.org/3/movie/popular?api_key=a7dab8c476fb31214a42e7867fa023b2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar =  findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ParseDBURL parseDBURL = new ParseDBURL();
        parseDBURL.execute(movie_db_url);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.menu_sesarch){
            Intent intent = new Intent(MainActivity.this, SearchMovies.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private class ParseDBURL extends AsyncTask<String, String, String>{
        ArrayList<MovieModel> movieModels = new ArrayList<>();
        ListView lv_movie = (ListView) findViewById(R.id.lv_Listview);
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
                Log.d("asdf", "doInBackground");
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
            //Bitmap bitmap = null;
            try {
                JSONObject obj = new JSONObject(s);
                JSONArray movies = obj.getJSONArray("results");

                for(int i=0; i< movies.length(); i++){
                    JSONObject object = movies.getJSONObject(i);

                    String title = object.getString("title");
                    String date = object.getString("release_date");
                    String img_path = img_path_url + object.getString("poster_path");

                    MovieModel model = new MovieModel();
                    model.setTitle(title);
                    model.setRelease_date(date);
                    model.setImg_path(img_path);
                    // Log.d("adf", img_path);
                    movieModels.add(model);

                }

                adapter adapter = new adapter(MainActivity.this, R.layout.movies_list, movieModels);
                lv_movie.setAdapter(adapter);
                Log.d("adf", "adapter");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

}