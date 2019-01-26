package com.ggj2019android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SelectActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_action);
    }

    public void leaveRoom(View v)
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
