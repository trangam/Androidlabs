package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {
    ProgressBar bar;
    TextView currentTemp;
    TextView minTemp;
    TextView maxTemp;
    TextView uvRate;
    ImageView currentWeather;
    HttpURLConnection urlConnection;

    private static final String UVAPI="http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";
    private static final String IMAGE="http://openweathermap.org/img/w/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        bar=findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);
        ForecastQuery req = new ForecastQuery();
        req.execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric");  //Type 1
    }
    class ForecastQuery extends AsyncTask<String,Integer,String> {
        private String uvRating;
        private String min;
        private String max;
        private String current;
        private Bitmap image;
        private String icon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... args) {
            try {

                //create a URL object of what server to contact:
                URL url = new URL(args[0]);
                //open the connection
                 urlConnection = (HttpURLConnection) url.openConnection();
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput( response  , "UTF-8");

                String parameter = null;

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while(eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_TAG)
                    {
                        //If you get here, then you are pointing at a start tag
                        if(xpp.getName().equals("temperature"))
                        {
                            //If you get here, then you are pointing to a <Weather> start tag
                           current = xpp.getAttributeValue(null,    "value");
                            Thread.sleep(2000);
                            publishProgress(25);
                            min= xpp.getAttributeValue(null, "min");
                            Thread.sleep(2000);
                            publishProgress(50);
                            max = xpp.getAttributeValue(null, "max");
                            Thread.sleep(2000);
                            publishProgress(75);

                        }
                       else if(xpp.getName().equals("weather")){
                            icon=xpp.getAttributeValue(null, "icon");
                            String fileName=icon+".png";
                            Log.i("fileName",icon+".png");
                            if (fileExistance(fileName)){
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(fileName);   }
                                catch (FileNotFoundException e) {
                                    e.printStackTrace();  }
                                image = BitmapFactory.decodeStream(fis);

                            }
                            else{
                              image= HTTPImage.getImage( WeatherForecast.IMAGE+ icon + ".png");

                                    try {
                                        FileOutputStream outputStream = openFileOutput(icon + ".png", Context.MODE_PRIVATE);
                                        image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                        outputStream.flush();
                                        outputStream.close();
                                    }catch(Exception e){}

                            }
                        }
                        publishProgress(100);
                    }
                    eventType = xpp.next(); //move to the next xml event and store it in a variable
                }

            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
            }
            try {
                URL url = new URL(WeatherForecast.UVAPI);

                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                String result = sb.toString();

                JSONObject uvReport = new JSONObject(result);

                 uvRating = uvReport.getString("value");
                Log.i("WeatherForecast", "The uv is now: " + uvRating) ;
            }
            catch (Exception e)
            {
            }


            return "Done";
          }
        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            return file.exists();   }

        public void onProgressUpdate(Integer ... value)
        {
            bar=findViewById(R.id.progressBar);
            bar.setVisibility(View.VISIBLE);
            bar.setProgress(value[0]);
        }
        //Type3
        public void onPostExecute(String fromDoInBackground)
        {
            currentTemp=findViewById(R.id.currentTemp);
            minTemp=findViewById(R.id.minTemp);
            maxTemp=findViewById(R.id.maxTemp);
            uvRate=findViewById(R.id.uvRate);
            currentWeather=findViewById(R.id.currentWeather);
            currentWeather.setImageBitmap(image);
            currentTemp.setText("Current temperature: "+ current +"°C");
            minTemp.setText("Min temperature: "+min+"°C");
            maxTemp.setText("Max temperature: "+max+"°C");
            uvRate.setText("UV: "+uvRating);
            bar.setVisibility(View.INVISIBLE);
            Log.i("HTTP", fromDoInBackground);
        }
    }
    private  static class HTTPImage{
        public static Bitmap getImage(URL url){
            HttpURLConnection urlConnection=null;

            try{
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(urlConnection.getInputStream());
                }
                else return null;
        }
            catch (Exception e){
                return null;
            }
    }
    public static Bitmap getImage(String path)
    {
        try {
            URL url = new URL(path);
            return getImage(url);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }    }

}
