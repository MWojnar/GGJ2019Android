package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;
import com.ggj2019android.model.Person;

public class DialogActivity extends AppCompatActivity {

    // Controls
    private TextView _lblLocationName;
    private TextView _lblPersonName;
    private TextView _txtRequest;

    // State
    private SharedPreferences _savedValues;
    private Game _game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        _lblLocationName = findViewById(R.id.lblLocationName);
        _lblPersonName = findViewById(R.id.lblPersonName);
        _txtRequest = findViewById(R.id.txtRequest);

        _txtRequest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO)
                {
                    say(textView.getText().toString());
                }
                return false;
            }
        });

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;

        Location location = _game.getCurrentLocation();
        Person person = _game.getCurrentPerson();
        _lblLocationName.setText(location.getName());
        _lblPersonName.setText(person.getName());
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

    public void say(String input)
    {
        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }
}
