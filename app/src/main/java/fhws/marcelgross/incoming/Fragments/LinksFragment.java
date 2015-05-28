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

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.LinkAdapter;
import fhws.marcelgross.incoming.MainActivity;
import fhws.marcelgross.incoming.Objects.LinksObject;
import fhws.marcelgross.incoming.R;
import fhws.marcelgross.incoming.UrlHandler;
import fhws.marcelgross.incoming.Volley.AppController;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinksFragment extends Fragment {

    private ProgressBar mProgressBar;
    private DBAdapter db;
    private View view;

    private ArrayList<LinksObject> objects;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_links, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.links_progressBar);
        db = new DBAdapter(getActivity());
        if (MainActivity.isConnected){
            loadData();
        }
        setUpView(db.getAllLinks());

        return view;
    }

    public void setUpView(ArrayList<LinksObject> linksObjects){

        if (linksObjects.isEmpty()){
            mProgressBar.setVisibility(View.VISIBLE);
        } else{
            mProgressBar.setVisibility(View.GONE);
            RecyclerView mRecyclerView = (RecyclerView)view.findViewById(R.id.links_list);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(llm);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            LinkAdapter mLinkAdapter = new LinkAdapter(linksObjects, R.layout.links_card, getActivity());
            mRecyclerView.setAdapter(mLinkAdapter);
            mLinkAdapter.notifyDataSetChanged();
        }
    }

    private void loadData(){
        final Genson genson = new Genson();
        JsonArrayRequest req = new JsonArrayRequest(UrlHandler.LINKSURL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        objects = genson.deserialize(response.toString(), new GenericType<ArrayList<LinksObject>>() {});
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

    private void checkViewReaload(ArrayList<LinksObject> linksObjects){
        int counter = 0;
        ArrayList<Long> ids = db.getAllLinksIDs();
        for (int i = 0; i<linksObjects.size(); i++){
            if (!ids.contains(linksObjects.get(i).getId()))
                ++counter;
        }
        db.saveLinks(linksObjects);
        if (counter>0){
            setUpView(db.getAllLinks());
        }
    }
}
