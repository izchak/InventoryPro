package com.yzhqnyzwb.inventorypro.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.yzhqnyzwb.inventorypro.R;
import com.yzhqnyzwb.inventorypro.ZedImportList;
import com.yzhqnyzwb.inventorypro.camera.models.ZedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZedFragment extends Fragment {
    RecyclerView rvZed;
    Context context;
    DatabaseReference db;
    FloatingActionButton flbAddZed;
    EditText getYEAR;
    EditText getMONTH;
    Button ok;




    public ZedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.zed_fragment, container, false);

        final FirebaseUser currntUser= FirebaseAuth.getInstance().getCurrentUser();
        if (currntUser==null){
            Log.e("store","null user");
            return v;
        }
        getYEAR=(EditText) v.findViewById(R.id.etGetyear);
        getMONTH=(EditText) v.findViewById(R.id.etGetmonth);
        ok=(Button)v.findViewById(R.id.btnOkZshow);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ZedListItem").child(currntUser.getUid()).child(getYEAR.getText().toString()).child(getMONTH.getText().toString());
                rvZed.setAdapter(new ZedListAdapter(reference,ZedFragment.this));

            }
        });



       // DatabaseReference reference= FirebaseDatabase.getInstance().getReference("ZedListItem").child(currntUser.getUid()).child("2018").child("9");


        rvZed= (RecyclerView) v.findViewById(R.id.rvZED);
        rvZed.setLayoutManager(new LinearLayoutManager(context));


      //  rvZed.setAdapter(new ZedListAdapter(reference,this));

        flbAddZed=v.findViewById(R.id.flbImpZed);
        flbAddZed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ZedImportList.class);
                startActivity(intent);
            }
        });

        return v;

    }

    public static class ZedListAdapter extends FirebaseRecyclerAdapter<ZedList,ZedListAdapter.ZedlistViewHolder> {
        Fragment fragment;

        public ZedListAdapter(Query query, Fragment fragment) {
            super(ZedList.class,R.layout.zed_item,ZedlistViewHolder.class, query);
            this.fragment=fragment;
        }


        @Override
        protected void populateViewHolder(ZedlistViewHolder viewHolder, ZedList model, int position) {

            viewHolder.etData.setText("Data: "+model.getDateOfZed());
            viewHolder.etRsult.setText("Rsult: "+model.getResultForZed());
            viewHolder.model=model;
        }


        public static class ZedlistViewHolder extends RecyclerView.ViewHolder{
            TextView etData,etRsult;
            ZedList model;

            public ZedlistViewHolder(View itemView) {
                super(itemView);
                etData= (TextView) itemView.findViewById(R.id.etZdate);
                etRsult= (TextView) itemView.findViewById(R.id.etZresult);

            }


        }
    }

}

