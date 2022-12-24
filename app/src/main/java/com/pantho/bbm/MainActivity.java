package com.pantho.bbm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.OnSwipe;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pantho.bbm.fragments.fragment_home;
import com.pantho.bbm.fragments.fragment_notAuthenticate;
import com.pantho.bbm.fragments.fragment_profile;
import com.pantho.bbm.fragments.fragment_search;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView bnv=findViewById(R.id.bnv);
        replaceFragment(new fragment_home());

        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menuHome:
                        replaceFragment(new fragment_home());
                        break;
                    case R.id.menuSearch:
                        replaceFragment(new fragment_search());
                        break;
                    case R.id.menuProfile:
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        if(user==null){
                            replaceFragment(new fragment_notAuthenticate());
                        }else{
                            replaceFragment(new fragment_profile());
                        }
                        break;
                }
                return true;
            }
        });

    }



    private void replaceFragment(Fragment fragment) {
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft= fm.beginTransaction();
        ft.replace(R.id.frame_layout,fragment);
        ft.commit();
    }
}