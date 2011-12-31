package com.equip9.meteoub;

import android.app.Activity;
import android.os.Bundle;
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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MeteoUB extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final TextView temperatura = (TextView) findViewById(R.id.temperatura);
        temperatura.setText("suooola!");
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
				final TextView temperatura = (TextView) findViewById(R.id.temperatura);
				
		        temperatura.setText(tempString+"¼C");
				
				
				/*
				NodeList nl = docEle.getElementsByTagName("dades");
				if(nl != null && nl.getLength() > 0) {
					for(int i = 0; i < nl.getLength(); i++){
						Element dada = (Element)nl.item(i);
						Element temp = (Element)dada.getElementsByTagName("temperature");
						String temperaturaString = temp.getFirstChild().getNodeValue();
					}
				}
				*/
				
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
}
