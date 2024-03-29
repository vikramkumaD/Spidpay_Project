package com.example.spidpay.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.example.spidpay.R;
import com.example.spidpay.ui.login.LoginActivity;
import com.example.spidpay.util.Constant;
import com.example.spidpay.interfaces.ChangeTitlenandIconInterface;
import com.example.spidpay.interfaces.UpdateBottomView;
import com.example.spidpay.util.PrefManager;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
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
    NavController navController;
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
         drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_host);
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

        NavigationView navigationView1=findViewById(R.id.nav_view);

        navigationView1.getMenu().findItem(R.id.logout).setOnMenuItemClickListener(item -> {
            NavigationUI.onNavDestinationSelected(item,navController);
            exitDialog();
            return false;
        });

    }

    public void exitDialog()
    {

            // Create the object of
            // AlertDialog Builder class
            AlertDialog.Builder builder = new AlertDialog.Builder(HostActivity.this);

            // Set the message show for the Alert time
            builder.setMessage("Are you sure you want to logout?");

            // Set Alert Title
            builder.setTitle("Alert !");

            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    new PrefManager(HostActivity.this).setIsLandingPageOpen(false);
                                    Intent intent=new Intent(HostActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            });

            // Set the Negative button with No name
            // OnClickListener method is use
            // of DialogInterface interface.
            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int which)
                                {

                                    drawer.close();
                                    dialog.cancel();
                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();

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