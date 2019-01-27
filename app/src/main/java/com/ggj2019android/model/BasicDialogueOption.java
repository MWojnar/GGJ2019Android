package com.ggj2019android.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class BasicDialogueOption extends DialogueOption
{
    protected String _person;
    protected String _location;
    protected Map<String, Integer> _skillRequirements;
    protected Map<String, Integer> _skillEffects;
    protected Map<String, Integer> _favorEffects;
    protected String[] _locationsGained;
    protected String[] _peopleGained;

    public BasicDialogueOption(
            String person,
            String location,
            String inputText,
            String responseText,
            String wordsGained,
            String locationsGained,
            String peopleGained)
    {
        super();
        _person = person;
        _location = location;
        _inputWords = splitWords(inputText);
        _responseText = responseText;
        _wordsGained = splitWords(wordsGained);
        _locationsGained = splitWords(locationsGained);
        _peopleGained = splitWords(peopleGained);
        _skillRequirements = new LinkedHashMap<>();
        _skillEffects = new LinkedHashMap<>();
        _favorEffects = new LinkedHashMap<>();
    }

    @Override
    public boolean isAvailable(Game game) {
        if (!_person.equalsIgnoreCase(game.getCurrentPersonName()))
        {
            return false;
        }
        //if (!_location.equalsIgnoreCase(game.getCurrentLocationId()))
        //{
        //    return false;
        //}

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
        for (String word : _wordsGained)
            game.addWord(word);
        for (String location : _locationsGained)
            game.addLocation(location);
        for (String person : _peopleGained)
            game.addPerson(person);
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