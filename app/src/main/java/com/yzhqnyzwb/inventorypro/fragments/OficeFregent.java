package com.yzhqnyzwb.inventorypro.fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yzhqnyzwb.inventorypro.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OficeFregent extends Fragment {
    Context context;
    TextView tvStoctakingNum;
    TextView tvinvestmetn;
    TextView tvVat;
    TextView tvProfit;
   // ProgressBar pbOfice;

    public OficeFregent() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View v= inflater.inflate(R.layout.fragment_ofice_fregent, container, false);
                tvStoctakingNum=(TextView) v.findViewById(R.id.tvStoctaking);
                tvinvestmetn=(TextView) v.findViewById(R.id.tvinvestment);
                tvVat=(TextView) v.findViewById(R.id.tvVATtoPAYnum);
                tvProfit=(TextView) v.findViewById(R.id.tvProfitNum);
               // pbOfice=(ProgressBar) v.findViewById(R.id.progressBarOfice);
        final ProgressDialog dialog = ProgressDialog.show(getActivity(), "Loading...", "Please wait...", true);



        final FirebaseUser currntUser= FirebaseAuth.getInstance().getCurrentUser();
        ValueEventListener reference= FirebaseDatabase.getInstance().getReference("productItem").child(currntUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dialog.show();
                int i=0;
                float d=0;
                float d1=0;
                float d2=0;
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    String numOfProducs=ds.child("count").getValue().toString();
                    Integer cuntPr=Integer.valueOf(numOfProducs);
                    i=cuntPr+i;

                    String rsultProductsMuny=ds.child("spoilerPrice").getValue().toString();
                    Float pricePro=Float.valueOf(rsultProductsMuny);
                    float priceXcunt=pricePro *cuntPr;
                    d=priceXcunt+d;

                    String rsultVATtoPay=ds.child("vat").getValue().toString();
                    Float vat=Float.valueOf(rsultVATtoPay);
                    float vatXcunt=vat *cuntPr;
                    d1=vatXcunt+d1;

                    String rsultProfit=ds.child("profit").getValue().toString();
                    Float profit=Float.valueOf(rsultProfit);
                    float profitXcunt=profit *cuntPr;
                    d2=profitXcunt+d2;

                }
                tvStoctakingNum.setText(i+"\n"+"products");
                tvinvestmetn.setText(d+"");
                tvVat.setText(d1+"");
                tvProfit.setText(d2+"");
                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return v;



    }

}
