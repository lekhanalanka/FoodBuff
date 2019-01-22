package com.example.lankalekhana.foodbuff.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lankalekhana.foodbuff.Activities.IngredientsActivity;
import com.example.lankalekhana.foodbuff.ModelClasses.Ingredient;
import com.example.lankalekhana.foodbuff.R;

import java.util.ArrayList;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientHolder> {

    Context context;
    ArrayList<Ingredient> ingredientArrayList;

    public IngredientsAdapter(IngredientsActivity ingredientsActivity, ArrayList<Ingredient> ingredients) {

        this.context=ingredientsActivity;
        this.ingredientArrayList=ingredients;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new IngredientHolder(LayoutInflater.from(context).inflate(R.layout.ingred,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder ingredientHolder, int i) {

        String text= ingredientArrayList.get(i).getIngredient();
        ingredientHolder.ingName.setText("Ingredient: "+text+"\n"+"Quantity: " + ingredientArrayList.get(i).getQuantity()+ " " + ingredientArrayList.get(i).getMeasure() +"\n");

    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {

        TextView ingName;
        CardView card_ing;
        public IngredientHolder(@NonNull View itemView) {
            super(itemView);

            ingName=itemView.findViewById(R.id.ingName);
            card_ing=itemView.findViewById(R.id.card_ingre_list);
        }
    }
}
