package com.wtechweb.v2_update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText etName, etId, etCell;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        etName = findViewById(R.id.etName);
        etId = findViewById(R.id.etId);
        etCell = findViewById(R.id.etCell);
    }

    public void insertData(View v)
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("id",etId.getText().toString().trim());
        data.put("name", etName.getText().toString().trim());
        data.put("cell", etCell.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference()
                .child("Students")
                .push()
        .setValue(data)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Data added successfully.", Toast.LENGTH_SHORT).show();
                etCell.setText("");
                etName.setText("");
                etId.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void viewData(View v)
    {
        startActivity(new Intent(MainActivity.this, ViewData.class));
    }
}