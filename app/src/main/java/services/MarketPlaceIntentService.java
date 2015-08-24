package services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.sinapp.sharathsind.tradepost.MarketPlaceFragment;

import java.util.ArrayList;
import java.util.List;

import Model.MarketPlaceData;

/**
 * Created by HenryChiang on 15-08-23.
 */
public class MarketPlaceIntentService extends IntentService {

    public static final String TAG = "IntentService";
    public static final String MARKET_PLACE_ARRAYLIST = "marketPlaceData";


    public ArrayList<MarketPlaceData> dataReceived;

    public MarketPlaceIntentService() {
        super(MarketPlaceIntentService.class.getName());
    }


    @Override
    protected void onHandleIntent(Intent intent) {


    }
}
