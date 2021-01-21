package com.example.lab8;

import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class AddPayment extends AppCompatActivity {

    private TextView cost, name, type;
    private Button button;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        cost = findViewById(R.id.costView);
        name = findViewById(R.id.nameView);
        type = findViewById(R.id.typeView);
        button = findViewById(R.id.saveButton);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                updateDb();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void updateDb() {
        if (cost.getText().toString().isEmpty() || name.getText().toString().isEmpty() || type.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT);
        } else {
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();

            String time = sdfDate.format(now);
            Double c = Double.parseDouble(cost.getText().toString());
            String n = name.getText().toString();
            String t = type.getText().toString();
            databaseReference.child("wallet").child(time).setValue(new Payment(c, n, t, time));
            this.finish();
        }
    }
}
