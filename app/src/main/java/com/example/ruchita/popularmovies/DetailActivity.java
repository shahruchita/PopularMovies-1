package com.example.ruchita.popularmovies;

/**
 * Created by ruchita on 9/5/16.
 */

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ruchita.popularmovies.R;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private TextView plot;

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String title=getIntent().getStringExtra("title");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);


        String plots = getIntent().getStringExtra("details");
        String image = getIntent().getStringExtra("image");
        String urating = getIntent().getStringExtra("vote");
        String releasedate = getIntent().getStringExtra("release");
        plot = (TextView) findViewById(R.id.plot);
        imageView = (ImageView) findViewById(R.id.grid_item_image);
        plots=" RELEASE DATE  "+releasedate+"\n USER RATING    "+urating+"\n PLOT \n"+plots;


        Picasso.with(this).load(image).into(imageView);
        plot.setText(plots);
    }
}