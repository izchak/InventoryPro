package com.yzhqnyzwb.inventorypro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.yzhqnyzwb.inventorypro.camera.utils.ImageLoader;

public class SerchOfDataBase extends AppCompatActivity {
    TextView barcodeOfExtre;
    EditText nameOfPro,desOfPro,countOfPro,priceOfPro;
    ImageView imgOfPro;
    Button deletePro,editPro,savePro;
    int chackData=0;
    ProgressDialog progresDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch_of_data_base);



        Intent intentGet=getIntent();
        final Bundle b=intentGet.getExtras();


        barcodeOfExtre= (TextView) findViewById(R.id.barcodeNumOfSerch);
        nameOfPro= (EditText) findViewById(R.id.nameOfpro);
        desOfPro= (EditText) findViewById(R.id.desOFpro);
        countOfPro= (EditText) findViewById(R.id.countOfPro);
        priceOfPro= (EditText) findViewById(R.id.priceOfPro);
        imgOfPro= (ImageView) findViewById(R.id.imgOfpro);
        deletePro= (Button) findViewById(R.id.btnDelPro);
        editPro= (Button) findViewById(R.id.btnEdit);
        savePro= (Button) findViewById(R.id.btnSaveEdit);
        progresDialog=new ProgressDialog(this);
        barcodeOfExtre.setText(b.get("barcode").toString());
        final String s=b.get("barcode").toString();
        final String[] list = new String[1];

        final FirebaseUser currntUser= FirebaseAuth.getInstance().getCurrentUser();
        if (currntUser==null){
            Log.e("store","null user");
            return ;
        }


        FirebaseDatabase mData=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=mData.getReference();
        databaseReference.child("productItem").child(currntUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String bar=b.get("barcode").toString();

                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    String uID=currntUser.getUid();

                    String barNumOfDataBase=ds.child("barcode").getValue().toString();

                    if(barNumOfDataBase.equals(bar)){
                        String nameP=ds.child("name").getValue().toString();
                        nameOfPro.setText(nameP);

                        String desP=ds.child("description").getValue().toString();
                        desOfPro.setText(desP);

                        String countP=ds.child("count").getValue().toString();
                        countOfPro.setText(countP);

                        String priceP=ds.child("price").getValue().toString();
                        priceOfPro.setText(priceP);

                        ImageLoader.loadImg(ds.child("imageUrl").getValue().toString(),imgOfPro);

                        list[0] =ds.child("listId").getValue().toString();
                        chackData=2;
                    }

                }
                if (chackData==0){
                    Toast.makeText(SerchOfDataBase.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                    editPro.setBackgroundColor(getResources().getColor(R.color.color_orange));
                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        deletePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                databaseReference.child("productItem").child(currntUser.getUid()).child(list[0]).removeValue();
                FirebaseStorage.getInstance().getReference(currntUser.getUid()+"productImeges").child(s).delete();
                Toast.makeText(SerchOfDataBase.this, "The Prouduct is Deleted ", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(SerchOfDataBase.this,HomeActivity.class);
                startActivity(intent);


            }
        });
        savePro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progresDialog.setMessage("saving changes...");
                progresDialog.show();

                if (list[0]!=null){
                    String nameToSAVE =nameOfPro.getText().toString();
                    databaseReference.child("productItem").child(currntUser.getUid()).child(list[0]).child("name").setValue(nameToSAVE);
                    String desToSAVE=desOfPro.getText().toString();
                    databaseReference.child("productItem").child(currntUser.getUid()).child(list[0]).child("description").setValue(desToSAVE);
                    String priceToSAVE=priceOfPro.getText().toString();
                    databaseReference.child("productItem").child(currntUser.getUid()).child(list[0]).child("price").setValue(priceToSAVE);
                    String countToSAVE=countOfPro.getText().toString();
                    databaseReference.child("productItem").child(currntUser.getUid()).child(list[0]).child("count").setValue(countToSAVE);
                    Toast.makeText(SerchOfDataBase.this, "Changes saved", Toast.LENGTH_SHORT).show();
                    progresDialog.dismiss();
                    Intent intent=new Intent(SerchOfDataBase.this,HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(SerchOfDataBase.this, "Product does not exist", Toast.LENGTH_SHORT).show();
                }

            }
        });

        editPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SerchOfDataBase.this,BarcodeList.class);
                intent.putExtra("brCode",s);
                startActivity(intent);
            }
        });


    }
}
