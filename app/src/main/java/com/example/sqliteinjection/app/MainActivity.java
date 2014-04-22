package com.example.sqliteinjection.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.ConsoleMessage;
import android.webkit.HttpAuthHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebStorage;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;


public class MainActivity extends Activity {
    private String startUrl = "http://192.168.100.200:8080/websql/";
    private String dbDomain = "http_192.168.100.200_8080";
    private String dbPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbPath = getDatabasePath("db").getAbsolutePath();

        WebView webView = (WebView) findViewById(R.id.webView);

        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(new MyWebViewClient());

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setDatabasePath(dbPath);

        webView.loadUrl(startUrl);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void copyDatabase() {
        Util.extractAsset(MainActivity.this, "Database.db",
                new File(dbPath + "/Database.db"));
        Util.extractAsset(MainActivity.this, "test.db",
                new File(dbPath + "/" + dbDomain + "/0000000000000002.db"));
    }

    private class MyWebChromeClient extends WebChromeClient {
        private final static String TAG = "MyWebView";

        @Override
        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            Log.i("MyWebView", consoleMessage.message() + ":" + consoleMessage.sourceId() + ":"
                    + consoleMessage.lineNumber());

            return true;
        }

        @Override
        public void onExceededDatabaseQuota(String url, String databaseIdentifier, long quota,
                                            long estimateDatabaseSize, long totalQuota,
                                            WebStorage.QuotaUpdater quotaUpdater) {
            Log.d(TAG, String.format
                    ("onExceededDatabaseQuota: url:%s, id:%s, quota:%d, estimate:%d, total:%d",
                            url, databaseIdentifier, quota, estimateDatabaseSize, totalQuota));
            quotaUpdater.updateQuota(estimateDatabaseSize);
        }
    }

    private class MyWebViewClient extends WebViewClient {
        private final static String TAG = "MyWebView";

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d(TAG, "shouldOverrideUrlLoading: " + url);
            // don't override URL so that stuff within iframe can work properly
            // view.loadUrl(url);
            return false;
        }

        @Override
        public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
            handler.cancel();
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            Log.d(TAG, "onLoadResource: " + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Log.d(TAG, "onPageFinshed: " + url);
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            Log.d(TAG, "shouldInterceptRequest: " + url);
            return null;
        }
    }


}
