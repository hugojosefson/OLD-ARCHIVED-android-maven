package com.totsp.sample;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleMath extends Activity {

    private ISimpleMathService mathService;
    private boolean bound;

    private Button add;
    private EditText inputa;
    private EditText inputb;

    private ServiceConnection connection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mathService = ISimpleMathService.Stub.asInterface(service);
            Toast.makeText(SimpleMath.this, "connected to Service", Toast.LENGTH_SHORT).show();
            bound = true;
            callService();
        }
        public void onServiceDisconnected(ComponentName className) {
            mathService = null;
            Toast.makeText(SimpleMath.this, "disconnected from Service", Toast.LENGTH_SHORT).show();
            bound = false;
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.simple_math);

        add = (Button) findViewById(R.id.add);
        inputa = (EditText) findViewById(R.id.inputa);
        inputb = (EditText) findViewById(R.id.inputb);

        add.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Log.v(SimpleMath.class.getSimpleName(), "bound = " + bound);
                if (!bound) {
                    Log.v(SimpleMath.class.getSimpleName(), "calling bindService");
                    // TODO research this call, can be simpler without
                    // connection?
                    SimpleMath.this.bindService(new Intent(SimpleMath.this, SimpleMathService.class), connection,
                            Context.BIND_AUTO_CREATE);
                } else {
                    callService();
                }
            }
        });
    }

    private void callService() {
        int ina = 0;
        int inb = 0;

        if (bound) {
            try {
                ina = Integer.parseInt(inputa.getText().toString());
                inb = Integer.parseInt(inputb.getText().toString());
                int result = mathService.add(ina, inb);
                Toast.makeText(SimpleMath.this, "service call result - " + result, Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                Toast.makeText(SimpleMath.this, "ERROR, inputs not present", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(SimpleMath.this, "ERROR, inputs not numeric", Toast.LENGTH_SHORT).show();
            } catch (DeadObjectException e) {
                Toast.makeText(SimpleMath.this, "ERROR, dead binder", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SimpleMath.this, "ERROR, cannot call service while not bound", Toast.LENGTH_SHORT).show();
        }
    }

}