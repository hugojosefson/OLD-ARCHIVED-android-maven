package com.totsp.test;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class Test extends Activity {
    
    private TextView title;   

    @Override
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	
	setContentView(R.layout.test);

	title = (TextView) findViewById(R.id.title);
	
    }

    
}
