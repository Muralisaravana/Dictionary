package com.ms.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private TextInputLayout til_term;
    private TextInputEditText tie_term;
    private AppCompatButton acb_find;
    public AppCompatTextView act_result;
    private String url;

    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intialize();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

    }

    private void intialize()
    {
        context = this;
        til_term = (TextInputLayout) findViewById(R.id.til_term);
        tie_term = (TextInputEditText) findViewById(R.id.tie_term);
        acb_find = (AppCompatButton) findViewById(R.id.acb_search);
        act_result = (AppCompatTextView)findViewById(R.id.act_meaning);
        til_term.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tie_term.getText().clear();
                act_result.setText("");
            }
        });
        acb_find.setOnClickListener(this);
        if (tie_term.getText().equals(""))
        {
            act_result.setText(null);
        }


    }

    private String dictionaryEntries() {
        final String language = "en-gb";
        final String word = tie_term.getText().toString();
        final String fields = "definitions";
        final String strictMatch = "false";
        final String word_id = word.toLowerCase();
        return "https://od-api.oxforddictionaries.com:443/api/v2/entries/" + language + "/" + word_id + "?" + "fields=" + fields + "&strictMatch=" + strictMatch;
    }

    @Override
    public void onClick(View v)
    {
         if (v.getId()==R.id.acb_search)
         {
           requestSearch();
         }
    }

    private void requestSearch()
    {

        MyDictionaryRequest myDictionaryRequest = new MyDictionaryRequest(this,act_result);
        url = dictionaryEntries();
        myDictionaryRequest.execute(url);
    }


    //Manifest : "ca-app-pub-9736033866589936~2918032178"
    //Layout  : "ca-app-pub-9736033866589936/4039542159"

}