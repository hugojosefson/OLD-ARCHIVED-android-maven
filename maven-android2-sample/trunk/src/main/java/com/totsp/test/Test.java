package com.totsp.test;


public class Test extends Activity {
    
    private TextView title;   

    @Override
    protected void onCreate(Bundle icicle) {
	super.onCreate(icicle);
	
	setContentView(R.layout.test);

	title = (TextView) findViewById(R.id.title);
	
    }

    
}
