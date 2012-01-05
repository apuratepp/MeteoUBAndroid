package com.equip9.meteoub;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
// import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.util.TimeZone;
import java.util.SimpleTimeZone;
import android.content.Intent;

public class MeteoUB extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView temperatura = (TextView) findViewById(R.id.temperatura);
        temperatura.setText("À?¼C");
        refreshTemperatura();
    }
	private void refreshTemperatura() {
        
		URL url;
		try{
			String meteoFeed = "http://ulisses.fis.ub.edu:8001/services/meteo/meteo.xml";
			url = new URL(meteoFeed);
			
			URLConnection connection;
			connection = url.openConnection();
			
			HttpURLConnection httpConnection = (HttpURLConnection)connection;
			int responseCode = httpConnection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				InputStream in = httpConnection.getInputStream();
				
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				
				Document dom = db.parse(in);
				Element docEle = dom.getDocumentElement();
				
				
				Element tempElement = (Element)docEle.getElementsByTagName("temperature").item(0);
				String tempString = tempElement.getFirstChild().getNodeValue();
				Element dateElement = (Element)docEle.getElementsByTagName("datetime").item(0);
				String dateString = dateElement.getFirstChild().getNodeValue();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'+00:00'");
				
				// get the supported ids for GMT-08:00 (Pacific Standard Time)
				String[] ids = TimeZone.getAvailableIDs(1 * 60 * 60 * 1000);
				SimpleTimeZone cet = new SimpleTimeZone(1 * 60 * 60 * 1000, ids[0]);
				
				Date date = new GregorianCalendar(cet).getTime();
				try{
					date = sdf.parse(dateString);
				} catch (ParseException e){
					e.printStackTrace();
				}
				
				
				final TextView temperatura = (TextView) findViewById(R.id.temperatura);
				temperatura.setText(tempString+"¼C");
		        final TextView comentari = (TextView) findViewById(R.id.comentari);
		        comentari.setText(date.toLocaleString());
			}
		} catch(MalformedURLException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(ParserConfigurationException e) {
			e.printStackTrace();
		} catch(SAXException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	private void mostrarInfo() {
		//final TextView comentari = (TextView) findViewById(R.id.comentari);
        //comentari.setText("MŽs informaci—: apuratepp@gmail.com");
        startService(new Intent(this, MeteoUBService.class));
	}
	
	public void mostrarComentari(String comentariString){
		final TextView comentari = (TextView) findViewById(R.id.comentari);
		comentari.setText(comentariString);
	}
	
	static final private int MENU_REFRESH = Menu.FIRST;
	static final private int MENU_INFO = Menu.FIRST+1;
	
	/* Creates the menu items */
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, MENU_REFRESH, 0, "Refrescar");
        menu.add(0, MENU_INFO, 0, "Info");
        return true;
    }
 
    /* Handles item selections */
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
    	case MENU_REFRESH:
    		mostrarComentari("Connectat amb MeteoUB...");
    		refreshTemperatura();
            return true;
    	case MENU_INFO:
    		mostrarInfo();
    		return true;
        }
        return false;
    }
}
