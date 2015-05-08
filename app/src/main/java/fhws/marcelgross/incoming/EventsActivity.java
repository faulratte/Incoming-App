package fhws.marcelgross.incoming;

import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;

import fhws.marcelgross.incoming.Adapter.HelperFunctions;


public class EventsActivity extends ActionBarActivity {

    private TextView title_tv, date_tv, time_tv, contactperson_tv, category_tv, description_tv;
    private ImageButton button;
    private HelperFunctions hf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        hf = new HelperFunctions();

        title_tv = (TextView) findViewById(R.id.event_title_tv);
        date_tv = (TextView) findViewById(R.id.event_date_tv);
        time_tv = (TextView) findViewById(R.id.event_time_tv);
        contactperson_tv = (TextView) findViewById(R.id.event_contactperson_tv);
        category_tv = (TextView) findViewById(R.id.event_category_tv);
        description_tv = (TextView) findViewById(R.id.event_description_tv);

        button = (ImageButton) findViewById(R.id.calendar_ib);

        final Intent intent = getIntent();
        title_tv.setText(intent.getStringExtra("title"));
        date_tv.setText(intent.getStringExtra("date"));
        time_tv.setText(intent.getStringExtra("time"));
        contactperson_tv.setText(intent.getStringExtra("contactperson"));
        category_tv.setText(intent.getStringExtra("category"));
        description_tv.setText(intent.getStringExtra("description"));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long[] startEndTime = hf.getStartAndEndTime(intent.getStringExtra("date"), intent.getStringExtra("time"));

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, intent.getStringExtra("title"));
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, intent.getStringExtra("location"));
                intent.putExtra(CalendarContract.Events.DESCRIPTION, intent.getStringExtra("description"));

                //year, month, day
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startEndTime[0]);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startEndTime[1]);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                startActivity(intent);
            }
        });
    }




}
