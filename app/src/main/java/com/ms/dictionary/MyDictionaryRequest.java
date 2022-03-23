package com.ms.dictionary;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;


import androidx.appcompat.widget.AppCompatTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyDictionaryRequest extends AsyncTask<String,Integer,String>
{
    final String app_id = "87ce6a57";
    final String app_key = "ad89d974a38547a510623ff8db6744af";
    String myURL;
    Context context;
    AppCompatTextView act_Result;
    private ProgressDialog dialog;

    public MyDictionaryRequest(Context context, AppCompatTextView act_result) {
        this.context = context;
        this.act_Result = act_result;
        dialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Please wait...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... params)
    {

        myURL = params[0];
        try
        {
            URL url = new URL(myURL);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        String defaultString;
        try
        {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray results = jsonObject.getJSONArray("results");

            JSONObject lEntries = results.getJSONObject(0);
            JSONArray leArray = lEntries.getJSONArray("lexicalEntries");

            JSONObject entries = leArray.getJSONObject(0);
            JSONArray eArray = entries.getJSONArray("entries");

            JSONObject sObject = eArray.getJSONObject(0);
            JSONArray  sArray = sObject.getJSONArray("senses");

            JSONObject dObject = sArray.getJSONObject(0);
            JSONArray  dArray = dObject.getJSONArray("definitions");

            defaultString = dArray.getString(0);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            act_Result.setText(defaultString);


        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
