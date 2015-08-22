package fhws.marcelgross.incoming.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.EventsAdapter;
import fhws.marcelgross.incoming.Adapter.NetworkChangeReceiver;
import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.R;
import fhws.marcelgross.incoming.UrlHandler;
import fhws.marcelgross.incoming.Volley.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {

    private TextView noEvents;
    private DBAdapter db;
    private View view;
    private boolean[] checkedBoxes = new boolean[4];
    private final String PREFNAME = "event_box";
    private  RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loadSavedPreferences();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_events, container, false);
        db = new DBAdapter(getActivity());
        noEvents = (TextView) view.findViewById(R.id.noEvents);
        if (NetworkChangeReceiver.getInstance().isConnected){
            loadData();
        }

        mRecyclerView = (RecyclerView) view.findViewById(R.id.events_list);
        setUpView(db.getAllEvents(checkedBoxes));

        return view;
    }

    public void setUpView(ArrayList<EventsObject> eventsObjects){
            if (eventsObjects.isEmpty() && checkBoxes()){
                noEvents.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                noEvents.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);

                final LinearLayoutManager llm = new LinearLayoutManager(getActivity());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mRecyclerView.setLayoutManager(llm);
                mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                EventsAdapter mEventsAdapter = new EventsAdapter(eventsObjects, R.layout.events_card, getActivity());
                mRecyclerView.setAdapter(mEventsAdapter);
                mEventsAdapter.notifyDataSetChanged();
            }

    }

    private void loadData(){
        final Genson genson = new Genson();
        JsonArrayRequest req = new JsonArrayRequest(UrlHandler.EVENTLINK,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<EventsObject> objects;
                        objects = genson.deserialize(response.toString(), new GenericType<ArrayList<EventsObject>>() {});
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
        AppController.getInstance(getActivity()).addToRequestQueue(req, "events_request");
    }

    private void checkViewReaload(ArrayList<EventsObject> eventObjects){
        int counter = 0;
        ArrayList<Long> ids = db.getAllEventsIDs();
        for (int i = 0; i<eventObjects.size(); i++){
            if (!ids.contains(eventObjects.get(i).getId()))
                ++counter;
        }
        db.saveEvents(eventObjects);
        if (counter>0){
            setUpView(db.getAllEvents(checkedBoxes));
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.events_menu, menu);
        menu.getItem(0).setChecked(checkedBoxes[0]);
        menu.getItem(1).setChecked(checkedBoxes[1]);
        menu.getItem(2).setChecked(checkedBoxes[2]);
        menu.getItem(3).setChecked(checkedBoxes[3]);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        item.setChecked(!item.isChecked());

        switch (item.getItemId()){
            case R.id.action_fhws:
                checkedBoxes[0] = item.isChecked();
                break;
            case R.id.action_uni:
                checkedBoxes[1] = item.isChecked();
                break;
            case R.id.action_ics:
                checkedBoxes[2] = item.isChecked();
                break;
            case R.id.action_holiday:
                checkedBoxes[3] = item.isChecked();
                break;
            default:
                break;
        }

            savePreferences();
            setUpView(db.getAllEvents(checkedBoxes));

        return super.onOptionsItemSelected(item);
    }

    private void savePreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for (int i = 0; i < checkedBoxes.length; i++){
            editor.putBoolean(PREFNAME +i, checkedBoxes[i]);
        }

        editor.apply();
    }

    private void loadSavedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        for (int i = 0; i < checkedBoxes.length; i++){
            checkedBoxes[i] = sharedPreferences.getBoolean(PREFNAME +i, true);
        }
    }

    private boolean checkBoxes(){
        boolean result = false;
        if(checkedBoxes[0] && checkedBoxes[1] && checkedBoxes[2] && checkedBoxes[3])
            result = true;
        return result;
    }

}
