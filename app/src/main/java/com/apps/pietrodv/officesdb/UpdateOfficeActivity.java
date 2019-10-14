package com.apps.pietrodv.officesdb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateOfficeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Button loadAllMyOffices;

    @Override
    protected void onResume() {
        super.onResume();
        allOffices();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_office);

        // get the reference of RecyclerView
        recyclerView = findViewById(R.id.recycler_view);

        //set LinearLayoutManager to recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        allOffices();

        //Connect the Button to the view
        loadAllMyOffices = findViewById(R.id.load_all_my_offices);

        //Set onClick
        loadAllMyOffices.setOnClickListener((View v) -> {

                allOffices();

        });


    }

    public void allOffices(){

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        //The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call call = apiInterface.getAllOffices();
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                if (response.body() != null) {

                    List<Office> offices = (List<Office>) response.body();

                    //  call the constructor of OfficesAdapter to send the reference and data to Adapter
                    OfficesAdapter adapter = new OfficesAdapter(UpdateOfficeActivity.this, offices);

                    // set the Adapter to RecyclerView
                    recyclerView.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(@NonNull Call call, @NonNull Throwable t) {

                Toast.makeText(getApplicationContext(), "Error Call", Toast.LENGTH_SHORT).show();

            }
        });

    }



}
