package com.medikeen.patient;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.medikeen.patient.R;
import com.medikeen.adapters.CustomDrawerAdapter;
import com.medikeen.adapters.DrawerItem;
import com.medikeen.util.SessionManager;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class LandingActivity extends FragmentActivity {

    CustomDrawerAdapter adapter;
    List<DrawerItem> dataList;
    Fragment fragment;
    SessionManager sessionManager;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

	/*
     * @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
	 * 
	 * if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //
	 * do something on back. Intent startMain = new Intent(Intent.ACTION_MAIN);
	 * startMain.addCategory(Intent.CATEGORY_HOME);
	 * startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	 * startActivity(startMain); return true; } return super.onKeyDown(keyCode,
	 * event); }
	 */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing
        dataList = new ArrayList<DrawerItem>();
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        sessionManager = new SessionManager(LandingActivity.this);

        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Add Drawer Item to dataList
        dataList.add(new DrawerItem("Home", R.drawable.home));
        dataList.add(new DrawerItem("Feedback", R.drawable.feedback));
        // dataList.add(new DrawerItem("History", R.drawable.history));
        dataList.add(new DrawerItem("Terms and Conditions",
                R.drawable.terms_and_conditions));
        dataList.add(new DrawerItem("About us", R.drawable.about_us));
        dataList.add(new DrawerItem("Profile", R.drawable.profile));
        dataList.add(new DrawerItem("Logout", R.drawable.logout));

        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer_item,
                dataList);

        mDrawerList.setAdapter(adapter);

        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // if (savedInstanceState == null) {
        SelectItem(0);
        // }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void SelectItem(int possition) {

        Bundle args = new Bundle();
        switch (possition) {
            case 0:
                fragment = new HomeFragment();
                args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(UploadDescription.IMAGE_RESOURCE_ID,
                        dataList.get(possition).getImgResID());
                break;

            case 1:
                fragment = new Feedback();
                args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(UploadDescription.IMAGE_RESOURCE_ID,
                        dataList.get(possition).getImgResID());

                break;
            case 2:
                fragment = new TermsNCondition();
                args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(UploadDescription.IMAGE_RESOURCE_ID,
                        dataList.get(possition).getImgResID());
                break;
            case 3:
                fragment = new AboutUs();
                args.putString(UploadDescription.ITEM_NAME, dataList.get(possition)
                        .getItemName());
                args.putInt(UploadDescription.IMAGE_RESOURCE_ID,
                        dataList.get(possition).getImgResID());
                break;
            case 4:
                Intent profileIntent = new Intent(LandingActivity.this,
                        UserProfileActivity.class);
                startActivity(profileIntent);
                break;
            case 5:
                sessionManager.logoutUser();

                Intent logoutIntent = new Intent(LandingActivity.this, Login.class);
                logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(logoutIntent);
                finish();

            default:
                break;
        }

        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.commit();

        // // fragment.setArguments(args);
        // FragmentManager frgManager = getSupportFragmentManager();
        // frgManager.beginTransaction().replace(R.id.content_frame,
        // fragment).commit();

        mDrawerList.setItemChecked(possition, true);
        setTitle(dataList.get(possition).getItemName());
        mDrawerLayout.closeDrawer(mDrawerList);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Fragment fragment = null;
        //
        // int itemId = item.getItemId();
        // if (itemId == R.id.action_privacy) {
        // fragment = new TermsNCondition();
        // setTitle("Terms and Conditions");
        // FragmentManager frgManager = getFragmentManager();
        // frgManager.beginTransaction().replace(R.id.content_frame,
        // fragment).commit();
        // } else if (itemId == R.id.action_aboutus) {
        // fragment = new AboutUs();
        // setTitle("About Us");
        // FragmentManager frgManager = getFragmentManager();
        // frgManager.beginTransaction().replace(R.id.content_frame,
        // fragment).commit();
        // } else if (itemId == R.id.action_profile) {
        // Intent profileIntent = new Intent(LandingActivity.this,
        // UserProfileActivity.class);
        // startActivity(profileIntent);
        // } else if (itemId == R.id.action_logout) {
        // sessionManager.logoutUser();
        //
        // Intent logoutIntent = new Intent(LandingActivity.this, Login.class);
        // logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // startActivity(logoutIntent);
        // finish();
        // }
        // else if (itemId == R.id.action_settings) {
        // setTitle("Settings");
        // }

        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            SelectItem(position);
        }
    }

}
