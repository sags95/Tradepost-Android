package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.android.gms.iid.InstanceIDListenerService;

public class IDService extends InstanceIDListenerService {
    public IDService() {
    }

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

    }
}
