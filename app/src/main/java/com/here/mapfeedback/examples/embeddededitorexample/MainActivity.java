package com.here.mapfeedback.examples.embeddededitorexample;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        setContentView(R.layout.activity_main);
    }

    public void startBrowser(View view) {
        String feedbackUrl = "https://mapfeedback.here.com/";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedbackUrl));
        startActivity(browserIntent);
    }

    public void startActivity(View view) {
        Intent feedback = new Intent(this, FeedbackActivity.class);
        startActivity(feedback);
    }

}