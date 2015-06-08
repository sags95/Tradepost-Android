package com.sinapp.sharathsind.tradepost;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by HenryChiang on 15-06-06.
 */
public class SingleListingActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_listing_view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Need to handle the text of actionbar (Edit or Offer)
        MenuItem item = menu.add(Menu.NONE,R.id.actionbar_single_listing,1,R.string.actionbar_single_listing);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.actionbar_single_listing) {
            Toast.makeText(this, "OFFER?", Toast.LENGTH_SHORT)
                    .show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
