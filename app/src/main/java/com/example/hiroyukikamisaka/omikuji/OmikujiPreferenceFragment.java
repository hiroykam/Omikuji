package com.example.hiroyukikamisaka.omikuji;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Hiroyuki Kamisaka on 2016/06/20.
 */
public class OmikujiPreferenceFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
    }
}
