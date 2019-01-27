package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences _savedValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    public void playGame(View view)
    {
        Game game = new Game(getApplicationContext());
        game.setCurrentLocationId("bedroom");
        game.setCurrentPerson("Mom");
        game.randomizePeople();
        Game.INSTANCE = game;

        Intent intent = new Intent(this, DialogActivity.class);
        //intent.putExtra("Game", game);
        startActivity(intent);
    }
}
