package com.ggj2019android.model;

import java.util.List;
import java.util.Map;

public class Person {
    private String _name, _description;
    private int _image, _favor;
    private Map<String, Float> _locations;
    private List<Interaction> _availableInteractions;

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

    public List<Interaction> getAvailableInteractions() {
        return _availableInteractions;
    }

    public void startInteracting(Game game) throws UnsupportedOperationException {
        //Currently has no implementation.
        throw new UnsupportedOperationException();
    }
}
