package com.sinapp.sharathsind.tradepost;

import com.google.android.gms.iid.InstanceIDListenerService;

public class IDService extends InstanceIDListenerService {
    public IDService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
