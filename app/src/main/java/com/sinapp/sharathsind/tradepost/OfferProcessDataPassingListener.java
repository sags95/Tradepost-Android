package com.sinapp.sharathsind.tradepost;

import android.graphics.Bitmap;

import java.util.ArrayList;

import Model.OfferProcessItem;

/**
 * Created by HenryChiang on 15-07-18.
 */

public interface OfferProcessDataPassingListener {

        void passString(String string,int condition);
        void passBitmap(Bitmap bm);
        void passInt(int i);
        void passOfferProcessItem(ArrayList<OfferProcessItem> offerProcessItemArrayList);
    }



