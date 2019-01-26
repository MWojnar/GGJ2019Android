package com.ggj2019android.model;

public abstract class Interaction {
    protected Person _person;
    protected String _locationId;
    protected String _name;
    protected String _text;

    public Interaction() {
        _person = null;
        _locationId = "";
        _name = "";
        _text = "";
    }

    public abstract boolean isAvailable(Game game);

    public abstract void say(Game game, String phrase);
}