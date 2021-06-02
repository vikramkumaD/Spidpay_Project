package com.example.spidpay.ui;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;

import com.example.spidpay.R;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spidpay.databinding.ActivityHostBinding;

public class HostActivity extends AppCompatActivity implements ChangeTitlenandIconInterface {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHostBinding binding;
    private boolean ishostfragment = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.landingFragment)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_host);
        NavigationUI.setupWithNavController(navigationView, navController);
        binding.imgOpendrawer.setOnClickListener(v -> {

            if (drawer.isOpen())
                drawer.openDrawer(GravityCompat.END);
            else
                drawer.openDrawer(GravityCompat.START);
        });


        binding.imgBackpress.setOnClickListener(v -> {
            onBackPressed();
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.host, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_host);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.tvPageTitle.setText("");
        binding.imgOpendrawer.setVisibility(View.VISIBLE);
        binding.imgBackpress.setVisibility(View.GONE);
    }

    @Override
    public void changeTitlenadIcon(String title, boolean ishostfrag) {

        this.ishostfragment = ishostfrag;
        if (!ishostfrag) {
            binding.tvPageTitle.setText("");
            binding.imgOpendrawer.setVisibility(View.VISIBLE);
            binding.imgBackpress.setVisibility(View.GONE);
        } else {
            binding.tvPageTitle.setText(title);
            binding.imgOpendrawer.setVisibility(View.GONE);
            binding.imgBackpress.setVisibility(View.VISIBLE);
        }

    }
}