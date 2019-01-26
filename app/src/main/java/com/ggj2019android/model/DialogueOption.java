package com.ggj2019android.model;

public abstract class DialogueOption {
    protected String _inputText;
    protected String _responseText;
    protected int _id;

    public DialogueOption() {
        _inputText = "";
        _responseText = "";
        _id = 0;
    }

    public String getInputText() {
        return _inputText;
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
