package com.example.ruchita.popularmovies;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final String LOG_TAG=MainActivity.class.getSimpleName();
    private GridView mGridView;
    private ProgressBar mProgressBar;
    private GridViewDisplay mGridAdapter;
    private ArrayList<GridItem> mGridData;
    String api_key="INSERT API KEY HERE";
    String web="https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+api_key;
    String text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mGridView=(GridView) findViewById(R.id.gridView);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewDisplay(this, R.layout.grid_item_layout, mGridData);
        mGridView.setAdapter(mGridAdapter);

        new imagefetch().execute(web);
        mProgressBar.setVisibility(View.VISIBLE);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                GridItem item = (GridItem) parent.getItemAtPosition(position);


                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("image", item.getImage());
                intent.putExtra("details", item.getPlot());
                intent.putExtra("title", item.getTitle());
                intent.putExtra("release", item.getDate());
                intent.putExtra("vote", item.getVote());

                startActivity(intent);
            }
        });

    }

    public class imagefetch extends AsyncTask<String, Void, Integer> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String details;
        String movie="results";
        String base="http://image.tmdb.org/t/p/w500/";
        protected Integer doInBackground(String... params) {
            try {

                Uri builtUri = Uri.parse(params[0]).buildUpon().build();
                Log.v(builtUri.toString(),"h");
                URL url=new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {

                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {

                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    Log.e("Error at buffer "," ");
                    return null;
                }
                details = buffer.toString();
                JSONObject response = new JSONObject(details);
                JSONArray movielist = response.getJSONArray(movie);
                GridItem item[]=new GridItem[20];

                for (int i = 0; i < movielist.length(); i++)
                {
                    item[i] = new GridItem();
                    JSONObject details = movielist.getJSONObject(i);
                    String y=details.getString("poster_path");
                    text=details.getString("overview");
                    item[i].setPlot(text);
                    text=details.getString("title");
                    item[i].setTitle(text);
                    text=details.getString("vote_average");
                    item[i].setVote(text);
                    text=details.getString("release_date");
                    item[i].setDate(text);
                    y=base+y;
                    item[i].setImage(y);
                    mGridData.add(item[i]);
                }







            } catch (Exception e) {
                Log.e(LOG_TAG, "Error ", e);
                return 0;

            }
            return 1;

        }
        protected void onPostExecute(Integer result) {

            if (result == 1) {

                mGridAdapter.setGridData(mGridData);
            } else
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            mProgressBar.setVisibility(View.GONE);
        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            String update=prefs.getString("Sort","Popularity");
            Log.v(update,"hello");
            if(update.equals("Rating"))
            {
                mGridAdapter.clear();
                web="https://api.themoviedb.org/3/movie/top_rated?api_key="+api_key;
                new imagefetch().execute(web);
            }
            else
            {
                mGridAdapter.clear();
                web="https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key="+api_key;
                new imagefetch().execute(web);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}