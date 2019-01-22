package com.example.lankalekhana.foodbuff.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lankalekhana.foodbuff.Activities.MainActivity;
import com.example.lankalekhana.foodbuff.ItemListActivity;
import com.example.lankalekhana.foodbuff.ModelClasses.Receipe;
import com.example.lankalekhana.foodbuff.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {

    Context context;
    List<Receipe> receipeList;
    private String servings;

    public RecipeAdapter(MainActivity mainActivity, List<Receipe> receipeArrayList) {

        this.context = mainActivity;
        this.receipeList = receipeArrayList;
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new RecipeHolder(LayoutInflater.from(context).inflate(R.layout.recipe_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int i) {

        recipeHolder.dish_tv.setText(receipeList.get(i).getName());
        servings = context.getString(R.string.servings) + " " + String.valueOf(receipeList.get(i).getServings());
        String imageUrl = receipeList.get(i).getImage();
        if (!imageUrl.equals("")) {
            Picasso.with(context).load(imageUrl).into(recipeHolder.imageView);
        }

        recipeHolder.servings_tv.setText(servings);

    }

    @Override
    public int getItemCount() {
        return receipeList.size();
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {

        TextView dish_tv,servings_tv;
        AppCompatImageView imageView;
        CardView cardView;

        public RecipeHolder(@NonNull View itemView) {
            super(itemView);

            dish_tv = itemView.findViewById(R.id.dish_text_view);
            servings_tv =itemView.findViewById(R.id.servings_text_view);
            imageView = itemView.findViewById(R.id.dish_image_view);
            cardView = itemView.findViewById(R.id.cardView);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Intent intent = new Intent(context, ItemListActivity.class);
                        intent.putParcelableArrayListExtra("stepsList", receipeList.get(pos).getSteps());
                        intent.putParcelableArrayListExtra("ingridents", receipeList.get(pos).getIngredients());
                        context.startActivity(intent);
                        //Toast.makeText(context, ""+receipeList.get(pos).getSteps(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}
