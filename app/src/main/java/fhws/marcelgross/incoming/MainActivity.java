package fhws.marcelgross.incoming;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import fhws.marcelgross.incoming.Adapter.AppSectionsAdapter;


public class MainActivity extends Activity implements ActionBar.TabListener {

    private AppSectionsAdapter mAppSectionsPagerAdapter;
    private ViewPager mViewPager;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAppSectionsPagerAdapter = new AppSectionsAdapter(getFragmentManager());
        try{

            final ActionBar actionBar = getActionBar();

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
                int icon;
                switch (i){
                    case 0:
                        icon = R.mipmap.ic_tab_home;
                        break;
                    case 1:
                        icon = R.mipmap.ic_tab_events;
                        break;
                    case 2:
                        icon = R.mipmap.ic_tab_navigation;
                        break;
                    case 3:
                        icon = R.mipmap.ic_tab_contact;
                        break;
                    case 4:
                        icon = R.mipmap.ic_tab_links;
                        break;
                    default:
                        icon = -1;
                        break;
                }
                actionBar.addTab(
                        actionBar.newTab()
                                .setIcon(icon)
                                .setTabListener(this));
            }
        }catch (NullPointerException e){
            Log.d("null", e.getMessage());
        }

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



}
