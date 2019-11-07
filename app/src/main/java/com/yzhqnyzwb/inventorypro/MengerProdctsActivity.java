package com.yzhqnyzwb.inventorypro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MengerProdctsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menger_prodcts);

        final FirebaseUser currntUser= FirebaseAuth.getInstance().getCurrentUser();
        ValueEventListener reference= FirebaseDatabase.getInstance().getReference("productItem").child(currntUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i=0;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                   String numOfProducs=ds.child("count").getValue().toString();
                    Integer cuntPr=Integer.valueOf(numOfProducs);
                    i=cuntPr+i;

                }
                Toast.makeText(MengerProdctsActivity.this, i+"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
