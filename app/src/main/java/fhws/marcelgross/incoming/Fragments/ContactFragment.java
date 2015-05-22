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

import fhws.marcelgross.incoming.Adapter.ContactAdapter;
import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.MainActivity;
import fhws.marcelgross.incoming.Objects.ContactObject;
import fhws.marcelgross.incoming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private static final String URL = "http://backend.applab.fhws.de:8080/incoming/api/person";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private ContactAdapter mContactAdapter;
    private DBAdapter db;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact, container, false);
        db = new DBAdapter(getActivity());
        new BackgroundTask().execute();
        setUpView(db.getAllContacts());


        mProgressBar = (ProgressBar) view.findViewById(R.id.contacts_progressBar);

        return view;
    }

    public void setUpView(ArrayList<ContactObject> contactObjects){

        mRecyclerView = (RecyclerView) view.findViewById(R.id.contact_list);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mContactAdapter = new ContactAdapter(contactObjects, R.layout.contact_card, getActivity());
        mRecyclerView.setAdapter(mContactAdapter);
        mContactAdapter.notifyDataSetChanged();
    }

    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<ContactObject>>{


        @Override
        protected ArrayList<ContactObject> doInBackground(Void... voids) {
            ArrayList<ContactObject> contacts;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(URL);
            get.addHeader("accept", "application/json");
            try {
                Genson genson = new Genson();
                HttpResponse response = httpClient.execute(get);
                String reply = IOUtils.toString(response.getEntity().getContent());
                contacts = genson.deserialize(reply, new GenericType<ArrayList<ContactObject>>() {});
            } catch (Exception e){
                e.printStackTrace();
                Log.d("Exception", e.getMessage());
                contacts = null;
            }

            return contacts;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            mRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(ArrayList<ContactObject> contactObjects) {
            super.onPostExecute(contactObjects);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            int counter = 0;
            ArrayList<Long> ids = db.getAllContactIDs();
            for (int i = 0; i < contactObjects.size(); i++){
                if (!ids.contains(contactObjects.get(i).getId()))
                    ++counter;
            }
            db.saveContacts(contactObjects);
            if(counter>0)
                setUpView(db.getAllContacts());
        }
    }


}
