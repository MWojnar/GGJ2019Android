package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;
import com.ggj2019android.model.Person;

import java.util.List;

public class SelectPersonActivity extends AppCompatActivity
{
    // Controls
    private ProgressBar _progressYear;
    private TextView _lblLocationName;
    private RecyclerView _lstPeople;
    private RecyclerView.LayoutManager _peopleLayout;
    private RecyclerView.Adapter _peopleAdapter;

    // State
    private SharedPreferences _savedValues;
    private Game _game;
    private ClockTask _task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_person);

        _progressYear = findViewById(R.id.progressYear);
        _lblLocationName = findViewById(R.id.lblLocationName);

        // Setup the List of People at the current Location
        _lstPeople = findViewById(R.id.lstPersons);
        _peopleLayout = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        _lstPeople.setHasFixedSize(false);
        _lstPeople.setLayoutManager(_peopleLayout);

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;

        Location location = _game.getCurrentLocation();
        _peopleAdapter = new SelectPersonActivity.PeopleAdapter(location.getPeople());
        _lstPeople.setAdapter(_peopleAdapter);
        _lblLocationName.setText(location.getName());

        _task = new ClockTask(_progressYear);
        _task.execute();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        _task.cancel(true);
    }

    public void leaveRoom(View v)
    {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void visitPerson(String personName)
    {
        _game.setCurrentPerson(personName);

        Intent intent = new Intent(this, DialogActivity.class);
        startActivity(intent);
    }

    private class PeopleAdapter extends RecyclerView.Adapter<SelectPersonActivity.PersonViewHolder>
    {
        private List<Person> _people;

        public PeopleAdapter(List<Person> people)
        {
            _people = people;
        }

        @Override @NonNull
        public SelectPersonActivity.PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
        {
            Button btnPersonAction = (Button)LayoutInflater.from(parent.getContext()).inflate(R.layout.item_action, parent, false);
            return new SelectPersonActivity.PersonViewHolder(btnPersonAction);
        }

        @Override
        public void onBindViewHolder(@NonNull SelectPersonActivity.PersonViewHolder vh, int i)
        {
            final Person person = _people.get(i);
            vh.btnPersonAction.setText(person.getName());
            vh.btnPersonAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    visitPerson(person.getName());
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return _people.size();
        }
    }

    private static class PersonViewHolder extends RecyclerView.ViewHolder
    {
        public Button btnPersonAction;

        public PersonViewHolder(Button btnPersonAction)
        {
            super(btnPersonAction);
            this.btnPersonAction = btnPersonAction;
        }
    }

}
