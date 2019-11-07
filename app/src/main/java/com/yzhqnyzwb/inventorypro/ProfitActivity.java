package com.yzhqnyzwb.inventorypro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ProfitActivity extends AppCompatActivity {

    EditText etPiceBforeVAT,etVat,etPricing;
    TextView tvPriceVat,tvVAT,tvProfit;
    RatingBar rbProfit;
    Button btmOK,btnHelp,btnSave;
    CheckBox cbA,cbB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit);
        etPiceBforeVAT= (EditText) findViewById(R.id.etPriceBeforVat);
        etVat= (EditText) findViewById(R.id.etVAT);
        etPricing= (EditText) findViewById(R.id.etPricing);

        tvPriceVat= (TextView) findViewById(R.id.tvPricevat);
        tvVAT= (TextView) findViewById(R.id.tvVatInList);
        tvProfit= (TextView) findViewById(R.id.tvProfitInList);

        rbProfit= (RatingBar) findViewById(R.id.rbPpfinInLIST);

        btnHelp= (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfitActivity.this, "Enter all refrence", Toast.LENGTH_SHORT).show();

            }
        });



        cbA= (CheckBox) findViewById(R.id.cbA);
        cbB= (CheckBox) findViewById(R.id.cbB);

        btmOK= (Button) findViewById(R.id.btnOK);
        btmOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (etPiceBforeVAT.length()==0||etPricing.length()==0){
                    Toast.makeText(ProfitActivity.this, "Enter all refrence", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (cbA.isChecked()){
                    cbB.isEnabled();
                    try {
                        Editable text1=etPiceBforeVAT.getText();
                        Editable text2=etVat.getText();
                        Editable text3=etPricing.getText();

                        double temp1=Double.valueOf(text1.toString());
                        double temp2=Double.valueOf(text2.toString());
                        double temp3=Double.valueOf(text3.toString());

                        double rsult1=0.0;
                        double rsult2=0.0;
                        double rsult3=0.0;
                        double rsult4=0.0;

                        rsult1=(temp1*0.01*temp2+temp1)*temp3;
                        rsult2=rsult1*0.01*temp2+rsult1;
                        float f= (float) rsult2;
                        String rsultB= String.format("%.2f",f);
                        tvPriceVat.setText(rsultB);

                        rsult3=(rsult2*0.01*temp2)-(temp1*0.01*temp2);
                        float f1= (float) rsult3;
                        String rsultC= String.format("%.2f",f1);

                        tvVAT.setText(rsultC);

                        rsult4=rsult2-rsult3-temp1;
                        float f2= (float) rsult4;
                        String rsultD=String.format("%.2f",f2);

                        tvProfit.setText(rsultD);

                        if (rsult4>temp1*2||rsult4==temp1*2){
                            rbProfit.setRating(5);
                        }else if (rsult4>temp1*1.5||rsult4==temp1*1.5){
                            rbProfit.setRating(4);
                        }else if(rsult4>temp1||rsult4==temp1*1){
                            rbProfit.setRating(3);
                        }else if(rsult4>temp1*0.5||rsult4==temp1*0.5){
                            rbProfit.setRating(2);
                        }

                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }else if (cbB.isChecked()){
                    cbA.isEnabled();
                    try {

                        Editable text1=etPiceBforeVAT.getText();
                        Editable text2=etVat.getText();
                        Editable text3=etPricing.getText();

                        Double temp1=Double.valueOf(text1.toString());
                        Double temp2=Double.valueOf(text2.toString());
                        Double temp3=Double.valueOf(text3.toString());

                        double rsult1=0.0;
                        double rsult2=0.0;
                        double rsult3=0.0;
                        double rsult4=0.0;

                        rsult1=temp1*temp3;
                        rsult2=(rsult1*0.01*temp2)+rsult1;
                        float f= (float) rsult2;
                        String rsultB=String.format("%.2f",f);
                        tvPriceVat.setText(rsultB);

                        rsult3=((rsult2-rsult1)-(temp1*0.01*temp2));
                        float f1= (float) rsult3;
                        String rsultC=String.format("%.2f",f1);

                        tvVAT.setText(rsultC);

                        rsult4=rsult2-rsult3-temp1;
                        float f2= (float) rsult4;
                        String rsultD=String.format("%.2f",f2);

                        tvProfit.setText(rsultD);

                        if (rsult4>temp1*2||rsult4==temp1*2){
                            rbProfit.setRating(5);
                        }else if (rsult4>temp1*1.5||rsult4==temp1*1.5){
                            rbProfit.setRating(4);
                        }else if(rsult4>temp1||rsult4==temp1*1){
                            rbProfit.setRating(3);
                        }else if(rsult4>temp1*0.5||rsult4==temp1*0.5){
                            rbProfit.setRating(2);
                        }

                    }catch (Exception e){
                        e.printStackTrace();

                    }

                }

            }
        });

        btnSave= (Button) findViewById(R.id.btnSeve1);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfitActivity.this,BarcodeList.class);
                intent.putExtra("price",etPiceBforeVAT.getText().toString());
                intent.putExtra("vat",tvVAT.getText().toString());
                intent.putExtra("profit",tvProfit.getText().toString());
                intent.putExtra("priceForSele",tvPriceVat.getText().toString());
                startActivity(intent);

            }
        });

    }
}
