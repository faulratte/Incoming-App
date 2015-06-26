package fhws.marcelgross.incoming;

import android.app.Dialog;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import fhws.marcelgross.incoming.Adapter.AppSectionsAdapter;
import fhws.marcelgross.incoming.Adapter.GPSTracker;
import fhws.marcelgross.incoming.Adapter.NetworkChangeReceiver;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager mViewPager;
    private ActionBar actionBar;
    private static boolean alertshowed = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPlayServices();


        AppSectionsAdapter mAppSectionsPagerAdapter = new AppSectionsAdapter(getFragmentManager());

        actionBar = getSupportActionBar();

        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mAppSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);

            }
        });

        for (int i = 0; i < mAppSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setIcon(mAppSectionsPagerAdapter.getIcon(i))
                            .setTabListener(this));
        }

        registerNetworkChangeReceiver();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GPSTracker gps = new  GPSTracker(this);
        if (!gps.canGetLocation() && !alertshowed){
            gps.showSettingsAlert();
            alertshowed = true;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            getBaseContext().unregisterReceiver(NetworkChangeReceiver.getInstance());
        } catch (IllegalArgumentException e){
            Log.d("unregisterReceiver", e.getMessage());
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    private void checkPlayServices(){
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());

        if (status != ConnectionResult.SUCCESS)
        {
            int requestCode = 10;
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this, requestCode);
            dialog.show();
        }
    }

    private void registerNetworkChangeReceiver(){
        IntentFilter networkFilter = new IntentFilter();
        networkFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getBaseContext().registerReceiver(NetworkChangeReceiver.getInstance(), networkFilter);
    }

}
