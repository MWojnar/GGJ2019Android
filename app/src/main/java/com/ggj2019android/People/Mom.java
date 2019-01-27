package com.ggj2019android.People;

import com.ggj2019android.Interactions.BasicInteraction;
import com.ggj2019android.model.BasicDialogueOption;
import com.ggj2019android.model.Game;
import com.ggj2019android.model.Interaction;
import com.ggj2019android.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Mom extends Person {
    //private List<Interaction> _possibleInteractions;

    public Mom() {
        super();
        _name = "Mom";
        _description = "Your Mother";
        _image = 0;
        _favor = 1;
        setProbabilityForLocation("child_bedroom", 1.0f);
        //initializeInteractions();
    }

    /*
    @Override
    public void startInteracting(Game game) {
        //Determine available interactions.
        _availableInteractions.clear();
        for (Interaction interaction : _possibleInteractions)
            if (interaction.isAvailable(game))
                _availableInteractions.add(interaction);
    }

    private void initializeInteractions() {
        _possibleInteractions = new ArrayList<Interaction>();

        Interaction interaction = new BasicInteraction(this, "child_bedroom", "Talk", "Honey come quick! He just said Momma! Maybe he'll say it again?");

        BasicDialogueOption dialogueOption = new BasicDialogueOption("Goo goo!", "Oh well...", 0);
        ((BasicInteraction) interaction).addDialogueOption(dialogueOption);

        dialogueOption = new BasicDialogueOption("Mama!", "See?  He's so smart!", 0);
        dialogueOption.addSkillRequirement("Vocab", 1);
        dialogueOption.addSkillEffect("Vocab", 1);
        ((BasicInteraction) interaction).addDialogueOption(dialogueOption);

        dialogueOption = new BasicDialogueOption("(juggle pacifiers)", "...", 0);
        dialogueOption.addSkillRequirement("Dexterity", 100);
        dialogueOption.addSkillEffect("Dexterity", 100);
        ((BasicInteraction) interaction).addDialogueOption(dialogueOption);

        _possibleInteractions.add(interaction);
    }
    */
}
