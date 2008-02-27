package com.totsp.sample;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SimpleMathService extends Service {
    
    private final ISimpleMathService.Stub binder = new ISimpleMathService.Stub() {
        public int add(int a, int b) {
            return a + b;
        }
    };
    
    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

}