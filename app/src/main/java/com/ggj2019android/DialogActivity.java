package com.ggj2019android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ggj2019android.model.DialogueOption;
import com.ggj2019android.model.Game;
import com.ggj2019android.model.Location;
import com.ggj2019android.model.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class DialogActivity extends AppCompatActivity {

    // Controls
    private ProgressBar _progressYear;
    private TextView _ageValue;
    private TextView _lblLocationName;
    private ImageView _imgLocationImage;
    private TextView _lblPersonName;
    private ImageView _imgPersonImage;
    private EditText _txtRequest;
    private TextView _lblOutput;
    private DrawerLayout _drawerLayout;
    private NavigationView _frameVocab;
    private RecyclerView _lstWords;

    // State
    private Random _rand;
    private SharedPreferences _savedValues;
    private Game _game;
    private ClockTask _task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        _progressYear = findViewById(R.id.progressYear);
        _ageValue = findViewById(R.id.ageValue);
        _lblLocationName = findViewById(R.id.lblLocationName);
        _imgLocationImage = findViewById(R.id.imgLocation);
        _lblPersonName = findViewById(R.id.lblPersonName);
        _imgPersonImage = findViewById(R.id.imgPerson);
        _txtRequest = findViewById(R.id.txtRequest);
        _lblOutput = findViewById(R.id.lblOutput);

        _drawerLayout = findViewById(R.id.drawerLayout);
        _frameVocab = findViewById(R.id.frameVocab);
        _lstWords = _frameVocab.getHeaderView(0).findViewById(R.id.lstWords);
        _lstWords.setLayoutManager(new GridLayoutManager(this, 3));

        _txtRequest.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_UNSPECIFIED ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_GO)
                {
                    say(_txtRequest);
                }
                return false;
            }
        });

        // Initialize a random number generator
        _rand = new Random();

        // Load saved values
        _savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        _game = Game.INSTANCE;

        _ageValue.setText(Integer.toString(_game.getAge()));

        Location location = _game.getCurrentLocation();
        Person person = _game.getCurrentPerson();
        _lblLocationName.setText(location.getName());
        _imgLocationImage.setImageResource(location.getImage());
        _lblPersonName.setText(person.getName());
        _imgPersonImage.setImageResource(person.getImage());
        _lblOutput.setVisibility(View.GONE);

        refreshWords();

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

    private void refreshWords()
    {
        _lstWords.setAdapter(new WordsAdapter(_game.getWords()));
    }

    public void showWords(View view)
    {
        _drawerLayout.openDrawer(_frameVocab);
    }

    public void say(View view)
    {
        String input = _txtRequest.getText().toString().trim();
        String[] inputWords = input.toLowerCase().split("[\\s\\.!?,]+");
        if (inputWords.length <= 0 || inputWords[0].equals(""))
        {
            return;
        }

        List<DialogueOption> available = getAvailableOptions();
        shuffleOptions(available);

        for (DialogueOption option : available)
        {
            if (doesInputMatch(inputWords, option))
            {
                option.runDialogueEffect(_game);
                refreshWords();
                showResponse(input, option.getResponseText());
                return;
            }
        }

        showResponse(input, "Huh?");
    }

    private void showResponse(String input, String output)
    {
        _txtRequest.setText("");
        _lblOutput.setText("Me: " + input + "\n\n" + _game.getCurrentPersonName() + ": " + output);
        _lblOutput.setVisibility(View.VISIBLE);
        hideKeyboard();
    }

    private void hideKeyboard()
    {
        InputMethodManager mgr = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(_txtRequest.getWindowToken(), 0);
    }

    private List<DialogueOption> getAvailableOptions()
    {
        List<DialogueOption> all = _game.getDialogOptions();
        List<DialogueOption> available = new ArrayList<>();

        for (DialogueOption option : all)
        {
            if (option.isAvailable(_game))
            {
                available.add(option);
            }
        }
        return available;
    }

    private <T> void shuffleOptions(List<T> items)
    {
        for (int i = 0, n = items.size(); i < n; ++i)
        {
            int j = i + _rand.nextInt(n - i);
            T temp = items.get(i);
            items.set(i, items.get(j));
            items.set(j, temp);
        }
    }

    private boolean doesInputMatch(String[] inputWords, DialogueOption option)
    {
        for (int i = 0; i < inputWords.length; i++)
            if (!_game.hasWord(inputWords[i]))
                inputWords[i] = "";
        String[] testWords = option.getInputText().trim().toLowerCase().split("\\s+");

        HashSet<String> inputSet = new HashSet<>();
        HashSet<String> testSet = new HashSet<>();
        Collections.addAll(inputSet, inputWords);
        Collections.addAll(testSet, testWords);

        return inputSet.containsAll(testSet);
    }

    private void enterWord(String word)
    {
        StringBuilder text = new StringBuilder(_txtRequest.getText());
        text.append(' ');
        text.append(word);
        _txtRequest.setText(text);
        _txtRequest.setSelection(text.length());
        _drawerLayout.closeDrawers();
    }

    private class WordsAdapter extends RecyclerView.Adapter<WordViewHolder>
    {
        private List<String> _words;

        public WordsAdapter(List<String> words)
        {
            _words = words;
        }

        @Override @NonNull
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i)
        {
            TextView lblWord = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, parent, false);
            return new WordViewHolder(lblWord);
        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder vh, int i)
        {
            final String word = _words.get(i);
            vh.lblWord.setText(word);
            vh.lblWord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    enterWord(word);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return _words.size();
        }
    }

    private static class WordViewHolder extends RecyclerView.ViewHolder
    {
        public TextView lblWord;

        public WordViewHolder(TextView lblWord)
        {
            super(lblWord);
            this.lblWord = lblWord;
        }
    }
}
