package com.yzhqnyzwb.inventorypro;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BaseActivity extends AppCompatActivity {
    protected AlertDialog dialog;

    protected AlertDialog showProgress(boolean show){
        if (dialog == null){
            dialog = new AlertDialog.Builder(this).setTitle("").setMessage("").create();
        }

        if (show){
            dialog.show();
        }else {
            dialog.dismiss();
        }
        return dialog;
    }

    protected FirebaseUser getUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

}

