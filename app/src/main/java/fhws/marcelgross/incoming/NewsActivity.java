package fhws.marcelgross.incoming;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;


public class NewsActivity extends ActionBarActivity {

    private CardView mCardView;
    private TextView title_tv;
    private TextView date_tv;
    private TextView description_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        title_tv = (TextView) findViewById(R.id.news_title_tv);
        date_tv = (TextView) findViewById(R.id.news_date_tv);
        description_tv = (TextView) findViewById(R.id.news_description_tv);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String date = intent.getStringExtra("date");
        String description = intent.getStringExtra("description");

        title_tv.setText(title);
        date_tv.setText(date);
        description_tv.setText(description);

    }
}