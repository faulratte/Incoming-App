package fhws.marcelgross.incoming.Adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

import fhws.marcelgross.incoming.Fragments.ContactFragment;
import fhws.marcelgross.incoming.Fragments.EventsFragment;
import fhws.marcelgross.incoming.Fragments.LinksFragment;
import fhws.marcelgross.incoming.Fragments.NavigationFragment;
import fhws.marcelgross.incoming.Fragments.NewsFragment;
import fhws.marcelgross.incoming.R;


/**
 * Created by Marcel on 24.04.2015.
 */
public class AppSectionsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public AppSectionsAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
        fragments.add(new NewsFragment());
        fragments.add(new EventsFragment());
        fragments.add(new NavigationFragment());
        fragments.add(new ContactFragment());
        fragments.add(new LinksFragment());

    }

   /* @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new NewsFragment();
            case 1:
                return new EventsFragment();
            case 2:
                return new NavigationFragment();
            case 3:
                return new ContactFragment();
            case 4:
                return new LinksFragment();
            default:
                return null;
        }
    }*/

    @Override
    public Fragment getItem(int i) {
       return fragments.get(i);
    }
    //number of tabs
    @Override
    public int getCount() {
        return fragments.size();
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
                icon = R.mipmap.ic_tab_navigation;
                break;
        }
        return icon;
    }
}