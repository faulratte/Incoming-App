package fhws.marcelgross.incoming.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.preference.PreferenceManager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;
import java.util.Locale;

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.EventsAdapter;
import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EventsFragment extends Fragment {


    private static final String URL = "http://backend.applab.fhws.de:8080/incoming/api/events";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private EventsAdapter mEventsAdapter;
    private DBAdapter db;
    private View view;
    private boolean[] checkedBoxes = new boolean[4];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < checkedBoxes.length; i++){
            checkedBoxes[i] = true;
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_events, container, false);
        db = new DBAdapter(getActivity());
        new BackgroundTask().execute();
        setUpView(db.getAllEvents());

        mProgressBar = (ProgressBar) view.findViewById(R.id.events_progressBar);

        return view;
    }

    public void setUpView(ArrayList<EventsObject> eventsObjects){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.events_list);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mEventsAdapter = new EventsAdapter(eventsObjects, R.layout.events_card, getActivity());
        mRecyclerView.setAdapter(mEventsAdapter);
        mEventsAdapter.notifyDataSetChanged();
    }

    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<EventsObject>>{

        @Override
        protected ArrayList<EventsObject> doInBackground(Void... voids) {
            ArrayList<EventsObject> objects;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(URL);
            get.addHeader("accept", "application/json");
            try {
                Genson genson = new Genson();
                HttpResponse response = httpClient.execute(get);
                String reply = IOUtils.toString(response.getEntity().getContent());
                objects = genson.deserialize(reply, new GenericType<ArrayList<EventsObject>>() {});
            } catch (Exception e){
                e.printStackTrace();
                Log.d("Exception", e.getMessage());
                objects = null;
            }
            return objects;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<EventsObject> eventsObjects) {
            super.onPostExecute(eventsObjects);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);


            int counter = 0;
            ArrayList<String> titles = db.getAllEventsTitle();

            for (int i = 0; i < eventsObjects.size(); i++){
                if (!titles.contains(eventsObjects.get(i).getTitle())){
                    ++counter;
                }
            }
            db.saveEvents(eventsObjects);
            if (counter>0){
                setUpView(db.getAllEvents());
            }
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
        String test = "";
        for (boolean c : checkedBoxes){
            test += String.valueOf(c)+" ";
        }
        Toast.makeText(getActivity(), test, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}
