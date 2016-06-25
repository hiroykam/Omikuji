package com.example.hiroyukikamisaka.omikuji;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private Vibrator vibrator;
    private SensorManager manager;

    private OmikujiParts[] omikujiShelf = new OmikujiParts[20];

    private OmikujiBox omikujiBox = new OmikujiBox();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.omikuji);

        this.vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        this.manager = (SensorManager)getSystemService(SENSOR_SERVICE);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean value = pref.getBoolean("button", false);

        Button btn = (Button)findViewById(R.id.button);
        btn.setVisibility(value ? View.VISIBLE : View.INVISIBLE);

        this.omikujiBox.setImage((ImageView)findViewById(R.id.imageView));

        for (int i = 0; i < 20; i++) {
            this.omikujiShelf[i] = new OmikujiParts(R.mipmap.result2, R.string.content1);
        }
        this.omikujiShelf[0].drawId = R.mipmap.result1;
        this.omikujiShelf[0].fortuneId = R.string.content2;

        this.omikujiShelf[1].drawId = R.mipmap.result3;
        this.omikujiShelf[1].fortuneId = R.string.content9;

        this.omikujiShelf[2].fortuneId = R.string.content3;
        this.omikujiShelf[3].fortuneId = R.string.content4;
        this.omikujiShelf[4].fortuneId = R.string.content5;
        this.omikujiShelf[5].fortuneId = R.string.content6;
        this.omikujiShelf[6].fortuneId = R.string.content7;
        this.omikujiShelf[5].fortuneId = R.string.content8;

        /*
        TextView tv = (TextView) findViewById(R.id.hello_view);
        Random rnd = new Random();
        int number = rnd.nextInt(20);
        String[] shelf = new String[20];
        for (int i = 0; i < 20; i++) {
            shelf[i] = "吉";
        }
        shelf[0] = "大吉";
        shelf[19] = "凶";
        tv.setText(shelf[number]);
        */
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (0 < this.omikujiBox.getNumber()) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
                if (pref.getBoolean("vibration", true)) {
                    this.vibrator.vibrate(50);
                }
                this.drawResult();
            }
        }
        return super.onTouchEvent(event);
    }

    public void onButtonClick(View v) {
        this.omikujiBox.shake();
    }

    public void drawResult() {
        OmikujiParts op = omikujiShelf[omikujiBox.getNumber()];

        setContentView(R.layout.fortune);

        ImageView imageView = (ImageView)findViewById(R.id.result1);
        imageView.setImageResource(op.drawId);
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setTextColor(Color.BLACK);
        textView.setText(op.fortuneId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        Toast toast = Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG);
        toast.show();
        */

        if (item.getItemId() == R.id.item1) {
            Intent intent = new Intent(this, OmikujiPreferenceActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.manager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = this.manager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (0 < sensors.size()) {
            this.manager.registerListener(this, sensors.get(0), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (this.omikujiBox.chkShake(event)) {
            if (this.omikujiBox.getNumber() < 0) {
                this.omikujiBox.shake();
            }
        }
        /*
        float value = event.values[0];
        if (7 < value) {
            Toast toast = Toast.makeText(this, "加速度：" + value, Toast.LENGTH_LONG);
            toast.show();
        }
        */
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
