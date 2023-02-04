package com.example.soundboard_jnt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.flexbox.FlexboxLayout;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ConstraintLayout mainLayout;
    public FlexboxLayout buttonsLayout;
    public TabLayout tabLayout;
    public Field[] audioFiles;
    public String[] names;
    HashMap<String, List<String>> hashmap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.constraintLayout2);
        buttonsLayout = findViewById(R.id.flexboxLayout2);
        tabLayout = (TabLayout) findViewById(R.id.test);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d("Selected Tab", "onTabSelected: " + tab.getText());
                renderButton(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {



            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        /// Get all files from raw folder
        initData();
    }

    /// Get all files needed
    public void getAudioFiles(){
        audioFiles = R.raw.class.getFields();
    }

    public void initData(){
        getAudioFiles();
        names = new String[audioFiles.length];

        for (int i = 0; i < audioFiles.length; i++) {
            names[i] = audioFiles[i].getName();
        }

        for (String str : names){
            String[] strArray = str.split("_");
            hashmap.computeIfAbsent(strArray[0], v-> new ArrayList<>()).add(strArray[1]);
        }

        renderTabs();
    }

    public void renderTabs(){
        for (String key: hashmap.keySet()) {
            tabLayout.addTab(tabLayout.newTab().setText(key));
        }
    }

    public void ClearButton() {
        buttonsLayout.removeAllViews();
    }

    public void renderButton(String arg){
        ClearButton();
        Log.d("Arg", "renderButton: " + arg);
        for (String c: hashmap.get(arg)
             ) {
            Log.d("hashmap", "renderButton: " + c);
            Button x = new Button(this);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ViewGroup.MarginLayoutParams margins = new ViewGroup.MarginLayoutParams(params);
            margins.leftMargin = 10;
            margins.rightMargin = 10;
            x.setPadding(100,25,100,25);
            x.setLayoutParams(params);

            x.setText(c);
            buttonsLayout.addView(x);
        }
    }

}