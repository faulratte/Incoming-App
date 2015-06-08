package fhws.marcelgross.incoming.Fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
public class ContactFragment extends Fragment {


    private ProgressBar mProgressBar;
    private DBAdapter db;
    private View view;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        db = new DBAdapter(getActivity());
        mProgressBar = (ProgressBar) view.findViewById(R.id.contacts_progressBar);
        if ( NetworkChangeReceiver.connection){
            loadData();
        }
        setUpView(db.getAllContacts());

        return view;
    }

    public void setUpView(ArrayList<ContactObject> contactObjects){
        if (contactObjects.isEmpty()){
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
            RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_list);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            ContactAdapter mContactAdapter = new ContactAdapter(contactObjects, R.layout.contact_card, getActivity());
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


}
