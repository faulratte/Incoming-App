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
        final String title = intent.getStringExtra("title");
        final String date = intent.getStringExtra("date");
        final String time = intent.getStringExtra("time");
        final String description = intent.getStringExtra("description");
        final String location = intent.getStringExtra("location");
        String contactperson = intent.getStringExtra("contactperson");
        String category = intent.getStringExtra("category");


        title_tv.setText(title);
        date_tv.setText(date);
        time_tv.setText(time);
        contactperson_tv.setText(contactperson);
        category_tv.setText(category);
        description_tv.setText(description);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long[] startEndTime = hf.getStartAndEndTime(date, time);

                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.Events.TITLE, title);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, description);

                //year, month, day
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startEndTime[0]);
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, startEndTime[1]);
//                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
