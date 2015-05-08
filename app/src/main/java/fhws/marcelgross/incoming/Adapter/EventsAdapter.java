package fhws.marcelgross.incoming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import fhws.marcelgross.incoming.EventsActivity;
import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.R;

/**
 * Created by Marcel on 30.04.2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<EventsObject> eventsObjects;
    private int rowLayout;
    private Context context;

    private HelperFunctions hf = new HelperFunctions();

    public EventsAdapter(List<EventsObject> eventsObjects, int rowLayout, Context context){
        this.eventsObjects = eventsObjects;
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
        final EventsObject eventsObject = eventsObjects.get(position);
        holder.title.setText(eventsObject.getTitle());
        holder.date.setText(eventsObject.getTermin());

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                long[] startEndTime = hf.getStartAndEndTime(eventsObject.getTermin(), eventsObject.getTime());

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, eventsObject.getTitle());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventsObject.getLocation());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, eventsObject.getText());

                //year, month, day
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startEndTime[0]);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startEndTime[1]);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                context.startActivity(intent);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EventsActivity.class);
                intent.putExtra("title", eventsObject.getTitle());
                intent.putExtra("date", eventsObject.getTermin());
                intent.putExtra("time", eventsObject.getTime());
                intent.putExtra("contactperson", eventsObject.getContactperson());
                intent.putExtra("category", eventsObject.getCategory());
                intent.putExtra("description", eventsObject.getText());
                intent.putExtra("location", eventsObject.getLocation());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventsObjects == null ? 0 : eventsObjects.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView date;
        public ImageButton button;

        public ViewHolder(final View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.eventsCard_title_tv);
            date = (TextView) itemView.findViewById(R.id.eventsCard_date_tv);
            button = (ImageButton) itemView.findViewById(R.id.eventsCard_btn);

        }
    }


}
