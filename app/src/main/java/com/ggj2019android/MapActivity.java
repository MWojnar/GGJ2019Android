package com.ggj2019android;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;

public class MapActivity extends AppCompatActivity {

    // Controls
    private ListView _lstMapLocations;

    // State
    private SharedPreferences _savedValues;
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Find controls
        _lstMapLocations = findViewById(R.id.lstMapLocations);

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        // Show locations
        // _lstMapLocations.setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //private class LocationsAdapter extends ArrayAdapter<Location>
    //{
    //}
}
