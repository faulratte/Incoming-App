package fhws.marcelgross.incoming.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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
import java.util.List;
import java.util.Map;

import fhws.marcelgross.incoming.Adapter.ContactAdapter;
import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.NetworkChangeReceiver;
import fhws.marcelgross.incoming.Objects.ContactObject;
import fhws.marcelgross.incoming.R;
import fhws.marcelgross.incoming.UrlHandler;
import fhws.marcelgross.incoming.Volley.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    private ProgressBar mProgressBar;
    private DBAdapter db;
    private View view;
    private ArrayList<ContactObject> allContacts;
    private ContactAdapter mContactAdapter;
    private RecyclerView mRecyclerView;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        db = new DBAdapter(getActivity());
        mProgressBar = (ProgressBar) view.findViewById(R.id.contacts_progressBar);
        if ( NetworkChangeReceiver.getInstance().isConnected){
            loadData();
        }
        allContacts = db.getAllContacts();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_list);
        setUpView( new ArrayList<>(allContacts));

        return view;
    }

    public void setUpView(ArrayList<ContactObject> contactObjects){
        if (contactObjects.isEmpty()){
            mProgressBar.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mContactAdapter = new ContactAdapter(contactObjects, R.layout.contact_card, getActivity());
            mRecyclerView.setAdapter(mContactAdapter);
            mContactAdapter.notifyDataSetChanged();
        }
    }

    private void loadData(){
        final Genson genson = new Genson();
        JsonArrayRequest req = new JsonArrayRequest(UrlHandler.CONTACTURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ArrayList<ContactObject> objects;
                        objects = genson.deserialize(response.toString(), new GenericType<ArrayList<ContactObject>>() {});
                        checkViewReload(objects);
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
        AppController.getInstance(getActivity()).addToRequestQueue(req, "contact_request");
    }

    private void checkViewReload(ArrayList<ContactObject> contactObject){
        int counter = 0;
        ArrayList<Long> ids = db.getAllContactIDs();
        for (int i = 0; i<contactObject.size(); i++){
            if (!ids.contains(contactObject.get(i).getId()))
                ++counter;
        }
        db.saveContacts(contactObject);
        if (counter>0){
            setUpView(db.getAllContacts());
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if (query.isEmpty()){
            flush();
        } else {
            final ArrayList<ContactObject> filteredModelList = filter(allContacts, query);
            mContactAdapter.animateTo(filteredModelList);
            mRecyclerView.scrollToPosition(0);
        }

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private ArrayList<ContactObject> filter(ArrayList<ContactObject> contactObjects, String query) {
        query = query.toLowerCase().trim();

        final ArrayList<ContactObject> filteredContactList = new ArrayList<>();
        for (ContactObject contactObject : contactObjects) {
            String name = contactObject.getFullName().toLowerCase();
            if (name.contains(query)){
                filteredContactList.add(contactObject);
            }
        }
        return filteredContactList;
    }

    private void flush(){
        allContacts = db.getAllContacts();
        setUpView(allContacts);
    }
    @Override
    public boolean onClose() {
        flush();
        return false;
    }
}
