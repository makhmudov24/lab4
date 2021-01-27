package com.example.lab4;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private static final int DEFAULT_POSITION = -1;

    TextView alsoKnowAs;
    TextView description;
    TextView placeOfOrigin;
    TextView ingredients;
    TextView sandwichName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        alsoKnowAs = (TextView) findViewById(R.id.also_known_tv);
        description = (TextView) findViewById(R.id.description_tv);
        placeOfOrigin = (TextView) findViewById(R.id.origin_tv);
        ingredients = (TextView) findViewById(R.id.ingredients_tv);
        sandwichName = (TextView) findViewById(R.id.sanwich_name_tv);

        Intent intent = getIntent();

        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra("ARG_ITEM_POSITION", DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {

            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String json = sandwiches[position];
        Sandwich sandwich = gson.fromJson(json, Sandwich.class);

        sandwichName.setText(MainActivity.sandwiches[position]);
        alsoKnowAs.setText(sandwich.getAlsoKnownAs());
        description.setText(sandwich.getDescription());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        ingredients.setText(sandwich.getIngredients());

        Log.i("INFO",  "Место " + sandwich.getPlaceOfOrigin() +  " Название "
                + sandwich.getMainName() + " Описание " + sandwich.getDescription()
                + " Как ещё называется " + sandwich.getAlsoKnownAs()
                + " Ингридиенты " + sandwich.getIngredients());

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}