package com.example.lankalekhana.foodbuff.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lankalekhana.foodbuff.Adapters.RecipeAdapter;
import com.example.lankalekhana.foodbuff.ModelClasses.Receipe;
import com.example.lankalekhana.foodbuff.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    RecyclerView recyclerView;
    List<Receipe> receipeArrayList;
    ProgressDialog dialog;
    String Url ="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()){
            getSupportLoaderManager().initLoader(1,null,this);

        }
        else {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Error");
            builder.setMessage("\n No Internet Connection...");
            builder.setCancelable(false);
            builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.show();

        }

        recyclerView = findViewById(R.id.recylerView);
        dialog=new ProgressDialog(this);

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
                dialog.setTitle("Please Wait...");
                dialog.setMessage("Loading");
                dialog.setCancelable(false);
                dialog.show();
            }

            @Nullable
            @Override
            public String loadInBackground() {


                try {
                    URL url = new URL(Url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.connect();
                    InputStream inputStream = httpURLConnection.getInputStream();

                    BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String li="";
                    while ((li=bufferedReader.readLine())!=null)
                    {
                        builder.append(li);
                    }
                    return builder.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

        dialog.dismiss();
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        receipeArrayList = new ArrayList<Receipe>();
        receipeArrayList =  Arrays.asList(gson.fromJson(s, Receipe[].class));

        RecipeAdapter receipAdapter=new RecipeAdapter(MainActivity.this,receipeArrayList);
        recyclerView.setAdapter(receipAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }


    }


