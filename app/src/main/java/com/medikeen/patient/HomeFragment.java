package com.medikeen.patient;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.medikeen.R;
import com.medikeen.util.ViewPagerAdapter;

public class HomeFragment extends Fragment {

    ViewSwitcher viewSwitcher;
    FrameLayout newPres, history;
    TextView new_pres_text, history_text;

    Fragment newPresFragment, historyFragment;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private String dataGotFromServer;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.home_fragment, container,
                false);

        viewPager = (ViewPager) rootView.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabs);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFrag(new NewUploadPrescription(), "New Prescription");
        adapter.addFrag(new HistoryFragment(), "History");

        viewPager.setAdapter(adapter);
    }

    private void changeColor(int pos) {
        if (pos == 0) {

            new_pres_text.setClickable(false);
            new_pres_text.setEnabled(false);
            history_text.setClickable(true);
            history_text.setEnabled(true);

            new_pres_text.setTextColor(Color.parseColor("#1E88E5"));
            new_pres_text.setBackgroundColor(Color.parseColor("#ffffff"));

            history_text.setTextColor(Color.parseColor("#ffffff"));
            history_text.setBackgroundColor(Color.parseColor("#1E88E5"));
        } else if (pos == 1) {

            new_pres_text.setClickable(true);
            new_pres_text.setEnabled(true);
            history_text.setClickable(false);
            history_text.setEnabled(false);

            new_pres_text.setTextColor(Color.parseColor("#ffffff"));
            new_pres_text.setBackgroundColor(Color.parseColor("#1E88E5"));

            history_text.setTextColor(Color.parseColor("#1E88E5"));
            history_text.setBackgroundColor(Color.parseColor("#ffffff"));
        }
    }

}