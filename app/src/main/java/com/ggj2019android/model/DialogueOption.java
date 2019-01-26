package com.ggj2019android.model;

public abstract class DialogueOption {
    protected String _text;
    protected String _responseText;
    protected int _id;

    public DialogueOption() {
        _text = "";
        _responseText = "";
        _id = 0;
    }

    public String getText() {
        return _text;
    }

    public String getResponseText() {
        return _responseText;
    }

    public int getId() {
        return _id;
    }

    public abstract boolean isAvailable(Game game);

    public abstract void runDialogueEffect(Game game);
}
