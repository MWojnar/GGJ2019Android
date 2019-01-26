package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;

import org.w3c.dom.Text;

import java.util.Collection;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    // Controls
    private RecyclerView _lstLocations;
    private RecyclerView.LayoutManager _locationsLayout;
    private RecyclerView.Adapter _locationsAdapter;

    // State
    private SharedPreferences _savedValues;
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Setup the list of locations
        _lstLocations = findViewById(R.id.lstMapLocations);
        _locationsLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        _lstLocations.setHasFixedSize(false);
        _lstLocations.setLayoutManager(_locationsLayout);

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);

        // Show locations
        // _lstMapLocations.setAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;

        _locationsAdapter = new LocationsAdapter(_game.getLocations());
        _lstLocations.setAdapter(_locationsAdapter);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
    }

    public void visitLocation(String locationId)
    {
        _game.setCurrentLocationId(locationId);

        Intent intent = new Intent(this, SelectPersonActivity.class);
        startActivity(intent);
    }

    private class LocationsAdapter extends RecyclerView.Adapter<LocationViewHolder>
    {
        private List<Location> _locations;

        public LocationsAdapter(List<Location> locations)
        {
            _locations = locations;
        }

        @Override @NonNull
        public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
        {
            View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
            Button btnLocationAction = layout.findViewById(R.id.btnLocationAction);
            TextView lblLocationDesc = layout.findViewById(R.id.lblLocationDesc);
            return new LocationViewHolder(layout, btnLocationAction, lblLocationDesc);
        }

        @Override
        public void onBindViewHolder(@NonNull LocationViewHolder vh, int i)
        {
            final Location location = _locations.get(i);
            vh.btnLocationAction.setText(location.getName());
            vh.lblLocationDesc.setText(location.getDescription());

            vh.btnLocationAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitLocation(location.getId());
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return _locations.size();
        }
    }

    private static class LocationViewHolder extends RecyclerView.ViewHolder
    {
        public View layout;
        public Button btnLocationAction;
        public TextView lblLocationDesc;
        public int position;

        public LocationViewHolder(View layout, Button btnLocationAction, TextView lblLocationDesc)
        {
            super(layout);
            this.layout = layout;
            this.btnLocationAction = btnLocationAction;
            this.lblLocationDesc = lblLocationDesc;
        }
    }
}
