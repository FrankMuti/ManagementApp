package com.example.blacksky;

import android.content.Intent;
import android.os.Build;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Login extends AppCompatActivity {

    PatternLockView mPatternLockView;

    TextView textView;

    Authenticate auth = new Authenticate();

    private PatternLockViewListener mPatternLockListener = new PatternLockViewListener() {
        @Override
        public void onStarted() {
            Log.d(getClass().getName(), "Pattern Drawing Started");
        }

        @Override
        public void onProgress(List<PatternLockView.Dot> progressPattern) {
            Log.d(getClass().getName(), "Pattern Progress: " +
                    PatternLockUtils.patternToString(mPatternLockView, progressPattern));
        }

        @Override
        public void onComplete(List<PatternLockView.Dot> pattern) {
            Log.d(getClass().getName(), "Pattern complete: " +
                    PatternLockUtils.patternToString(mPatternLockView, pattern));
            if (auth.authenticate(pattern)) {
                mPatternLockView.clearPattern();
                textView.setText("Correct");
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
            else {
                //mPatternLockView.getWrongStateColor();
                textView.setText("Wrong Pattern");
                mPatternLockView.clearPattern();
            }
        }

        @Override
        public void onCleared() {
            Log.d(getClass().getName(), "Pattern has been cleared");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
        textView = findViewById(R.id.errorText);

        mPatternLockView.addPatternLockListener(mPatternLockListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.darkText));
        }
    }


    class Authenticate {

        PatternLockView.Dot dot;

        List<PatternLockView.Dot> myList = new ArrayList<>();

        private void setList(){

        }

        int[][] pp = {{0, 0}, {1, 0}, {2, 0}, {2, 1}};
        int[][] ip = new int[4][2];


        public boolean authenticate(List<PatternLockView.Dot> inPattern){

            if (inPattern.size() != 4) return false;

            int i = 0;
            while (i < inPattern.size()){
                int r = inPattern.get(i).getRow();
                int c = inPattern.get(i).getColumn();
                ip[i][0] = r;
                ip[i][1] = c;
                i++;
            }


            for (i = 0; i < pp.length; i++) {
                for (int j = 0; j < pp[i].length; j++){
                    if (ip[i][j] != pp[i][j]) return false;
                }
            }

            return true;
        }

    }
}
