package com.example.bulletinator;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class BulletinActivity extends Activity {

    private Bulletin bulletin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Show the Up button in the action bar.
        setupActionBar();

        bulletin = (Bulletin) getIntent().getSerializableExtra("BULLETIN");
        ImageView image;

        if (bulletin.getBodyText() == null) {
            // Flyer, only set up image
            setContentView(R.layout.bulletin_flyer);

            image = (ImageView) findViewById(R.id.flyer);
            image.setImageResource(bulletin.getImageId());
            image.setScaleType(ScaleType.CENTER_INSIDE);
            // Fix white space
            image.setAdjustViewBounds(true);
        } else {
            // No flyer, set up image, text, contact
            setContentView(R.layout.activity_bulletin);

            image = (ImageView) findViewById(R.id.flyer);
            image.setImageResource(bulletin.getImageId());

            TextView body = (TextView) findViewById(R.id.bodyText);
            body.setText(bulletin.getBodyText());

            TextView contact = (TextView) findViewById(R.id.contactText);
            contact.setText(bulletin.getContactInfo());
        }
        this.setTitle(bulletin.getTitle());
    }

    /**
     * Set up the {@link android.app.ActionBar}.
     */
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.b, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
