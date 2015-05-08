package fhws.marcelgross.incoming.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.LinkAdapter;
import fhws.marcelgross.incoming.Objects.LinksObject;
import fhws.marcelgross.incoming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LinksFragment extends Fragment {


    private static final String URL = "http://backend.applab.fhws.de:8080/incoming/api/index";


    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private LinkAdapter mLinkAdapter;
    private DBAdapter db;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_links, container, false);
        db = new DBAdapter(getActivity());
        new BackgroundTask().execute();
        setUpView(db.getAllLinks());

        mProgressBar = (ProgressBar) view.findViewById(R.id.links_progressBar);
        return view;
    }

    public void setUpView(ArrayList<LinksObject> linksObjects){

        mRecyclerView = (RecyclerView)view.findViewById(R.id.links_list);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mLinkAdapter = new LinkAdapter(linksObjects, R.layout.links_card, getActivity());
        mRecyclerView.setAdapter(mLinkAdapter);
        mLinkAdapter.notifyDataSetChanged();
    }


    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<LinksObject>> {

        @Override
        protected ArrayList<LinksObject> doInBackground(Void... params) {
            ArrayList<LinksObject> objects;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(URL);
            get.addHeader("accept", "application/json");
            try {
                Genson genson = new Genson();
                HttpResponse response = httpClient.execute(get);
                String reply = IOUtils.toString(response.getEntity().getContent());
                objects = genson.deserialize(reply, new GenericType<ArrayList<LinksObject>>() {});

            } catch (Exception e) {
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
        protected void onPostExecute(ArrayList<LinksObject> linksObjects) {
            super.onPostExecute(linksObjects);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            int counter = 0;
            ArrayList<String> titles = db.getAllLinksTitle();

            for (int i = 0; i<linksObjects.size();i++){
                if (!titles.contains(linksObjects.get(i).getTitle())){
                    ++counter;
                }
            }
            db.saveLinks(linksObjects);
            if (counter>0){
                setUpView(db.getAllLinks());
            }
        }
    }

}
