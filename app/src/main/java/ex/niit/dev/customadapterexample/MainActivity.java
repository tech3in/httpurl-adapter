package ex.niit.dev.customadapterexample;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {



    private ListView listView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        progressDialog = ProgressDialog.show(this,"Loading","Contacting Server");
        WebService webService = new WebService();
        webService.execute("https://jsonplaceholder.typicode.com/posts");



    }


    private class WebService extends AsyncTask<String,Void,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(urls[0]);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.setReadTimeout(10000);
                httpsURLConnection.setConnectTimeout(15000);
                httpsURLConnection.setDoInput(true);

                httpsURLConnection.connect();

                InputStream inputStream = httpsURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                String line = "";

                while ((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line).append("\n");
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.w("Result",s);
            //titles = new ArrayList<String>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                JsonAdapter adapter = new JsonAdapter(MainActivity.this,jsonArray);
                listView.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressDialog.dismiss();

        }
    }
}
