package fhws.marcelgross.incoming.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import fhws.marcelgross.incoming.R;

import fhws.marcelgross.incoming.Objects.NewsObject;

/**
 * Created by Marcel on 29.04.2015.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

    private List<NewsObject> newsObjects;
    private int rowLayout;

    public NewsAdapter(List<NewsObject> newsObjects, int rowLayout){
        this.newsObjects = newsObjects;
        this.rowLayout = rowLayout;
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
                Toast.makeText(holder.itemView.getContext(), newsObject.getTitle(), Toast.LENGTH_SHORT).show();
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
            title = (TextView) itemView.findViewById(R.id.newsCard_title_tv);
            date = (TextView) itemView.findViewById(R.id.newsCard_date_tv);
            description = (TextView) itemView.findViewById(R.id.newsCard_description_tv);

        }
    }
}
