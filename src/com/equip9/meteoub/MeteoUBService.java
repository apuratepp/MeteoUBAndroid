package com.equip9.meteoub;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MeteoUBService extends Service {
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onCreate() {
		// super.onCreate();
		MeteoUB meteo = new MeteoUB();
		meteo.mostrarComentari("Service onCreate!!");
		Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
	}
	@Override
	public void onDestroy() {
		Toast.makeText(this, "Service destroyed!", Toast.LENGTH_LONG).show();
	}
}
