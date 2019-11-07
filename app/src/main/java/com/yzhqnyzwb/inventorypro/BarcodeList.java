package com.yzhqnyzwb.inventorypro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.yzhqnyzwb.inventorypro.camera.models.ProudactItem;

public class BarcodeList extends Activity {

    private static final int PIC_IMAGE_REQUEST = 234;
    // use a compound button so either checkbox or switch widgets work.
    FirebaseUser mUser;
    FirebaseDatabase mDatabase;
    StorageReference mstorge;
    private Uri imageUri;
    private String mUrl;
    ProgressDialog progresDialog;


    Context context;

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private EditText etName, etDes;
    private TextView tvBarcode,tvPrice1, tvVAT,tvProfit,tvPriceSele;
    private Button btnScen;
    private Button btnSave;
    private Button btnProfit;
    private Button btnAddImageProduct;
    private Uri filePath;
    private ImageView ivProduct;


    Button btnPlos, btnMinos;
    EditText price, cuont;

    private static final int RC_BARCODE_CAPTURE = 9001;
    private static final String TAG = "BarcodeMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_list);

        Intent intentGet=getIntent();
        final Bundle b=intentGet.getExtras();

        statusMessage = (TextView) findViewById(R.id.status_message);
        ivProduct = (ImageView) findViewById(R.id.ivImageProduct);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();
        progresDialog=new ProgressDialog(this);

        tvPrice1= (TextView) findViewById(R.id.tvSprice);
        tvVAT=findViewById(R.id.tvVATtoPAY);
        tvProfit=findViewById(R.id.tvPROFIT);
        tvPriceSele =findViewById(R.id.tvPriceSele);

        if (b!=null){
            String s= (String) b.get("price");
            tvPrice1.setText(s);
            //price.setText(s);
            String vat= (String) b.get("vat");
            tvVAT.setText(vat);
            String profit1= (String) b.get("profit");
            tvProfit.setText(profit1);
            String priceseler= (String) b.get("priceForSele");
            tvPriceSele.setText(priceseler);

        }

        tvBarcode = (TextView) findViewById(R.id.etBarcode);

        if (b!=null){
            String barcodeNumOfIntent= (String) b.get("brCode");
            tvBarcode.setText(barcodeNumOfIntent);
        }


        btnAddImageProduct = (Button) findViewById(R.id.btnAddImageProduct);


        btnAddImageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "select an Image"), PIC_IMAGE_REQUEST);

                // mstorge = FirebaseStorage.getInstance().getReference();
                // ivProduct.setImageResource();


            }
        });

        btnProfit = (Button) findViewById(R.id.btnProfit);
        btnProfit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BarcodeList.this,ProfitActivity.class);
                startActivity(intent);
            }
        });

        etName = (EditText) findViewById(R.id.etname);
        etDes = (EditText) findViewById(R.id.etDes);

        if (tvBarcode == null) {
            etName.setError("scan barcode first");
            etDes.setError("scan barcode first");
        }

        btnScen = (Button) findViewById(R.id.btnScen);
        btnScen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // launch barcode activity.
                Intent intent = new Intent(BarcodeList.this, BarcodeCaptureActivity.class);
                intent.putExtra(BarcodeCaptureActivity.AutoFocus, autoFocus.isChecked());
                intent.putExtra(BarcodeCaptureActivity.UseFlash, useFlash.isChecked());

                startActivityForResult(intent, RC_BARCODE_CAPTURE);

            }
        });


        findViewById(R.id.read_barcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText(null);
                etDes.setText(null);
                tvBarcode.setText(null);
                cuont.setText("0");
                price.setText(null);

            }
        });

        price = (EditText) findViewById(R.id.etPrice);
        cuont = (EditText) findViewById(R.id.etCount);

        btnPlos = (Button) findViewById(R.id.btnPlos);
        btnPlos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = cuont.getText();
                Integer temp = Integer.valueOf(text.toString());
                temp++;
                cuont.setText(temp.toString());
            }
        });
        btnMinos = (Button) findViewById(R.id.btnMinos);
        btnMinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Editable text = cuont.getText();
                Integer temp = Integer.valueOf(text.toString());
                temp--;
                if (temp < 0) {
                    temp = 0;
                }
                cuont.setText(temp.toString());
            }
        });



        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                progresDialog.setMessage("adding product...");
                progresDialog.show();
                final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (currentUser == null) {
                    Log.e("Store", "no user");
                    return;

                }
                if (tvBarcode.getText().equals("barcode number") || etName.getText().length() == 0) {
                    if (tvBarcode.getText().equals("barcode number")) {
                        Toast.makeText(BarcodeList.this, "scen barcode", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (etName.getText().length() == 0) {
                        Toast.makeText(BarcodeList.this, "name product is empty", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (filePath!=null){

                    FirebaseStorage.getInstance().getReference(currentUser.getUid()+"productImeges").child(tvBarcode.getText().toString()).putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadUrl = urlTask.getResult();
                            mUrl=downloadUrl.toString();
                          //  mUrl= taskSnapshot.getStorage().getDownloadUrl().toString();
                            DatabaseReference newRowRef = FirebaseDatabase.getInstance().
                                    getReference("productItem").
                                    child(currentUser.getUid())
                                    .push();

                            ProudactItem item = new ProudactItem(currentUser.getUid(),
                                    tvBarcode.getText().toString(),
                                    etName.getText().toString(),
                                    etDes.getText().toString(),
                                    cuont.getText().toString(),
                                    price.getText().toString(),
                                    newRowRef.getKey(),
                                    mUrl,
                                    tvVAT.getText().toString(),
                                    tvProfit.getText().toString(),
                                    tvPrice1.getText().toString());


                            newRowRef.setValue(item).addOnSuccessListener(new OnSuccessListener<Void>() {

                                @Override
                                public void onSuccess(Void aVoid) {

                                    Toast.makeText(BarcodeList.this, "Item saved Successfully", Toast.LENGTH_SHORT).show();
                                    MediaPlayer mediaPlayer = MediaPlayer.create(BarcodeList.this, R.raw.success);
                                    mediaPlayer.start();
                                    progresDialog.dismiss();
                                    Intent intent=new Intent(BarcodeList.this,HomeActivity.class);
                                    startActivity(intent);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BarcodeList.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                            etName.setText(null);
                            etDes.setText(null);
                            tvBarcode.setText("barcode number");
                            cuont.setText("0");
                            price.setText(null);


                        }
                    });

                }else {
                    progresDialog.dismiss();
                    Toast.makeText(BarcodeList.this, "add image", Toast.LENGTH_SHORT).show();
                }

            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    MediaPlayer mediaPlayer = MediaPlayer.create(BarcodeList.this, R.raw.barcode);
                    mediaPlayer.start();
                    statusMessage.setText(R.string.barcode_success);
                    tvBarcode.setText(barcode.displayValue);
                    Log.d(TAG, "Barcode read: " + barcode.displayValue);
                } else {
                    statusMessage.setText(R.string.barcode_failure);
                    Log.d(TAG, "No barcode captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.barcode_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }

        } else if (requestCode == PIC_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                Glide.with(this).load(filePath).into(ivProduct);

            }


        }

    }


}

