package id.technow.rejowinangunsiagacovid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import im.delight.android.webview.AdvancedWebView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    private AdvancedWebView mWebView;
    private ProgressBar webViewProgressBar;
    LinearLayout webLayout, eror, loading;
    Button btnRetry;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.dashboard:
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        break;
                        break;
                    case R.id.admin:
                        Intent intent2 = new Intent(MainActivity.this, Admin.class);
                        startActivity(intent2);
                        break;
                    case R.id.galeri:
                        String url = "https://rejowinangun.kkn-ppmugm.com/galeri";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                        break;
                    default:
                        return true;
                }
                return true;

            }
        });

        checkConnection();
        mWebView = (AdvancedWebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://rejowinangun.kkn-ppmugm.com/");
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    public void loadWeb() {

//        mWebView.loadUrl("http://ipesantren.id");
//        //webView.loadUrl("http://192.168.1.6/ipesantren2/ipesantren/public/");

//        webView.setWebViewClient(new BannerWebViewClient());
//        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
//        webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setBuiltInZoomControls(true);
//
//        webView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                return true;
//            }
//        });
//        webView.setLongClickable(true);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            confirmBackpress();
        }
    }

    public void confirmBackpress() {
        eror.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        webLayout.setVisibility(View.GONE);

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_confirm_exit_app);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        dialog.getWindow().setLayout((9 * width) / 10, (3 * height) / 5);

        MaterialButton btnNo = dialog.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        MaterialButton btnYes = dialog.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkConnection() {
        if (isNetworkAvailable()) {

        } else {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_no_internet);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            dialog.getWindow().setLayout((9 * width) / 10, height);

            MaterialButton btnRetry = dialog.findViewById(R.id.btnRetry);
            btnRetry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    checkConnection();
                }
            });
            dialog.show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}