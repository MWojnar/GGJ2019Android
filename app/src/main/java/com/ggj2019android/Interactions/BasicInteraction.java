package com.ggj2019android.Interactions;

import com.ggj2019android.model.DialogueOption;
import com.ggj2019android.model.Game;
import com.ggj2019android.model.Interaction;
import com.ggj2019android.model.Location;
import com.ggj2019android.model.Person;

import java.util.ArrayList;
import java.util.List;

public class BasicInteraction extends Interaction {
    private List<DialogueOption> _dialogueOptions;

    public BasicInteraction(Person person, String locationId, String name, String text) {
        super();
        _person = person;
        _locationId = locationId;
        _name = name;
        _text = text;
        _dialogueOptions = new ArrayList<DialogueOption>();
    }

    @Override
    public boolean isAvailable(Game game) {
        return game.getCurrentLocationId() == _locationId;
    }

    @Override
    public void say(Game game, String phrase) {

    }

    public void addDialogueOption(DialogueOption dialogueOption) {
        _dialogueOptions.add(dialogueOption);
    }

    /*public DialogueOption getDialogueOptionFromId(int id) {
        for (DialogueOption dialogueOption : _dialogueOptions)
            if (dialogueOption.getId() == id)
                return dialogueOption;
        return null;
    }*/
}
