package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;

public class SelectPersonActivity extends AppCompatActivity
{
    // Controls
    private TextView _lblLocationName;

    // State
    private SharedPreferences _savedValues;
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);

        _lblLocationName = findViewById(R.id.lblLocationName);

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;

        Location location = _game.getCurrentLocation();
        _lblLocationName.setText(location.getName());
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void leaveRoom(View v)
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

}
