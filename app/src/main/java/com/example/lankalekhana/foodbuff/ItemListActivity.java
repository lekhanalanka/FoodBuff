package com.example.lankalekhana.foodbuff;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.lankalekhana.foodbuff.Activities.IngredientsActivity;
import com.example.lankalekhana.foodbuff.ModelClasses.Ingredient;
import com.example.lankalekhana.foodbuff.ModelClasses.Step;
import com.example.lankalekhana.foodbuff.Widget.AppWidget;

import java.util.ArrayList;

public class ItemListActivity extends Activity {

    ArrayList<Step> stepArrayList;
    ArrayList<Ingredient> ingredientArrayList;
    RecyclerView ingredRecycle;
    private boolean mTwoPane;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        toolbar=findViewById(R.id.toolbar1);
        setActionBar(toolbar);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        stepArrayList = new ArrayList<>();
        ingredientArrayList = new ArrayList<>();

        ingredRecycle=findViewById(R.id.ingredient_recycler);

        stepArrayList = getIntent().getParcelableArrayListExtra("stepsList");
        ingredientArrayList = getIntent().getParcelableArrayListExtra("ingridents");

        if (findViewById(R.id.item_detail_container) != null) {

            mTwoPane = true;
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepArrayList, mTwoPane));
    }

    public void showIngredients(View view) {

        Intent intent=new Intent(this,IngredientsActivity.class);
        intent.putParcelableArrayListExtra("ingredientsList",ingredientArrayList);
        StringBuilder builder = new StringBuilder();
        String ingredients;
        for (int i=0;i<ingredientArrayList.size();i++)
        {
            String quantity = ingredientArrayList.get(i).getQuantity();
            String measure = ingredientArrayList.get(i).getMeasure();
            ingredients =getString(R.string.measure)+quantity.concat(" ").concat(measure);
            builder.append(getString(R.string.ingredient)+ingredientArrayList.get(i).getIngredient()+"\n"+ingredients+"\n");
        }
        sharedPreferences=getSharedPreferences("sharedPreference",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.putString("quantity",builder.toString());
        editor.commit();

        Intent i = new Intent(ItemListActivity.this, AppWidget.class);
        i.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int id[]= AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),AppWidget.class));
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,id);
        sendBroadcast(i);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final ArrayList<Step> mValues;
        private boolean mTwoPane;


        public SimpleItemRecyclerViewAdapter(ItemListActivity parent, ArrayList<Step> stepArrayList, boolean mTwoPane) {

            mValues = stepArrayList;
            mParentActivity = parent;
            this.mTwoPane = mTwoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.mContentView.setText(mValues.get(position).getShortDescription());
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mContentView;
            CardView cardView;

            ViewHolder(View view) {
                super(view);

                mContentView = view.findViewById(R.id.step_name_text_view);
                cardView=view.findViewById(R.id.card_video_list);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int pos = getAdapterPosition();
                        if(mTwoPane)
                        {
                            Bundle arguments = new Bundle();
                            arguments.putString("videoUrl",mValues.get(pos).getVideoURL());
                            arguments.putString("desc",mValues.get(pos).getDescription());
                            arguments.putString("thumbUrl",mValues.get(pos).getThumbnailURL());

                            ItemDetailFragment fragment = new ItemDetailFragment();
                            fragment.setArguments(arguments);

                            mParentActivity.getFragmentManager().beginTransaction().
                                    replace(R.id.item_detail_container,fragment).commit();
                        }

                        else {
                            Intent intent = new Intent(mParentActivity, ItemDetailActivity.class);
                            intent.putExtra("videoUrl",mValues.get(pos).getVideoURL());
                            intent.putExtra("desc",mValues.get(pos).getDescription());
                            intent.putExtra("thumbUrl",mValues.get(pos).getThumbnailURL());
                            intent.putParcelableArrayListExtra("stepslist",mValues);
                            intent.putExtra("position",pos);
                            mParentActivity.startActivity(intent);
                        }
                    }
                });
            }
        }
    }

}
