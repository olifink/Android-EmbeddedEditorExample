package com.here.mapfeedback.examples.embeddededitorexample;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class FeedbackActivity extends Activity {

    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // no layout.xml needed, just add a WebView
        myWebView = new WebView(this);
        setContentView(myWebView);

        // make sure we're properly set up to do JavaScript and Geolocation
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setGeolocationDatabasePath( this.getFilesDir().getPath());

        // we want the WebView to take care of all URLs and not open a browser
        myWebView.setWebViewClient(new WebViewClient() {
           public boolean shouldOverrideUrlLoading(WebView view, String url) {
               return (false);
           }
       });

        // make sure we're allowed to use the location, and no need to persist it at the moment
        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        // and now load the the Embedded Editor Feedback widget
        // Note: you should always include your real AppId, otherwise your feedback will be ignored
        myWebView.loadUrl("https://mapfeedback.here.com/");
    }

    // we want the back button to work properly inside the widget
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {

            // unless we have submitted properly, 'back' takes us back a page in the WebView
            if(!myWebView.getUrl().contains("#/submit")) {
                myWebView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
