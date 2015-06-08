package fhws.marcelgross.incoming.Fragments;


import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.GPSTracker;
import fhws.marcelgross.incoming.Objects.NavigationObject;
import fhws.marcelgross.incoming.R;
import fhws.marcelgross.incoming.UrlHandler;
import fhws.marcelgross.incoming.Volley.AppController;


public class NavigationFragment extends MapFragment {

    private GoogleMap map;
    private boolean[] checkBoxes = new boolean[4];
    private final String PREFNAME = "poi_box";

    private DBAdapter db;
    private ArrayList<NavigationObject> objects;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSavedPreferences();
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        db = new DBAdapter(getActivity());
        map = getMap();
        loadData();
        map.setMyLocationEnabled(true);



        GPSTracker gps = new GPSTracker(getActivity());

        if (gps.canGetLocation()){
            map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gps.getLatitude(), gps.getLongitude())));
            map.animateCamera(CameraUpdateFactory.zoomTo(14), 2000, null);
        }
        setMarkers(db.getAllPois(checkBoxes));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.navigation_menue, menu);
        menu.getItem(0).setChecked(checkBoxes[0]);
        menu.getItem(1).setChecked(checkBoxes[1]);
        menu.getItem(2).setChecked(checkBoxes[2]);
        menu.getItem(3).setChecked(checkBoxes[3]);
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < checkBoxes.length; i++) {
            editor.putBoolean(PREFNAME + i, checkBoxes[i]);
        }
        editor.apply();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());

        switch (item.getItemId()) {
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
        setMarkers(db.getAllPois(checkBoxes));
        return super.onOptionsItemSelected(item);
    }

    private void loadSavedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (int i = 0; i < checkBoxes.length; i++) {
            checkBoxes[i] = sharedPreferences.getBoolean(PREFNAME + i, true);
        }
    }

    private void loadData() {
        final Genson genson = new Genson();
        JsonArrayRequest req = new JsonArrayRequest(UrlHandler.NAVILINK,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        objects = genson.deserialize(response.toString(), new GenericType<ArrayList<NavigationObject>>() {
                        });
                        checkViewReaload(objects);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("result", "Error: " + error.getMessage());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };
        AppController.getInstance(getActivity()).addToRequestQueue(req, "links_request");
    }

    private void checkViewReaload(ArrayList<NavigationObject> naviObjects) {
        int counter = 0;
        ArrayList<Long> ids = db.getAllPoiIDs();
        for (int i = 0; i < naviObjects.size(); i++) {
            if (!ids.contains(naviObjects.get(i).getId()))
                ++counter;
        }
        db.savePois(naviObjects);
        if (counter > 0) {
            setMarkers(db.getAllPois(checkBoxes));
        }
    }

    public void setMarkers(final ArrayList<NavigationObject> navigationObjects) {
        map.clear();

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                showDialog(navigationObjects, marker);

                return true;
            }
        });

        for (NavigationObject current : navigationObjects) {
            int drawable;
            if (current.getCategory().equals(getResources().getString(R.string.fhws_building))) {
                drawable = R.mipmap.ic_map_uni;
            } else if (current.getCategory().equals(getResources().getString(R.string.dormitory))) {
                drawable = R.mipmap.ic_map_dorm;
            } else if (current.getCategory().equals(getResources().getString(R.string.eatDrink))) {
                drawable = R.mipmap.ic_map_cafeteria;
            } else {
                drawable = R.mipmap.ic_map_lib;
            }
            LatLng tempLatLng = new LatLng(current.getLatitude(), current.getLongitude());
            Marker temp = map.addMarker(
                    new MarkerOptions().position(tempLatLng)
                            .title(current.getName())
                            .icon(BitmapDescriptorFactory.fromResource(drawable)));
        }
    }

    public int getArrayPosition(ArrayList<NavigationObject> navigationObjects, String marker){
        int arrayPosition = -1;
        for (int i = 0; i < navigationObjects.size(); i++) {
            if (marker.equals(navigationObjects.get(i).getName()))
                arrayPosition = i;
        }
        return arrayPosition;
    }

    public void showDialog(ArrayList<NavigationObject> navigationObjects, Marker marker){
        int arrayPosition = getArrayPosition(navigationObjects, marker.getTitle());
        final NavigationObject temp = navigationObjects.get(arrayPosition);

        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.marker);
        dialog.setTitle(temp.getName());
        TextView contact_person = (TextView) dialog.findViewById(R.id.marker_contactperson_tv);
        TextView opening_hours = (TextView) dialog.findViewById(R.id.marker_openinghours_tv);
        ImageButton call_btn = (ImageButton) dialog.findViewById(R.id.marker_phone_btn);
        if (arrayPosition >= 0) {
            contact_person.setText(String.valueOf(temp.getContactperson()));
            opening_hours.setText(String.valueOf(temp.getOeffnungszeiten()));
            call_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Intent.ACTION_DIAL);
                    String p = "tel:" + temp.getPhone();
                    i.setData(Uri.parse(p));
                    getActivity().startActivity(i);
                }
            });
        } else {
            call_btn.setVisibility(View.GONE);
        }
        dialog.show();
    }

}
