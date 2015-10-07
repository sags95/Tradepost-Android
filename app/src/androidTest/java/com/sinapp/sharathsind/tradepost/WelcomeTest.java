package com.sinapp.sharathsind.tradepost;

import android.test.ActivityInstrumentationTestCase2;

import datamanager.userdata;

/**
 * Created by sharathsind on 2015-10-01.
 */
public class WelcomeTest extends ActivityInstrumentationTestCase2<Welcome> {
Welcome sctivity;
    public WelcomeTest()
    {
        super(Welcome.class);
    }
    public WelcomeTest(Class<Welcome> activityClass) {
        super(activityClass);
    }

    public void testinit() throws Exception {
        if(userdata.userid!=0) {
            assertNotNull(userdata.items);
            assertNotNull(Constants.db);
        }
    }

    public void init() throws Exception {
assertNotNull(userdata.items);
        assertNotNull(Constants.db);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        sctivity=(Welcome)getActivity();


    }

}
