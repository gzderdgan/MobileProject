package com.example.gzde.mobileproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TextView txt_Sehir, txt_Sicaklik, txt_Weather, txt_Aciklama;
    private Spinner spin;
    private ImageView image;
    String sehir;
    private TextView tavsiye;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_Sehir = (TextView) findViewById(R.id.txt_sehir);
        txt_Aciklama = (TextView) findViewById(R.id.txt_aciklama);
        txt_Sicaklik = (TextView) findViewById(R.id.txt_sicaklik);
        txt_Weather = (TextView) findViewById(R.id.txt_weather);
        image = (ImageView) findViewById(R.id.imageView);
        spin = (Spinner) findViewById(R.id.spinner);

        tavsiye=(TextView)findViewById(R.id.twTavsiye);
        ArrayList<String> spinList = new ArrayList<>(Arrays.asList("ADANA", "ADIYAMAN", "AFYON", "AĞRI", "AMASYA", "ANKARA", "ANTALYA", "ARTVİN", "AYDIN", "BALIKESİR", "BİLECİK", "BİNGÖL", "BİTLİS", "BOLU", "BURDUR", "BURSA", "ÇANAKKALE", "ÇANKIRI", "ÇORUM", "DENİZLİ", "DİYARBAKIR", "EDİRNE", "ELAZIĞ", "ERZİNCAN", "ERZURUM", "ESKİŞEHİR", "GAZİANTEP", "GİRESUN", "GÜMÜŞHANE", "HAKKARİ", "HATAY", "ISPARTA", "İÇEL", "İSTANBUL", "İZMİR", "KARS", "KASTAMONU", "KAYSERİ", "KIRKLARELİ", "KIRŞEHİR", "KOCAELİ", "KONYA", "KÜTAHYA", "MALATYA", "MANİSA", "KAHRAMANMARAŞ", "MARDİN", "MUĞLA", "MUŞ", "NEVŞEHİR", "NİĞDE", "ORDU", "RİZE", "SAKARYA", "SAMSUN", "SİİRT", "SİNOP", "SİVAS", "TEKİRDAĞ", "TOKAT", "TRABZON", "TUNCELİ", "ŞANLIURFA", "UŞAK", "VAN", "YOZGAT", "ZONGULDAK", "AKSARAY", "BAYBURT", "KARAMAN", "KIRIKKALE", "BATMAN", "ŞIRNAK", "BARTIN", "ARDAHAN", "IĞDIR", "YALOVA", "KARABÜK", "KİLİS", "OSMANİYE", "DÜZCE"));


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinList);
        spin.setAdapter(adapter);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String itemSelected = adapterView.getItemAtPosition(i).toString();
                sehir = String.valueOf(itemSelected);
                new JsonParse ().execute();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    protected class JsonParse extends AsyncTask<Void, Void, Void>{
        String result_main ="";
        String result_description = "";
        String result_icon = "";
        int result_temp=0;
        String result_city;
        Bitmap bitImage;
        @Override
        protected Void doInBackground(Void... params) {
            String result="";
            try {
                URL weather_url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+sehir+"&appid=5519df78a91952f50079565124888a76");
                BufferedReader bufferedReader = null;
                bufferedReader = new BufferedReader(new InputStreamReader(weather_url.openStream()));
                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();

                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("weather");
                JSONObject jsonObject_weather = jsonArray.getJSONObject(0);
                result_main = jsonObject_weather.getString("main");
                result_description = jsonObject_weather.getString("description");
                result_icon = jsonObject_weather.getString("icon");

                JSONObject jsonObject_main = jsonObject.getJSONObject("main");
                Double temp = jsonObject_main.getDouble("temp");

                result_city = jsonObject.getString("name");


                result_temp = (int) (temp-273);

                URL icon_url = new URL("http://openweathermap.org/img/w/"+result_icon+".png");
                bitImage = BitmapFactory.decodeStream(icon_url.openConnection().getInputStream());



            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
      protected void onPostExecute(Void aVoid) {
            txt_Sicaklik.setText(String.valueOf(result_temp + " °"));
            txt_Weather.setText(result_main);
            txt_Sehir.setText(result_city);
            txt_Aciklama.setText(result_description);
            image.setImageBitmap(bitImage);
            super.onPostExecute(aVoid);

            if(txt_Weather.getText().toString().equals("Snow")) {
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.kar);
                tavsiye.setText("Are you ready for snowball war!!");

            }
            else if(txt_Weather.getText().toString().equals("Mist")){
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.mist);
                tavsiye.setText("Do not forget to light up your fog lamps!!");
            }
            else if(txt_Weather.getText().toString().equals("Rain")){
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.rain);
                tavsiye.setText("Do not forget your umbrella!!");
        }
            else if(txt_Weather.getText().toString().equals("Clouds")){
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.cloud);
                tavsiye.setText("Don't be sad! The sun will come soon!!");
            }
            else if(txt_Weather.getText().toString().equals("Haze")){
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.haze);
                tavsiye.setText("If you dont want to fly, shouldn't go outside!!!");
            }
            else{
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.main);
                rLayout.setBackgroundResource(R.drawable.sunny);
                tavsiye.setText("Don't forget your sunglasses!!!");
            }
    }}


    @Override
    public void onBackPressed() {

        Intent setIntent = new Intent(this,Login.class);
        startActivity(setIntent);
        return;


    }


}

