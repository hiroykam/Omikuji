package com.example.hiroyukikamisaka.omikuji;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Hiroyuki Kamisaka on 2016/06/21.
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);

        PackageManager pm = getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            TextView tv = (TextView)findViewById(R.id.textView2);
            tv.setText("Version " + info.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
