package com.yzhqnyzwb.inventorypro;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yzhqnyzwb.inventorypro.camera.models.User;

public class RagisterUser extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;
    EditText name,phone,email,pasword;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ragister_user);

        name =(EditText) findViewById(R.id.etNameUser);
        phone =(EditText) findViewById(R.id.etPhoneUser);
        email =(EditText) findViewById(R.id.etEmail);
        pasword =(EditText) findViewById(R.id.etPasword);
        btnSave= (Button) findViewById(R.id.btnSaveUser);
        mAuth=FirebaseAuth.getInstance();




        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rgisterUser();

            }
        });

    }

    public void rgisterUser(){



        final String emaill=email.getText().toString();
        final String password=pasword.getText().toString();

        if (TextUtils.isEmpty(emaill)){

        }if (TextUtils.isEmpty(password)){

        }

        mAuth.createUserWithEmailAndPassword(emaill,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    String userID=mAuth.getCurrentUser().getUid().toString();
                    Toast.makeText(RagisterUser.this,userID, Toast.LENGTH_LONG).show();

                    String nameUser=name.getText().toString();
                    String phoneUser=phone.getText().toString();
                    String emailUser=email.getText().toString();
                    String paswordUser=pasword.getText().toString();

                    User user=new User(mAuth.getUid().toString(),nameUser,phoneUser,emailUser,paswordUser);


                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userID);
                   databaseReference.setValue(user);

                    Toast.makeText(RagisterUser.this, "המשתמש נוסף בהצלחה", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

}
