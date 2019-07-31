package com.pear.facedetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void Solution(View view) {
        if (view.getId() == R.id.btn1) {
            Intent intent = new Intent(MainActivity.this, Solution1Activity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.btn2) {
            Intent intent = new Intent(MainActivity.this, Solution2Activity.class);
            startActivity(intent);
        }

    }
}
