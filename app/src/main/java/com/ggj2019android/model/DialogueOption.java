package com.ggj2019android.model;

public abstract class DialogueOption {
    protected String _inputText;
    protected String _responseText;
    protected String _wordsGained;
    protected String _locationsGained;
    protected String _peopleGained;

    public DialogueOption() {
        _inputText = "";
        _responseText = "";
        _wordsGained = "";
        _locationsGained = "";
        _peopleGained = "";
    }

    public String getInputText() {
        return _inputText;
    }

    public String getResponseText() {
        return _responseText;
    }

    public abstract boolean isAvailable(Game game);

    public abstract void runDialogueEffect(Game game);
}
