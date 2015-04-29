package fhws.marcelgross.incoming.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;

import fhws.marcelgross.incoming.Fragments.DummySectionFragment;
import fhws.marcelgross.incoming.Fragments.NewsFragment;
import fhws.marcelgross.incoming.R;


/**
 * Created by Marcel on 24.04.2015.
 */
public class AppSectionsAdapter extends FragmentPagerAdapter {

    public AppSectionsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new NewsFragment();

            default:
                Fragment fragment = new DummySectionFragment();
                Bundle args = new Bundle();
                args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
                fragment.setArguments(args);
                return fragment;
        }
    }

    //Anzahl der Tabs
    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Section " + (position + 1);
    }

    public int getIcon(int position){
        int icon;
        switch (position){
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
        return icon;
    }
}