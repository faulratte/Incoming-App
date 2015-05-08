package fhws.marcelgross.incoming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import fhws.marcelgross.incoming.EventsActivity;
import fhws.marcelgross.incoming.NewsActivity;
import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.Objects.LinksObject;
import fhws.marcelgross.incoming.Objects.NewsObject;
import fhws.marcelgross.incoming.R;

/**
 * Created by Marcel on 08.05.2015.
 */
public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.ViewHolder>{

    private List<LinksObject> linksObjects;
    private int rowLayout;
    private Context context;

    public LinkAdapter(List<LinksObject> linksObjects, int rowLayout, Context context){
        this.linksObjects = linksObjects;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final LinksObject linksObject = linksObjects.get(position);
        holder.title.setText(linksObject.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(linksObject.getUrl()));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return linksObjects == null ? 0 : linksObjects.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ViewHolder(final View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.linksCard_title_tv);

        }
    }
}
