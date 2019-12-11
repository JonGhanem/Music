package com.example.productviewer.api;

import android.os.AsyncTask;
import android.util.Log;

import com.example.productviewer.activities.SplashActivity;
import com.example.productviewer.model.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchHttpConnection extends AsyncTask<Void,Void,Void> {

    Gson gson = new Gson();
    StringBuffer buffer = new StringBuffer();



    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("http://www.nweave.com/wp-content/uploads/2012/09/featured.txt");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();



            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            Log.d("data", "doInBackground: ");

            while ((line = bufferedReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (MalformedURLException e) {

            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        SplashActivity.data.setText(buffer.toString());


        Type productType = new TypeToken<List<Product>>(){}.getType();
        List<Product> products = gson.fromJson(buffer.toString(), productType);
        Log.d("data", "onPostExecute: "+products.get(1).getProduct().getName());

    }
}
