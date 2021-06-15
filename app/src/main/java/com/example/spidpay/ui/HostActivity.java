package com.example.spidpay.ui;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;

import com.example.spidpay.R;
import com.example.spidpay.util.Constant;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.interfaces.UpdateBottomView;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.spidpay.databinding.ActivityHostBinding;

public class HostActivity extends AppCompatActivity implements ChangeTitlenandIconInterface, UpdateBottomView {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHostBinding binding;


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


        binding.imgBackpress.setOnClickListener(v -> onBackPressed());

        binding.relativeNotification.setOnClickListener(v -> navController.navigate(R.id.notificationFragment));

        binding.relativeHome.setOnClickListener(v -> navController.navigate(R.id.landingFragment));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.host, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_host);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
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

    public void updateBottomView(int id) {
        if (id == Constant.BOTTOM_NOTIFICATION) {
            update_Notification();
        } else if (id == Constant.BOTTOM_HOME) {
            update_Home();
        } else if (id == Constant.BOTTOM_COMMISION) {
            update_Commision();
        } else if (id == Constant.BOTTOM_HISTORY) {
            update_History();
        }
    }

    public void update_Notification() {
        binding.relativeNotification.setBackgroundColor(getResources().getColor(R.color.green));
        binding.relativeHome.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHistory.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeCommisson.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
    }

    public void update_Home() {
        binding.relativeNotification.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHome.setBackgroundColor(getResources().getColor(R.color.green));
        binding.relativeHistory.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeCommisson.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
    }

    public void update_History() {
        binding.relativeNotification.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHome.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHistory.setBackgroundColor(getResources().getColor(R.color.green));
        binding.relativeCommisson.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
    }

    public void update_Commision() {
        binding.relativeNotification.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHome.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeHistory.setBackgroundColor(getResources().getColor(R.color.botton_nav_inactive_color));
        binding.relativeCommisson.setBackgroundColor(getResources().getColor(R.color.green));
    }

    @Override
    public void bottomViewId(int id) {
        updateBottomView(id);
    }
}