//Homework 12
//Sujan Gyawali
//L20445862

package com.example.drawsurfaceviewtest;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MainView view = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = new MainView(this);
        setContentView(view);
    }

    @Override
    protected void onPause () {
        super.onPause();
        if (view != null) {
            view.pause();
        }
    }

    @Override
    protected void onResume () {
        super.onResume();
        if (view != null) {
            view.resume();
        }
    }
}

