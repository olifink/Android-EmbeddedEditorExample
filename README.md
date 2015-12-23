# Android Embedded Editor Example

This example shows how to report [HERE Map Feedback](http://mapcreator.here.com/mapfeedback/doc/) by including the Embedded Editor into a simple Android application.
It shows two options how you can include the component in your own applications:

- open up an external browser to load the reporting page
- starting a new activity with a WebView embedded

> Always make sure include your correct AppId as part of the URL parametersm otherwise
> we might feel free to ignore your feedback. To obtain the credentials for an application,
> please visit http://developer.here.com/plans to register with HERE.

## Open in external browser

For obvious reasons, this is the easiest way to enable map feedback in any application. All you need to do is to
open up the system browser with the proper URL. Have a look at the  [feedback docutmation](http://mapcreator.here.com/mapfeedback/doc/),
where there is also a handy [URL Generator](http://mapcreator.here.com/mapfeedback/doc/urlgen.html)
that helps you to pick the correct one.

For an Android application, the code you can find in the [MainActivity.java](../blob/master/app/src/main/java/com/here/mapfeedback/examples/embeddededitorexample/MainActivity.java)
just looks like this:

``` java
String feedbackUrl = "https://mapfeedback.here.com/";

// use a view intent on the URL to open the default browser
Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(feedbackUrl));
startActivity(browserIntent);
```
