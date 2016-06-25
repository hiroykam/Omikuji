package com.example.hiroyukikamisaka.omikuji;

import android.hardware.SensorEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Hiroyuki Kamisaka on 2016/06/19.
 */
public class OmikujiBox implements Animation.AnimationListener{
    private long beforeTime;
    private float beforeValue;

    private int number;
    private ImageView image;

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public OmikujiBox() {
        this.number = -1;
    }

    public boolean chkShake(SensorEvent event) {
        long now_time = System.currentTimeMillis();
        long diff_time = now_time - this.beforeTime;
        float now_value = event.values[0] + event.values[1];

        if (1500 < diff_time) {
            float speed = Math.abs(now_value - this.beforeValue) / diff_time * 10000;
            this.beforeTime = now_time;
            this.beforeValue = now_value;

            if (50 < speed) {
                return true;
            }
        }
        return  false;
    }

    public void shake() {
        TranslateAnimation translate = new TranslateAnimation(0, 0, 0, -100);
        translate.setRepeatMode(Animation.REVERSE);
        translate.setRepeatCount(5);
        translate.setDuration(100);

        RotateAnimation rotate = new RotateAnimation(0, -36, this.image.getWidth() / 2, this.image.getHeight() / 2);
        rotate.setDuration(200);

        AnimationSet set = new AnimationSet(true);
        set.addAnimation(rotate);
        set.addAnimation(translate);
        set.setAnimationListener(this);

        this.image.startAnimation(set);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        Random rnd = new Random();
        this.number = rnd.nextInt(20);
        this.image.setImageResource(R.mipmap.omikuji2);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
