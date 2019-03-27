package com.apps.pietrodv.workspaceofficesdb;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadOfficeActivity extends AppCompatActivity {

    //Context set for calendar
    final Context context = this;

    EditText inputTitle, inputDescription, inputPrice;
    //TextView inputDateFrom, inputDateTo;
    Button saveOffice, uploadImage;
    Toast fillAllFields, officeSaved, errorCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_office);

        inputTitle = findViewById(R.id.input_title);
        inputDescription = findViewById(R.id.input_description);
        inputPrice = findViewById(R.id.input_price);
        /*inputDateFrom = findViewById(R.id.input_date_from);
        inputDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateFrom();

            }
        });*/

        /*inputDateTo = findViewById(R.id.input_date_to);
        inputDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dateTo();

            }
        });*/

        //input photo
        uploadImage = findViewById(R.id.input_image);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addImage();

            }
        });

        saveOffice = findViewById(R.id.save_upload_office);
        saveOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateAdd(inputTitle,inputDescription,inputPrice)){

                    addOffice();

                }

            }
        });

    }

    public boolean validateAdd( EditText inputTitle, EditText inputDescription, EditText inputPrice){

        boolean result = false;

        if(!inputTitle.getText().toString().matches("") && !inputDescription.getText().toString().matches("") && !inputPrice.getText().toString().matches("")){

            result = true;

        } else {

            fillAllFields = Toast.makeText(getApplicationContext(), "Please fil in all fields", Toast.LENGTH_SHORT);
            fillAllFields.show();

        }

        return result;
    }

    private void dateFrom(){


    }

    private void dateTo(){


    }

    private void addImage(){

        Toast.makeText(getApplicationContext(),"Work In Progress", Toast.LENGTH_SHORT).show();

    }

    private void addOffice(){

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        //The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        //TODO: Missing the Image String(url) input
        Call call = apiInterface.addOffice(inputTitle.getText().toString(), inputDescription.getText().toString(), inputPrice.getText().toString());
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                officeSaved = Toast.makeText(getApplicationContext(), "Office Saved", Toast.LENGTH_SHORT);
                officeSaved.show();

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                errorCall = Toast.makeText(getApplicationContext(), "Error Call", Toast.LENGTH_SHORT);
                errorCall.show();

            }
        });

    }

}
