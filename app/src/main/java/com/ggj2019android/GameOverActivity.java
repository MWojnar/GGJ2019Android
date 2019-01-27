package com.ggj2019android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ggj2019android.model.Game;

public class GameOverActivity extends AppCompatActivity {
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _game = Game.INSTANCE;
    }

    public void restart(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
