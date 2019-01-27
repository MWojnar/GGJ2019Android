package com.ggj2019android.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Person
{
    protected String _name, _description;
    protected int _image, _favor;
    protected Map<String, Float> _locations;
    //protected List<Interaction> _availableInteractions;

    public Person() {
        _name = "";
        _description = "";
        _image = 0;
        _favor = 0;
        _locations = new LinkedHashMap<>();
        //_availableInteractions = new ArrayList<>();
    }

    public Person(String name, String desc, int image)
    {
        _name = name;
        _description = desc;
        _image = image;
        _favor = 0;
        _locations = new LinkedHashMap<>();
        //_availableInteractions = new ArrayList<>();
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

    public int getFavor() {
        return _favor;
    }

    public void adjustFavor(int adjustment) {
        _favor += adjustment;
    }

    public float getProbabilityForLocation(String locationId) {
        return _locations.containsKey(locationId) ? _locations.get(locationId) : 0.0f;
    }

    public void setProbabilityForLocation(String locationId, float probability)
    {
        _locations.put(locationId, probability);
    }

    //public List<Interaction> getAvailableInteractions() {
    //    return _availableInteractions;
    //}

    //public abstract void startInteracting(Game game);
}
