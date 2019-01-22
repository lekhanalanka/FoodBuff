package com.example.lankalekhana.foodbuff.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.lankalekhana.foodbuff.Adapters.IngredientsAdapter;
import com.example.lankalekhana.foodbuff.ItemDetailActivity;
import com.example.lankalekhana.foodbuff.ModelClasses.Ingredient;
import com.example.lankalekhana.foodbuff.R;

import java.util.ArrayList;

public class IngredientsActivity extends AppCompatActivity {

    ArrayList<Ingredient> ingredients;
    RecyclerView recyclerView;
    IngredientsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);


        ingredients=new ArrayList<>();
        ingredients=getIntent().getParcelableArrayListExtra("ingredientsList");

        recyclerView=findViewById(R.id.ingredient_recycler);
        adapter=new IngredientsAdapter(this,ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemDetailActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
