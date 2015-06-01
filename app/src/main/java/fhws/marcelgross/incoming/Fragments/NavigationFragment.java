package fhws.marcelgross.incoming.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fhws.marcelgross.incoming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends MapFragment {

    private GoogleMap map;
    private boolean[] checkBoxes = new boolean[4];
    private final String PREFNAME = "poi_box";

    private static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    private static final LatLng KIEl = new LatLng(53.551, 9.993);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSavedPreferences();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map = getMap();
        Marker hamburg = map.addMarker(new MarkerOptions().position(HAMBURG).title("Hamburg"));
        Marker kiel = map.addMarker(new MarkerOptions().position(KIEl).title("Kiel"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(HAMBURG, 15));
        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_menue, menu);
        menu.getItem(0).setChecked(checkBoxes[0]);
        menu.getItem(1).setChecked(checkBoxes[1]);
        menu.getItem(2).setChecked(checkBoxes[2]);
        menu.getItem(3).setChecked(checkBoxes[3]);
    }

    private void savePreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < checkBoxes.length; i++){
            editor.putBoolean(PREFNAME + i, checkBoxes[i]);
        }
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());

        switch (item.getItemId()){
            case R.id.action_libary:
                checkBoxes[0] = item.isChecked();
                break;
            case R.id.action_eatDrink:
                checkBoxes[1] = item.isChecked();
                break;
            case R.id.action_dormitory:
                checkBoxes[2] = item.isChecked();
                break;
            case R.id.action_fhws_building:
                checkBoxes[3] = item.isChecked();
                break;
            default:
                break;
        }

        savePreferences();
        return super.onOptionsItemSelected(item);
    }

    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (int i = 0; i < checkBoxes.length; i++){
            checkBoxes[i] = sharedPreferences.getBoolean(PREFNAME + i, true);
        }
    }
}
