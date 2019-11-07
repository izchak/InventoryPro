package com.yzhqnyzwb.inventorypro;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yzhqnyzwb.inventorypro.camera.models.ZedList;

public class ZedImportList extends AppCompatActivity {
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    DatePicker datePicker;
    TextView tvDate;
    EditText etRsult;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zed_import_list);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        etRsult = (EditText) findViewById(R.id.etRsult);
        btnSave = (Button) findViewById(R.id.btnSavaZedImport);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser=FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser==null){
                    Log.e("Store","no user");
                    return;
                }

                int dey=datePicker.getDayOfMonth();
                int month=datePicker.getMonth()+1;
                int year=datePicker.getYear();
                String date=dey+"/" +month+"/"+year;
                String rsult=etRsult.getText().toString();

                if(rsult.length()==0){
                    Toast.makeText(ZedImportList.this, "import rsult", Toast.LENGTH_SHORT).show();
                    return;
                }

                DatabaseReference newRowRef=FirebaseDatabase.getInstance().
                        getReference("ZedListItem").child(currentUser.getUid()).child(year+"").child(month+"").
                        push();

                ZedList item=new ZedList(currentUser.getUid(),date,rsult);



                newRowRef.setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ZedImportList.this, "Zed saved Successfully", Toast.LENGTH_SHORT).show();
                        MediaPlayer mediaPlayer=MediaPlayer.create(ZedImportList.this,R.raw.success);
                        mediaPlayer.start();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ZedImportList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        MediaPlayer mediaPlayer=MediaPlayer.create(ZedImportList.this,R.raw.failure);
                        mediaPlayer.start();

                    }
                });
                Intent intent=new Intent(ZedImportList.this,HomeActivity.class);
                startActivity(intent);
            }

        });

    }

}
