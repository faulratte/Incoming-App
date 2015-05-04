package fhws.marcelgross.incoming.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import fhws.marcelgross.incoming.Objects.EventsObject;
import fhws.marcelgross.incoming.R;

/**
 * Created by Marcel on 30.04.2015.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{

    private List<EventsObject> eventsObjects;
    private int rowLayout;
    private Context context;

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


                int[] dateArray = dateStringToIntArray(eventsObject.getTermin());
                long[] startEndTime = getStartAndEndTime(eventsObject.getTermin(), eventsObject.getTime());



                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, eventsObject.getTitle());
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, eventsObject.getLocation());
                intent.putExtra(CalendarContract.Events.DESCRIPTION, eventsObject.getLocation());

                //year, month, day
                GregorianCalendar calDate = new GregorianCalendar(dateArray[2], dateArray[1]-1, dateArray[0]);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startEndTime[0]);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        startEndTime[1]);

//                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                context.startActivity(intent);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, NewsActivity.class);
//                intent.putExtra("title", newsObject.getTitle());
//                intent.putExtra("date", newsObject.getDate());
//                intent.putExtra("description", newsObject.getText());
//                context.startActivity(intent);
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

    public int[] dateStringToIntArray(String date){
        String[] stringArray = date.split("\\.");
        int[] dateArray = new int[stringArray.length];
        for (int i = 0; i < dateArray.length; i++){
            dateArray[i] = Integer.parseInt(stringArray[i]);
        }

        return dateArray;
    }
    public int[] timeStringToIntArray(String time) {
        String[] stringArray = time.split(":");
        int[] timeArray = new int[stringArray.length];
        for (int i = 0; i < timeArray.length; i++) {
            timeArray[i] = Integer.parseInt(stringArray[i]);
        }
        return timeArray;
    }
    public long[] getStartAndEndTime(String date, String time){
        int[] timeArray = timeStringToIntArray(time);
        int[] dateArray = dateStringToIntArray(date);
        long[] startAndEndTime = new long[2];

        for (int i = 0; i < startAndEndTime.length; i++){
            Calendar c = Calendar.getInstance();
            // year, month, day, hourOfDay, minute
            if (i == 1)
                timeArray[0] += 1;
            c.set(dateArray[2], dateArray[1], dateArray[0], timeArray[0], timeArray[1]);
            startAndEndTime[i] = c.getTimeInMillis();
        }

        return startAndEndTime;
    }
}
