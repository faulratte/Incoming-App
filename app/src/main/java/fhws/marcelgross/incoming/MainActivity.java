package fhws.marcelgross.incoming;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fhws.marcelgross.incoming.Adapter.AppSectionsAdapter;

public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private ViewPager mViewPager;
    private ActionBar actionBar;
    public static boolean isConnected;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isConnected = isNetworkAvailable();

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (!isConnected)
        {
            menu.add(Menu.NONE, R.string.noSignal, Menu.NONE, R.string.noSignal).setIcon(R.mipmap.ic_no_signal)
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        }


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.string.noSignal:
                Toast.makeText(this, R.string.noInternetConection, Toast.LENGTH_LONG).show();
            break;
        }
        return super.onOptionsItemSelected(item);
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
