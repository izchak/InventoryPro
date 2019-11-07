package com.yzhqnyzwb.inventorypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;

    EditText email;
    EditText pasword;
    Button login;
    Button register;
    ProgressDialog progresDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAuth= FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser()!=null){
            Intent intnt=new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intnt);
        }


        email= (EditText) findViewById(R.id.etEmail);
        pasword= (EditText) findViewById(R.id.etPasword);
        login= (Button) findViewById(R.id.btnLogin);
        progresDialog=new ProgressDialog(this);
        login.setOnClickListener(this);

        register= (Button) findViewById(R.id.btnReg);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            loginUser();
        }if(v== register){
            registerUser();
        }

    }
    private void loginUser() {
        String getEmail = email.getText().toString().trim();
        String getPasword = pasword.getText().toString().trim();

        if (TextUtils.isEmpty(getEmail)){
            Toast.makeText(this, "Please Enter Emeil", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(getPasword)){
            Toast.makeText(this, "Please Enter Pasword", Toast.LENGTH_SHORT).show();
        }

        progresDialog.setMessage("Conecting Please Wait...");
        progresDialog.show();
        mAuth.signInWithEmailAndPassword(getEmail,getPasword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progresDialog.dismiss();
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "login is Secses", Toast.LENGTH_SHORT).show();
                    Intent intnt=new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intnt);

                }else {
                    Toast.makeText(MainActivity.this,"חבר אינך רשום במערכת מוזמן להצטרף", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void registerUser (){
        Intent intent=new Intent(this,RagisterUser.class);
        startActivity(intent);
    }

}