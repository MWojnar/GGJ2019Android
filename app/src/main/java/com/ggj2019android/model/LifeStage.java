package com.ggj2019android.model;

public enum LifeStage {
    INFANT(0),
    CHILD(6),
    TEEN(12);

    private int _minAge;

    LifeStage(int minAge)
    {
        _minAge = minAge;
    }

    public int getMinAge()
    {
        return _minAge;
    }

}
