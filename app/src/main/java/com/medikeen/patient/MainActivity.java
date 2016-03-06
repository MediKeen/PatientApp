package com.medikeen.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.medikeen.R;
import com.medikeen.util.SessionManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment fragment;
    SessionManager sessionManager;
    HomeFragment homeFragment;

    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //SET ACTIONBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attachPrescriptionsIntent = new Intent(
                        MainActivity.this,
                        AttachPrescription.class);
                startActivity(attachPrescriptionsIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        if (savedInstanceState == null) {
            // ATTACH HOME FRAGMENT
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            homeFragment = new HomeFragment();
            fragmentTransaction.replace(R.id.frame_container, homeFragment);
            fragmentTransaction.commit();
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().getFragments().get(0);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            SelectItem(0);
        } else if (id == R.id.nav_profile) {
            SelectItem(4);
        } else if (id == R.id.nav_about_us) {
            SelectItem(3);
        } else if (id == R.id.nav_terms_and_conditions) {
            SelectItem(2);
        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_feedback) {
            SelectItem(1);
        } else if (id == R.id.nav_logout) {
            SelectItem(5);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideFAB(boolean hide) {
        if (hide) {
            fab.hide();
        } else {
            fab.show();
        }
    }

    public void SelectItem(int possition) {

        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new HomeFragment();

                android.support.v4.app.FragmentTransaction ftHome = getSupportFragmentManager()
                        .beginTransaction();
                ftHome.replace(R.id.frame_container, fragment);
                ftHome.commit();

                hideFAB(false);

                break;
            case 1:
                fragment = new Feedback();

                android.support.v4.app.FragmentTransaction ftFeedback = getSupportFragmentManager()
                        .beginTransaction();
                ftFeedback.replace(R.id.frame_container, fragment);
                ftFeedback.commit();

                hideFAB(true);

                break;
            case 2:
                fragment = new TermsNCondition();

                android.support.v4.app.FragmentTransaction ftTandC = getSupportFragmentManager()
                        .beginTransaction();
                ftTandC.replace(R.id.frame_container, fragment);
                ftTandC.commit();

                hideFAB(true);

                break;
            case 3:
                fragment = new AboutUs();

                android.support.v4.app.FragmentTransaction ftAboutUs = getSupportFragmentManager()
                        .beginTransaction();
                ftAboutUs.replace(R.id.frame_container, fragment);
                ftAboutUs.commit();

                hideFAB(true);

                break;
            case 4:
                fragment = new UserProfileActivity();

                android.support.v4.app.FragmentTransaction ftProfile = getSupportFragmentManager()
                        .beginTransaction();
                ftProfile.replace(R.id.frame_container, fragment);
                ftProfile.commit();

                hideFAB(true);

//                Intent profileIntent = new Intent(MainActivity.this,
//                        UserProfileActivity.class);
//                startActivity(profileIntent);
                break;
            case 5:
                sessionManager.logoutUser();

                Intent logoutIntent = new Intent(MainActivity.this, Login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                finish();
                break;
            default:
                break;
        }
    }
}
