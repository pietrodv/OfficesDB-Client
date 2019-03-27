package com.apps.pietrodv.workspaceofficesdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class EditOfficeActivity extends AppCompatActivity {

    //create objects
    EditText editTitle, editDescription, editPrice;
    ImageView editPhoto;
    Button updateOffice, deleteOffice;
    Toast officeUpdated, officeDeleted, errorCall;
    Integer officeId;
    Office office;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_office);

        //connect objects to views
        editTitle = findViewById(R.id.edit_title);
        editDescription = findViewById(R.id.edit_description);
        editPrice = findViewById(R.id.edit_price);
        editPhoto = findViewById(R.id.edit_photo);

        officeUpdated = Toast.makeText(getApplicationContext(), "Office Updated", Toast.LENGTH_SHORT);
        officeDeleted = Toast.makeText(getApplicationContext(),"Office Deleted", Toast.LENGTH_SHORT);
        errorCall = Toast.makeText(getApplicationContext(), "Error Call", Toast.LENGTH_SHORT);

        updateOffice = findViewById(R.id.update_office);
        deleteOffice = findViewById(R.id.delete_office);

        //Get the item Intent from Adapter onClickListener
        office = (Office)getIntent().getExtras().getSerializable("OFFICE");
        editTitle.setText(office.getTitle());
        editDescription.setText(office.getDescription());
        editPrice.setText(office.getPrice());
        Picasso.get().load(office.getPhoto()).resize(180,120).into(editPhoto);
        officeId = office.getId();

        //Set the onClick methods for the buttons
        updateOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateOffice();

            }
        });

        deleteOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deleteOffice();

            }
        });

    }

    //UpdateOffice method
    public void updateOffice(){

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        //The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call call = apiInterface.updateOffice(officeId, editTitle.getText().toString(), editDescription.getText().toString(), editPrice.getText().toString());

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                officeUpdated.show();

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                errorCall.show();

            }
        });


    }

    //DeleteOffice method
    public void deleteOffice(){

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        //The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call call = apiInterface.deleteOfficeById(officeId);

        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {

                //Toast
                officeDeleted.show();

                //Clear all fields
                editTitle.getText().clear();
                editDescription.getText().clear();
                editPrice.getText().clear();

            }

            @Override
            public void onFailure(Call call, Throwable t) {

                //Toast
                errorCall.show();

            }
        });

    }


}
