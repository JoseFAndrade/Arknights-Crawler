package Main.util;

import Main.Main;

public class Constants {

    /*
     -------------------------------------------------------------------------------------
     URL
     -------------------------------------------------------------------------------------
    */
    public static final String materialList = "https://gamepress.gg/arknights/database/material-list";
    public static final String operatorList = "https://gamepress.gg/arknights/tools/interactive-operator-list#tags=null##stats";





     /*
     -------------------------------------------------------------------------------------
     Operator stats
     -------------------------------------------------------------------------------------
    */

     //TODO: figure out how to get range, picture for each E0,E1,E2

    //div class
    //will need to count the number of img src for star there is - that will tell us the rarity
    public static final String opRarity = "rarity-cell";


    //data-rank attribute within a button class
    //we need to press these in order to change the values
    public static final String nonElite = "ne";
    public static final String EliteOne = "e1";
    public static final String EliteTwo = "e2";

    //<input type="range" min="1" max="50" value="1" class="slider" id="myRange">
    //TODO: make some modifications to this before we get the stats for the character because stats are at level 1 instead of max lvl

    //ID's to obtain the information for stats
    public static final String statHP = "stat-hp";
    public static final String statAttack = "stat-atk";
    public static final String statDef = "stat-def";

    public static final String artsResist = "arts-resist";
    public static final String operatorCost = "operator-cost";
    public static final String operatorBlock = "operator-block";
    //unchangeable stats based on rank or level
    //these are a bit harder to obtain
    //TODO come back to these because they are a bit harder

    //static final String

    /*
    -------------------------------------------------------------------------------------
    Skills
    -------------------------------------------------------------------------------------
     */

    //3 is only for 6*s
    public static final String skillOneID = "skill-tab-1";
    public static final String skillTwoID = "skill-tab-2";
    public static final String skillThreeID = "skill-tab-3";

    //<changes with skill level>
    public static final String skillEffectDescriptionIndex = "effect-description skill-upgrade-tab-%f";

    //div for name information
    public static final String skillTitleCell = "skill-title-cell";

    //div for sp information
    //where both of these use the effect-description skill-upgrade-tab-# <'current-tab' if selected'>
    //<changes with skill level>
    public static final String spCost = "sp-cost";
    public static final String initialSp = "initial-sp";

    //div for sp charge type
    public static final String spChargeType = "sp-charge-type skill-effect-title";

    //div for skill activate
    public static final String skillActivation = "skill-activation skill-effect-title";

    //div for duration of skill
    //where both of these use the effect-description skill-upgrade-tab-# <'current-tab' if selected'>
    //<changes with skill level>
    public static final String skillDuration = "skill-duration skill-effect-title";

    //div for the description of the skill
    //where both of these use the effect-description skill-upgrade-tab-# <'current-tab' if selected'>
    //to get the full value of this you will need to get 'skill-description-value' this can be put multiple times in one description div
    public static final String skillDescription = "skill-description";

    //this might not be applicable to all skills
    public static final String skillRange = "skill-range-box";

    //Range box important const where they are all within classes

    public static final String characterBox = "fill-box margin";
    public static final String attackGrid = "empty-box margin";
    public static final String nullGrid = "null-box margin";
}