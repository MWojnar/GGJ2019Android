package com.ggj2019android.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Location {
    private String _id, _name, _description;
    private int _image;
    private List<Person> _people;

    public Location(String id, String name, String description, int image) {
        _id = id;
        _name = name;
        _description = description;
        _image = image;
        _people = new ArrayList<Person>();
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public String getDescription() {
        return _description;
    }

    public int getImage() {
        return _image;
    }

    public void randomizePeople(List<Person> people) {
        _people.clear();
        for (Person person : people)
            if (personPresent(person))
                _people.add(person);
    }

    public boolean personPresent(Person person) {
        Random randGen = new Random();
        float randFloat = randGen.nextFloat();
        return (randFloat <= person.getProbabilityForLocation(_id));
    }

    public void addPerson(Person person) {
        _people.add(person);
    }

    public void removePerson(Person person) {
        _people.remove(person);
    }
}
