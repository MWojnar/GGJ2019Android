package com.ggj2019android.model;

import android.content.Context;

import com.ggj2019android.People.Mom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public static Game INSTANCE = null;

    private Context _applicationContext;
    private long _startTime;
    private long _timeElapsed;
    private int _age;
    private LifeStage _lifeStage;
    private String _currenLocationId;
    private String _currentPersonName;

    private List<String> _words;
    private Map<String, Integer> _skills;
    private Map<String, Location> _locations;
    private Map<String, Person> _people;

    public Game(Context applicationContext, long startTime) {
        _applicationContext = applicationContext;
        _startTime = startTime;
        _timeElapsed = 0;
        _age = 0;
        _lifeStage = LifeStage.INFANT;
        _currenLocationId = "infant_bedroom";

        _words = new ArrayList<>();
        _skills = new LinkedHashMap<>();
        _locations = new LinkedHashMap<>();
        _people = new LinkedHashMap<>();

        _words.add("Dad");
        _words.add("Mom");

        //addLocation("infant_bedroom", "Bedroom", "Your favorite place", 0);
        addLocation("child_bedroom", "Bedroom", "Your favorite place", 0);
        addLocation("child_playground", "Playground", "Has the most awesome swingset", 0);
        addLocation("child_library", "Library", "Has the best books. This is your quiet place.", 0);

        addPerson(new Mom());

    }

    public long getStartTime() {
        return _startTime;
    }

    public void setTimeElapsed(long time) {
        _timeElapsed = time;
    }

    public int getAge() {
        return _age;
    }

    public LifeStage getLifeStage() {
        return _lifeStage;
    }

    public List<String> getWords() {
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
        else
            _skills.put(skill, value);
    }

    public String getCurrentLocationId()
    {
        return _currenLocationId;
    }

    public Location getCurrentLocation()
    {
        return _locations.get(_currenLocationId);
    }

    public void setCurrentLocationId(String locationId)
    {
        _currenLocationId = locationId;
    }

    public List<Location> getLocations()
    {
        List<Location> returnList = new ArrayList<Location>();
        for (Location location : _locations.values())
            returnList.add(location);
        return returnList;
    }

    private void addLocation(String id, String name, String description, int image)
    {
        _locations.put(id, new Location(id, name, description, image));
    }

    public List<Person> getPeople() {
        List<Person> returnList = new ArrayList<Person>();
        for (Person person : _people.values())
            returnList.add(person);
        return returnList;
    }

    public Person getPerson(String name) {
        return _people.get(name);
    }

    public Location getLocation(String id) {
        return _locations.get(id);
    }

    public String getCurrentPersonName() {
        return _currentPersonName;
    }

    public void setCurrentPerson(String personName) {
        _currentPersonName = personName;
    }

    public void addPerson(Person person) {
        _people.put(person.getName(), person);
    }
}