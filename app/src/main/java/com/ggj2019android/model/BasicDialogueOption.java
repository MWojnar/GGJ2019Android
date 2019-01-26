package com.ggj2019android.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BasicDialogueOption extends DialogueOption
{
    private String _person;
    private String _location;
    private Map<String, Integer> _skillRequirements;
    private Map<String, Integer> _skillEffects;
    private Map<String, Integer> _favorEffects;

    public BasicDialogueOption(String person, String location, String inputText, String responseText, int id) {
        super();
        _person = person;
        _location = location;
        _inputText = inputText;
        _responseText = responseText;
        _id = id;
        _skillRequirements = new LinkedHashMap<String, Integer>();
        _skillEffects = new LinkedHashMap<String, Integer>();
        _favorEffects = new LinkedHashMap<String, Integer>();
    }

    @Override
    public boolean isAvailable(Game game) {
        if (!_person.equalsIgnoreCase(game.getCurrentPersonName()))
        {
            return false;
        }
        if (!_location.equalsIgnoreCase(game.getCurrentLocationId()))
        {
            return false;
        }

        for (Map.Entry<String, Integer> skillRequirement : _skillRequirements.entrySet())
            if (game.getSkill(skillRequirement.getKey()) < skillRequirement.getValue())
                return false;
        return true;
    }

    @Override
    public void runDialogueEffect(Game game) {
        for (Map.Entry<String, Integer> skillEffect : _skillEffects.entrySet())
            game.increaseSkill(skillEffect.getKey(), skillEffect.getValue());
        for (Map.Entry<String, Integer> favorEffect : _favorEffects.entrySet())
            game.getPerson(favorEffect.getKey()).adjustFavor(favorEffect.getValue());
    }

    public void addSkillRequirement(String skillName, int minValue) {
        _skillRequirements.put(skillName, minValue);
    }

    public void addSkillEffect(String skillName, int effect) {
        _skillEffects.put(skillName, effect);
    }

    public void addFavorEffect(String personName, int effect) {
        _favorEffects.put(personName, effect);
    }
}