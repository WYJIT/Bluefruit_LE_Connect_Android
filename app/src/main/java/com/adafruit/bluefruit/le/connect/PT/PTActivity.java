package com.adafruit.bluefruit.le.connect.PT;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.adafruit.bluefruit.le.connect.R;

public class PTActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private PTActivityFragment mFragment = new PTActivityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pt);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.messages:
                        transitionToFragment(new MessageFragment());
                        break;
                    case R.id.home:
                        transitionToFragment(new PTActivityFragment());
                        break;
                    case R.id.patients:
                        transitionToFragment(new PTExersiceSummaryFragment());
                        break;
                }
                return true;
            }
        });

        mFragment.setArguments(getIntent().getExtras());
        transitionToFragment(mFragment);

    }

    public void transitionToFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

}
