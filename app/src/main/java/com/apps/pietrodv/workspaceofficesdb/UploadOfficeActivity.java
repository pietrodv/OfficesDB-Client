package com.apps.pietrodv.workspaceofficesdb;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.stream.IntStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UploadOfficeActivity extends AppCompatActivity {

    private static int SELECT_PICTURE = 1;
    //Context set for calendar
    final Context context = this;

    EditText inputTitle, inputDescription, inputPrice;
    //TextView inputDateFrom, inputDateTo;
    Button saveOffice, inputPhoto, uploadPhoto;
    Toast fillAllFields, officeSaved, errorCall;
    ImageView showPhoto;
    String keyname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_office);

        inputTitle = findViewById(R.id.input_title);
        inputDescription = findViewById(R.id.input_description);
        inputPrice = findViewById(R.id.input_price);
        showPhoto = findViewById(R.id.show_photo);

        //input photo
        inputPhoto = findViewById(R.id.input_photo);
        inputPhoto.setOnClickListener(new View.OnClickListener() {
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
                    keyname = inputTitle.getText().toString().toLowerCase().replaceAll("\\s+","") + ".jpg";
                    uploadPhotoToS3();
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

    private void addImage(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {

                //Get ImageURi and load with help of picasso
                Uri selectedImageURI = data.getData();
                Picasso.get().load(selectedImageURI).resize(180,120).into(showPhoto);
            }

        }
    }

    private void uploadPhotoToS3() {
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        // Photo from ImageView to byte[]
        Bitmap bitmap = ((BitmapDrawable) showPhoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageInByte = baos.toByteArray();

        // Create a request body with file and image media type
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), imageInByte);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part file = MultipartBody.Part.createFormData("uploadfile", "photo", fileReqBody);
        //Create request body with text description and text media type
        //RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call call = apiInterface.uploadMultipartFile(keyname, file);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.v("Upload", "success");
                Toast.makeText(getApplicationContext(), "Photo Uploaded to Server", Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
                Toast.makeText(getApplicationContext(), "Error Uploading Photo", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void addOffice(){

        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();

        //The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        // path to S3: https://{bucket-name}.s3.{region}.amazonaws.com/{file-name}
        Call call = apiInterface.addOffice(inputTitle.getText().toString(), inputDescription.getText().toString(), inputPrice.getText().toString(), keyname );
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
