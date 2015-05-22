package fhws.marcelgross.incoming.Adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import fhws.marcelgross.incoming.NewsActivity;
import fhws.marcelgross.incoming.R;

import fhws.marcelgross.incoming.Objects.NewsObject;

/**
 * Created by Marcel on 29.04.2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<NewsObject> newsObjects;
    private int rowLayout;
    private Context context;
    private FragmentManager fragmentManager;

    public NewsAdapter(List<NewsObject> newsObjects, int rowLayout, Context context, FragmentManager fragmentManager){
        this.newsObjects = newsObjects;
        this.rowLayout = rowLayout;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsObject newsObject = newsObjects.get(position);
        holder.title.setText(newsObject.getTitle());
        holder.date.setText(newsObject.getDate());
        holder.description.setText(newsObject.getText());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, NewsActivity.class);
                intent.putExtra("title", newsObject.getTitle());
                intent.putExtra("date", newsObject.getDate());
                intent.putExtra("description", newsObject.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsObjects == null ? 0 : newsObjects.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public TextView description;
        public NewsObject object;

        public ViewHolder(final View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.eventsCard_title_tv);
            date = (TextView) itemView.findViewById(R.id.eventsCard_date_tv);
            description = (TextView) itemView.findViewById(R.id.newsCard_description_tv);

        }
    }
}
