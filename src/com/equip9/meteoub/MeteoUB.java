package com.equip9.meteoub;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MeteoUB extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView temperatura = (TextView) findViewById(R.id.temperatura);
        temperatura.setText("suooola!");
    }
}