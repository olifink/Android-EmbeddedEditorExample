# Android Embedded Editor Example

This example shows how to report [HERE Map Feedback](http://mapcreator.here.com/mapfeedback/doc/) by including the Embedded Editor into a simple Android application.
It shows two options how you can include the component in your own applications:

- open up an external browser to load the reporting page
- starting a new activity with a WebView embedded

> Always make sure include your correct AppId as part of the URL parameters, otherwise
> we might feel free to ignore your feedback. To obtain the credentials for an application,
> please visit http://developer.here.com/plans to register with HERE.

## Open in external browser

For obvious reasons, this is the easiest way to enable map feedback in any application. All you need to do is to
open up the system browser with the proper URL. Have a look at the  [feedback documentation](http://mapcreator.here.com/mapfeedback/doc/),
where there is also a handy [URL Generator](http://mapcreator.here.com/mapfeedback/doc/urlgen.html)
that helps you to pick the correct one.

For an Android application, the code in `startBrowser()` method in [MainActivity](../master/app/src/main/java/com/here/mapfeedback/examples/embeddededitorexample/MainActivity.java)
just looks like this:

``` java
String feedbackUrl = "https://mapfeedback.here.com/";

// use a view intent on the URL to open the default browser
Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedbackUrl));
startActivity(browserIntent);
```

## Start a new in-app activity

There are good reasons for not opening up a new browser for the user but to keep feedback
functionality in the application itself. This can be done using a `WebView` control inside your
activity. This is slightly more work than just opening up the browser, but it allows much
more control over the experience. This is particularly important for back-navation handling, so
that pressing the back button while in the feedback flow allows the user to return to the
previous page, and not - as would be the default - abandon the feedback submission completely.

We have provided a suggested way to do this on Android with the [FeedbackActivity](../master/app/src/main/java/com/here/mapfeedback/examples/embeddededitorexample/FeedbacActivity.java)
class. You should just be able to take this file and drop it into your own project. Keep in mind,
however, to enable the right permissions for your project. You need the following entries
in your [AndroidManifest.xml](../master/app/src/main/AndroidManifest.xml) file:

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

### Configuring the WebView

The WebView must be configured correctly to show the Embedded Editor page. This means first that it
actually should handle all URLs itself (it will open them in the external browser otherwise), and
JavaScript must be enabled. Also, since we're not getting the geo-position from beforehand from
Android-native code, we have enabled Geolocation capabilities so the WebView can get the current
position.

Here are the steps necessary to set up the WebView:

#### Enable JavaScript and Geolocation

The WebView needs to have these options turned on in it's settings. Also, as the geolocation
periodically caches the locations, it's a good idea to give a path where these can be persisted
across multiple invocations of the application.

```java
// make sure we're properly set up to do JavaScript and Geolocation
WebSettings webSettings = myWebView.getSettings();
webSettings.setJavaScriptEnabled(true);
webSettings.setGeolocationEnabled(true);
webSettings.setGeolocationDatabasePath( this.getFilesDir().getPath());
```

#### Handle all URLs inside the WebView itself

A bit counter-intuitive at first: the WebView must be instructed to actually show the URL it is
given. Unless this is done, it would revert to the external default browser. For this, a configured
`WebViewClient` instance needs to be installed and return `false` when the view inquires on the
URL given.

```java
// we want the WebView to take care of all URLs and not open a browser
myWebView.setWebViewClient(new WebViewClient() {
   public boolean shouldOverrideUrlLoading(WebView view, String url) {
       return (false);
   }
});
```

#### Back-navigation button handling

It is sensible to have the back-button behaviour work normally for the user even while inside the
feedback flow that is coming from the embedded editor widget. This means that instead of backing
out of the activity completely, it should go back on the web navigation if it can.

```java
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
```

To make it really feel naturally, this logic is not applied if the user just submitted the feedback
successfully. This this point in the flow, ie. when the URL contains `"#/submit"`, the back button
should actually close the complete Activity and not go back through the browse history.

And that's it really. We will be wrapping up that functionality in custom library, but until then,
just feel free to take the code and include it in your own application.