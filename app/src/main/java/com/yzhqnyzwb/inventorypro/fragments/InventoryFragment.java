package com.yzhqnyzwb.inventorypro.fragments;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.yzhqnyzwb.inventorypro.BarcodeCaptureActivity;
import com.yzhqnyzwb.inventorypro.BarcodeList;
import com.yzhqnyzwb.inventorypro.R;
import com.yzhqnyzwb.inventorypro.SerchOfDataBase;
import com.yzhqnyzwb.inventorypro.camera.models.ProudactItem;
import com.yzhqnyzwb.inventorypro.camera.utils.ImageLoader;

/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends Fragment {
    private static final int RC_BARCODE_CAPTURE = 9001;
    RecyclerView rvInventory;
    Button btnFind;
    Context context;
    Button add;
    DatabaseReference mDate;


    public InventoryFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_inventory, container, false);
        //  rvInventory.setAdapter();
        btnFind= (Button) v.findViewById(R.id.btnFindProudct);
        btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), BarcodeCaptureActivity.class);
                startActivityForResult(intent,RC_BARCODE_CAPTURE);


            }
        });

        add= (Button) v.findViewById(R.id.btnAdd);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), BarcodeList.class);
                startActivity(intent);
            }
        });

        FirebaseUser currntUser= FirebaseAuth.getInstance().getCurrentUser();
        if (currntUser==null){
            Log.e("store","null user");
            return v;
        }

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("productItem").child(currntUser.getUid());

        rvInventory= (RecyclerView) v.findViewById(R.id.rvInventoriLIST);
        rvInventory.setLayoutManager(new LinearLayoutManager(context));

        rvInventory.setAdapter(new InventoryListAdapter(reference,this));


        return v;
    }

    public static class InventoryListAdapter extends FirebaseRecyclerAdapter<ProudactItem,InventoryListAdapter.InventoryListViewHolder> {
        Fragment fragment;
        public InventoryListAdapter(Query query, Fragment fragment){
            super(ProudactItem.class,R.layout.inventory_item,InventoryListViewHolder.class,query);
            this.fragment=fragment;
        }



        @Override
        protected void populateViewHolder(InventoryListViewHolder viewHolder, ProudactItem model, int position) {
            viewHolder.tvIVname.setText("Name: "+model.getName());
            viewHolder.tvIVdes.setText("Description: "+model.getDescription());
            viewHolder.tvIVbarcode.setText("Barcode: "+model.getBarcode());
            viewHolder.tvIVcount.setText("Count: "+model.getCount());
            viewHolder.tvIVprice.setText("Price: "+model.getPrice());
            viewHolder.model=model;
            ImageLoader.loadImg(model.getImageUrl(),viewHolder.imageInventory);
        }


        public static class InventoryListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tvIVname,
                    tvIVdes,
                    tvIVbarcode,
                    tvIVcount,
                    tvIVprice;
            ProudactItem model;
            ImageView imageInventory;

            public InventoryListViewHolder(View itemView) {
                super(itemView);
                tvIVname= (TextView) itemView.findViewById(R.id.tvIVname);
                tvIVdes= (TextView) itemView.findViewById(R.id.tvIvDes);
                tvIVbarcode= (TextView) itemView.findViewById(R.id.tvIVbarcode);
                tvIVcount= (TextView) itemView.findViewById(R.id.tvIvCount);
                tvIVprice= (TextView) itemView.findViewById(R.id.tvIVprice);
                imageInventory = (ImageView)itemView.findViewById(R.id.ivinventory);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_BARCODE_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra(BarcodeCaptureActivity.BarcodeObject);
                    MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), R.raw.barcode);
                    mediaPlayer.start();
                    String barcodeRead=barcode.displayValue;

              Intent intent=new Intent(getActivity(),SerchOfDataBase.class);
                    intent.putExtra("barcode",barcodeRead);
                    startActivity(intent);



                } else {
                    Toast.makeText(context,R.string.barcode_failure, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context,R.string.barcode_error, Toast.LENGTH_SHORT).show();

            }

        }
    }


}