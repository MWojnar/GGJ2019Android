package com.ggj2019android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ggj2019android.model.Game;

public class BirthdayActivity extends AppCompatActivity
{
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _game = Game.INSTANCE;
    }

    @Override
    protected void onPause() {
        super.onPause();
        _game.endPaused();
    }

    public void leaveRoom(View v)
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }
}
