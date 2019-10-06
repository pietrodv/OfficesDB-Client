package com.apps.pietrodv.officesdb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button uploadOffice;
        Button updateOffice;

        uploadOffice = findViewById(R.id.upload_office);
        updateOffice = findViewById(R.id.update_office);

        uploadOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUpload = new Intent(getApplicationContext(), UploadOfficeActivity.class );
                startActivity(goToUpload);
            }
        });

        updateOffice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToUpdate = new Intent(getApplicationContext(), UpdateOfficeActivity.class );
                startActivity(goToUpdate);
            }
        });
    }
}
