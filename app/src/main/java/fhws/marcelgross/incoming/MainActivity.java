package fhws.marcelgross.incoming;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import fhws.marcelgross.incoming.Adapter.AppSectionsAdapter;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    private AppSectionsAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAppSectionsPagerAdapter = new AppSectionsAdapter(getFragmentManager());

            final ActionBar actionBar = getSupportActionBar();

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


  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

    return super.onOptionsItemSelected(item);
    }*/



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
}
