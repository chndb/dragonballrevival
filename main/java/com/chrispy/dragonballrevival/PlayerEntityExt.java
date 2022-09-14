package com.chrispy.dragonballrevival;

public interface PlayerEntityExt {

    double getTrainingMult();
    void addTenacityXP(int amount);
    void setTenacityXP(int amount);
    int getTenacityXP();
    void addTenacity(int amount);
    void setTenacity(int amount);
    int getTenacity();
    int getTenacityNextLevelXP();
    void updateTenacity();
    void addVitalityXP(int amount);
    void setVitalityXP(int amount);
    int getVitalityXP();
    void addVitality(int amount);
    void setVitality(int amount);
    int getVitality();
    int getVitalityNextLevelXP();
    void updateVitality();
    void addResilienceXP(int amount);
    void setResilienceXP(int amount);
    int getResilienceXP();
    void addResilience(int amount);
    void setResilience(int amount);
    int getResilience();
    int getResilienceNextLevelXP();
    void updateResilience();
}
