package com.yzhqnyzwb.inventorypro;

//import android.app.FragmentManager;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.yzhqnyzwb.inventorypro.fragments.InventoryFragment;
import com.yzhqnyzwb.inventorypro.fragments.OficeFregent;
import com.yzhqnyzwb.inventorypro.fragments.ZedFragment;

public class HomeActivity extends AppCompatActivity {
   private  FragmentManager fragmentManager=getSupportFragmentManager();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentManager.beginTransaction().replace(R.id.framebuoton, new InventoryFragment()).commit();

                    return true;
                case R.id.navigation_dashboard:
                    fragmentManager.beginTransaction().replace(R.id.framebuoton, new ZedFragment()).commit();

                    return true;
                case R.id.navigation_meneger:
                    fragmentManager.beginTransaction().replace(R.id.framebuoton, new OficeFregent()).commit();


                    return true;

            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fragmentManager.beginTransaction().replace(R.id.framebuoton, new InventoryFragment()).commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


    }

}
