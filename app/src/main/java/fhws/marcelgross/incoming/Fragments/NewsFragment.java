package fhws.marcelgross.incoming.Fragments;


import android.app.FragmentManager;
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
import android.widget.Toast;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.ArrayList;

import fhws.marcelgross.incoming.Adapter.DBAdapter;
import fhws.marcelgross.incoming.Adapter.NewsAdapter;
import fhws.marcelgross.incoming.Objects.NewsObject;
import fhws.marcelgross.incoming.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {

    private static final String URL = "http://backend.applab.fhws.de:8080/incoming/api/news";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private NewsAdapter mNewsAdapter;
    private DBAdapter db;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        db = new DBAdapter(getActivity());
        new BackgroundTask().execute();
        setUpView(db.getAllNews());

        mProgressBar = (ProgressBar) view.findViewById(R.id.news_progressBar);

        return view;
    }

    public void setUpView(ArrayList<NewsObject> newsObjects){

        mRecyclerView = (RecyclerView)view.findViewById(R.id.list);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mNewsAdapter = new NewsAdapter(newsObjects, R.layout.news_card, getActivity(), getFragmentManager());
        mRecyclerView.setAdapter(mNewsAdapter);
        mNewsAdapter.notifyDataSetChanged();
    }


    class BackgroundTask extends AsyncTask<Void, Void, ArrayList<NewsObject>> {

        @Override
        protected ArrayList<NewsObject> doInBackground(Void... params) {
            ArrayList<NewsObject> objects;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet get = new HttpGet(URL);
            get.addHeader("accept", "application/json");
            try {
                Genson genson = new Genson();
                HttpResponse response = httpClient.execute(get);
                String reply = IOUtils.toString(response.getEntity().getContent());
                objects = genson.deserialize(reply, new GenericType<ArrayList<NewsObject>>() {});

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
        protected void onPostExecute(ArrayList<NewsObject> newsObjects) {
            super.onPostExecute(newsObjects);
            mRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);

            int counter = 0;
            ArrayList<String> titles = db.getAllNewsTitle();

            for (int i = 0; i<newsObjects.size();i++){
                if (!titles.contains(newsObjects.get(i).getTitle())){
                    ++counter;
                }
            }
            db.saveNews(newsObjects);
            if (counter>0){
                setUpView(db.getAllNews());
            }
        }
    }

}
