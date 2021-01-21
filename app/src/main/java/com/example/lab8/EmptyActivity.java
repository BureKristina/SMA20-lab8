package com.example.lab8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab8.adapters.PaymentAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmptyActivity extends AppCompatActivity {

    private TextView tStatus;
    private Button bPrevious, bNext;
    private DatabaseReference databaseReference;
    private FloatingActionButton floatingButton;
    private ListView listPayments;
    private int currentMonth;
    private List<Payment> payments = new ArrayList<Payment>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_empty);

        tStatus = (TextView) findViewById(R.id.tStatus);
        floatingButton = (FloatingActionButton) findViewById(R.id.fabAdd);
        listPayments = (ListView) findViewById(R.id.listPayments);

        addPayments();

        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EmptyActivity.this.onAddPayment();
            }
        });
    }

    public void onAddPayment(){
        Intent intent = new Intent(this, AddPayment.class);
        startActivity(intent);
    }

    private void addPayments() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        payments.removeAll(payments);
        databaseReference.child("wallet").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot datas: snapshot.getChildren()){

                        String time = datas.getKey();
                        String cost = datas.child("cost").getValue().toString();
                        String name = datas.child("name").getValue().toString();
                        String type = datas.child("type").getValue().toString();

                        payments.add(new Payment(Double.parseDouble(cost), name, type, time));
                    }
                    setListView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setListView() {
        PaymentAdapter paymentAdapter = new PaymentAdapter(this, R.layout.payment, payments);
        listPayments.setAdapter(paymentAdapter);
    }
}
