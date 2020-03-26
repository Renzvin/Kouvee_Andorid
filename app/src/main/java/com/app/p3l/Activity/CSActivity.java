package com.app.p3l.Activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.app.p3l.R;
import com.google.android.material.navigation.NavigationView;

public class CSActivity extends AppCompatActivity {
    private TextView nama,role;
    private AppBarConfiguration mAppBarConfiguration;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs);
        Toolbar toolbar = findViewById(R.id.cs_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.cs_drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_cs);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_cs_produk, R.id.nav_cs_layanan,R.id.nav_cs_customer,R.id.nav_cs_hewan)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_cs_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        view = navigationView.getHeaderView(0);
        nama = (TextView) view.findViewById(R.id.CS_Nama);
        role = (TextView) view.findViewById(R.id.CS_Role);
        nama.setText(getIntent().getStringExtra("nama"));
        role.setText(getIntent().getStringExtra("role"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_cs_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
