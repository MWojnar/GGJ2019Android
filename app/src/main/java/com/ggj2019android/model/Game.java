package com.ggj2019android.model;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public static Game INSTANCE = null;

    public static final long MILLISECONDS_PER_YEAR = 1000L * 60;

    private Context _applicationContext;
    private long _startTime;
    private long _currTime;
    private long _timeElapsed;
    private int _age;
    private LifeStage _lifeStage;
    private String _currentLocationId;
    private String _currentPersonName;

    private List<String> _words;
    private Map<String, Integer> _skills;
    private Map<String, Location> _locations;
    private Map<String, Person> _people;
    private List<DialogueOption> _dialogueOptions;

    public Game(Context applicationContext) {
        _applicationContext = applicationContext;
        _startTime = SystemClock.uptimeMillis();
        _currTime = _startTime;
        _timeElapsed = 0;
        _age = 0;
        _lifeStage = LifeStage.INFANT;
        _currentLocationId = "bedroom";

        _words = new ArrayList<>();
        _skills = new LinkedHashMap<>();
        _locations = new LinkedHashMap<>();
        _people = new LinkedHashMap<>();
        _dialogueOptions = new ArrayList<>();

        _words.add("dad");
        _words.add("mom");

        //addLocation("infant_bedroom", "Bedroom", "Your favorite place", 0);
        addLocation("bedroom", "Bedroom", "Your favorite place", 0);
        //addLocation("playground", "Playground", "Has the most awesome swingset", 0);
        //addLocation("library", "Library", "Has the best books. This is your quiet place.", 0);

        Person mom = new Person("Mom", "Your Mother", 0);
        Person dad = new Person("Dad", "Your Dad", 0);

        mom.setProbabilityForLocation("bedroom", 1.0f);
        dad.setProbabilityForLocation("bedroom", 1.0f);
        mom.setProbabilityForLocation("library", 0.2f);
        dad.setProbabilityForLocation("playground", 0.2f);

        addPerson(mom);
        addPerson(dad);

        loadDialogue();
        int test = 1;
    }

    private void loadDialogue() {
        File dialogueFile = new File("assets/dialogue.csv");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(_applicationContext.getAssets().open("dialogue.csv")));
            reader.readLine();
            String line = reader.readLine();
            while (line != null) {
                _dialogueOptions.add(dialogueOptionFromLine(line));
                line = reader.readLine();
            }
        } catch (FileNotFoundException e) {
            Log.e("Game","Could not load dialogue data, File not found.");
        } catch (IOException e) {
            Log.e("Game","Could not load dialogue data.");
        }
    }

    private DialogueOption dialogueOptionFromLine(String line) {
        String[] lineParts = line.split(",");

        //Parse line
        String person = lineParts[0];
        String location = lineParts[1];
        String inputWords = lineParts[2];
        String responseText = lineParts[3];
        String wordsGained = lineParts[4];
        String locationsGained = lineParts[5];
        String peopleGained = lineParts[6];
        int favor = 0;
        try {
            favor = Integer.parseInt(lineParts[7]);
        } catch (NumberFormatException e) {
            System.out.println("Could not translate text \"" + lineParts[4] + "\" from favor column to integer.");
        }
        Map<String, Integer> skillEffects = new LinkedHashMap<String, Integer>();
        for (int i = 8; i < 11; i++) {
            String[] skillEffectParts = lineParts[i].split(" ");
            if (skillEffectParts.length == 2)
                try {
                    int skillEffectValue = Integer.parseInt(skillEffectParts[1]);
                    skillEffects.put(skillEffectParts[0], skillEffectValue);
                } catch (NumberFormatException e) {
                    System.out.println("Could not interpret value \"" + skillEffectParts[1] + "\" from skill effect column to integer.");
                }
        }

        //Create BasicDialogueOption with data
        BasicDialogueOption dialogueOption = new BasicDialogueOption(person, location, inputWords, responseText,
                wordsGained, locationsGained, peopleGained);
        if (favor != 0)
            dialogueOption.addFavorEffect(person, favor);
        for (Map.Entry<String, Integer> skillEffect : skillEffects.entrySet())
            dialogueOption.addSkillEffect(skillEffect.getKey(), skillEffect.getValue());

        return dialogueOption;
    }

    //public long getStartTime() {
    //    return _startTime;
    //}

    public long getTimeElapsed() {
        return _timeElapsed;
    }

    public void updateTimeElapsed()
    {
        long time = SystemClock.uptimeMillis();
        long deltaTime = time - _currTime;
        _timeElapsed += deltaTime;
        _currTime = time;

        if (_timeElapsed >= MILLISECONDS_PER_YEAR)
        {
            _timeElapsed -= MILLISECONDS_PER_YEAR;
            _age += 1;

            if (_age >= LifeStage.TEEN.getMinAge())
            {
                _lifeStage = LifeStage.TEEN;
            }
            else if (_age >= LifeStage.CHILD.getMinAge())
            {
                _lifeStage = LifeStage.CHILD;
            }

            randomizePeople();
        }
    }

    public void randomizePeople()
    {
        List<Location> locations = getLocations();
        List<Person> people = getPeople();
        for (Location location : locations) {
            location.randomizePeople(people);
        }
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
        if (word != null && word != "" && !_words.contains(word))
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
        return _currentLocationId;
    }

    public Location getCurrentLocation()
    {
        return _locations.get(_currentLocationId);
    }

    public void setCurrentLocationId(String locationId)
    {
        _currentLocationId = locationId;
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

    public void addLocation(String locationId) {
        for (Location location : _locations.values())
            if (location.getId() == locationId)
                return;
        switch (locationId) {
            case "bedroom":
                addLocation(locationId, "Bedroom", "Your favorite place", 0);
                break;
            case "playground":
                addLocation(locationId, "Playground", "Has the most awesome swingset", 0);
                break;
            case "library":
                addLocation(locationId, "Library", "Has the best books. This is your quiet place.", 0);
                break;
            case "friend_house":
                addLocation(locationId, "Friend's House", "You love hanging out at your friends' homes!", 0);
                break;
            case "bandroom":
                addLocation(locationId, "Bandroom", "A great place to let loose and jam.", 0);
                break;
            case "stage":
                addLocation(locationId, "Stage", "Don't be nervous! It's showtime!", 0);
                break;
            case "dance_studio":
                addLocation(locationId, "Dance Studio", "A place to move it to the beat.", 0);
                break;
        }
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

    public Person getCurrentPerson() {
        return _people.get(_currentPersonName);
    }

    public void setCurrentPerson(String personName) {
        _currentPersonName = personName;
    }

    public void addPerson(Person person) {
        _people.put(person.getName(), person);
    }

    public void addPerson(String personName) {
        for (Person person : _people.values())
            if (person.getName() == personName)
                return;
        switch(personName) {
            case "tj":
                Person tj = new Person(personName, "A bookworm, but a good friend.", 0);
                tj.setProbabilityForLocation("library", 1.0f);
                tj.setProbabilityForLocation("friend_house", 0.5f);
                tj.setProbabilityForLocation("playground", 0.1f);
                addPerson(tj);
                break;
            case "shawn":
                Person shawn = new Person(personName, "Loves playing all kinds of sports.", 0);
                shawn.setProbabilityForLocation("playground", 1.0f);
                shawn.setProbabilityForLocation("friend_house", 0.5f);
                shawn.setProbabilityForLocation("library", 0.1f);
                addPerson(shawn);
                break;
            case "judy":
                Person judy = new Person(personName, "A very talented pianist.", 0);
                judy.setProbabilityForLocation("bandroom", 1.0f);
                judy.setProbabilityForLocation("stage", 0.5f);
                judy.setProbabilityForLocation("friend_house", 0.5f);
                judy.setProbabilityForLocation("dance_studio", 0.1f);
                addPerson(judy);
                break;
            case "jennifer":
                Person jennifer = new Person(personName, "Loves to dance, very energetic.", 0);
                jennifer.setProbabilityForLocation("dance_studio", 1.0f);
                jennifer.setProbabilityForLocation("stage", 0.5f);
                jennifer.setProbabilityForLocation("friend_house", 0.5f);
                jennifer.setProbabilityForLocation("bandroom", 0.1f);
                addPerson(jennifer);
                break;
        }
    }

    public List<DialogueOption> getDialogOptions()
    {
        return _dialogueOptions;
    }
}