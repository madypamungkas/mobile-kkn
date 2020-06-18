package id.technow.rejowinangunsiagacovid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.ybq.android.spinkit.SpinKitView;

public class Splash extends AppCompatActivity {
    SpinKitView spinKitView;
    private Object DoubleBounce;
    private Handler mHandler;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
        }
*/
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
*/
        setContentView(R.layout.activity_splash);

        mHandler = new Handler();
        mStatusChecker.run();
        checkLogin();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {

            mHandler.postDelayed(mStatusChecker, 3000);
        }
    };

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void checkConnection() {
        if (isNetworkAvailable()) {
            checkLogin();
        } else {
            checkLogin();
        }
    }

    private void checkLogin() {
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent intent = new Intent(Splash.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        }, 3000L);
    }

    public void intentLogin(View view) {
        Intent intent = new Intent(Splash.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
