package com.ggj2019android.model;

import android.content.Context;

import java.util.List;
import java.util.Map;

public class Game {
    private Context _applicationContext;
    private long _startTime, _timeElapsed;
    private int _age;
    private LifeStage _lifeStage;
    private List<String> _words;
    private Map<String, Integer> _skills;
    private List<Location> _locations;
    private List<Person> _people;

    public Game(Context applicationContext, long startTime) {}

    public long getStartTime() {
        return _startTime;
    }

    public void setTimeElapsed(long time) {
        _timeElapsed = time;
    }

    public int getAge() {
        return _age;
    }

    public LifeStage get_lifeStage() {
        return _lifeStage;
    }

    public List<String> get_words() {
        return _words;
    }

    public boolean hasWord(String word) {
        return _words.contains(word);
    }

    public void addWord(String word) {
        _words.add(word);
    }

    public int getSkill(String skill) {
        return _skills.containsKey(skill) ? _skills.get(skill) : 0;
    }

    public void increaseSkill(String skill, int value) {
        if (_skills.containsKey(skill))
            _skills.put(skill, _skills.get(skill) + value);
    }

    public List<Location> getLocations() {
        return _locations;
    }

    public List<Person> getPeople() {
        return _people;
    }
}