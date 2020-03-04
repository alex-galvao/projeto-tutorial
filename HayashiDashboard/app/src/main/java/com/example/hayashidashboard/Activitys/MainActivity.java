package com.example.hayashidashboard.Activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.hayashidashboard.Fragments.FragmentEstoque;
import com.example.hayashidashboard.Fragments.FragmentFinancas;
import com.example.hayashidashboard.Fragments.FragmentHome;
import com.example.hayashidashboard.Fragments.FragmentVendas;
import com.example.hayashidashboard.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {


    Toolbar toolbar;
    BottomNavigationView bnvMain;
    Fragment selectedFragment = null;
    private Connection connect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bnvMain = findViewById(R.id.bottom_navigation);
        bnvMain.setOnNavigationItemSelectedListener(navListener);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Hayashi Dashboard");
        toolbar.inflateMenu(R.menu.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.itemSair:
                        Intent sairIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(sairIntent);
                        finish();
                        return true;

                    case R.id.itemConfig:

                        return true;

                    default:
                        return false;
                }
            }
        });


        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentHome()).commit();


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {

                case R.id.itemHome:
                    selectedFragment = new FragmentHome();
                    break;

                case R.id.itemFinanças:
                    selectedFragment = new FragmentFinancas();
                    break;

                case R.id.itemVendas:
                    selectedFragment = new FragmentVendas();
                    break;

                case R.id.itemEstoque:
                    selectedFragment = new FragmentEstoque();
                    break;

            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };
}
