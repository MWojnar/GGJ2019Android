package com.ggj2019android.model;

public abstract class DialogueOption {
    protected String[] _inputWords;
    protected String _responseText;
    protected String[] _wordsGained;

    public DialogueOption() {
        _inputWords = new String[0];
        _responseText = "";
        _wordsGained = new String[0];
    }

    protected String[] splitWords(String words)
    {
        return words.trim().toLowerCase().split("\\s+");
    }

    public String[] getInputWords() { return _inputWords;}

    public String getResponseText() { return _responseText; }

    public String[] getGainedWords() { return _wordsGained; }

    public abstract boolean isAvailable(Game game);

    public abstract void runDialogueEffect(Game game);
}
