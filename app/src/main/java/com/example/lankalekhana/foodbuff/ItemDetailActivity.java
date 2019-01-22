package com.example.lankalekhana.foodbuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.lankalekhana.foodbuff.ModelClasses.Step;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {

    String description,videoUrl,thumbUrl;
    ArrayList<Step> stepArrayList;
    int position;
    Button previous,next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        stepArrayList = new ArrayList<>();
        previous = findViewById(R.id.previous);
        next=findViewById(R.id.next);

        if (savedInstanceState == null) {

            description = getIntent().getStringExtra("desc");
            videoUrl = getIntent().getStringExtra("videoUrl");
            thumbUrl=getIntent().getStringExtra("thumbUrl");
            position = getIntent().getIntExtra("position",0);
            stepArrayList = getIntent().getParcelableArrayListExtra("stepslist");

            Bundle arguments = new Bundle();
            arguments.putString("videoUrl",videoUrl);
            arguments.putString("desc",description);
            arguments.putString("thumbUrl",thumbUrl);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.exo_frag, fragment)
                    .commit();
        }

        else
            {
            position = savedInstanceState.getInt("Pos");
            description = savedInstanceState.getString("desc");
            videoUrl = savedInstanceState.getString("videoUrl");
            thumbUrl=savedInstanceState.getString("thumbUrl");
            stepArrayList = savedInstanceState.getParcelableArrayList("stepslist");

                Bundle arguments = new Bundle();
                arguments.putString("videoUrl",videoUrl);
                arguments.putString("desc",description);
                arguments.putString("thumbUrl",thumbUrl);
                ItemDetailFragment fragment = new ItemDetailFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .replace(R.id.exo_frag, fragment)
                        .commit();

        }
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

    public void previousButton(View view) {

        next.setEnabled(true);
        if(position>=1)
        {
            position--;
            previous.setEnabled(true);

            videoUrl = stepArrayList.get(position).getVideoURL();
            description = stepArrayList.get(position).getDescription();
            thumbUrl=stepArrayList.get(position).getThumbnailURL();

            Bundle bundle = new Bundle();
            bundle.putString("videoUrl",videoUrl);
            bundle.putString("desc",description);
            bundle.putString("thumbUrl",thumbUrl);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.exo_frag, fragment)
                    .commit();
        }

        if (position==0)
        {
            previous.setEnabled(false);
        }
    }

    public void nextButton(View view) {

        previous.setEnabled(true);
        if(position<=stepArrayList.size())
        {
            position++;


            videoUrl = stepArrayList.get(position).getVideoURL();
            description = stepArrayList.get(position).getDescription();
            thumbUrl=stepArrayList.get(position).getThumbnailURL();

            Bundle bundle = new Bundle();
            bundle.putString("videoUrl",videoUrl);
            bundle.putString("desc",description);
            bundle.putString("thumbUrl",thumbUrl);
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(bundle);
            getFragmentManager().beginTransaction()
                    .replace(R.id.exo_frag, fragment)
                    .commit();

            if (position==stepArrayList.size()-1)
            {
                next.setEnabled(false);
            }

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Pos",position);
        outState.putString("videoUrl",videoUrl);
        outState.putString("desc",description);
        outState.putString("thumbUrl",thumbUrl);
        outState.putParcelableArrayList("stepslist",stepArrayList);
    }
}
