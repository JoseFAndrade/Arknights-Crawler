package Main.unit;

import Main.skill.*;
import Main.stats.StatBean;
import Main.stats.Stats;
import Main.stats.TrustStats;
import Main.Manager.EliteMaterialCostManager;
import Main.Manager.SkillManager;
import Main.Manager.SkillMasteryMaterialCostManager;
import Main.Manager.TalentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Unit {
    //maybe make a class for this info here?
    private String name;
    private int rarity;
    private String position;
    private String attackType;
    private String classType;
    private List<String> tags;
    private List<String> archetype;
    private String trait;
    private String unitImage;

    //TODO MISSING ATTACK Range

    private SkillManager skillManager = null;
    private TalentManager talentManager = null;
    private Stats stats = null;
    private Potentials potentials = null;
    private TrustStats trustStats = null;

    private SkillMaterialCost skillMaterialCost;
    private SkillMasteryMaterialCostManager skillMasteryMaterialCostManager;
    private EliteMaterialCostManager eliteMaterialCostManager;

    public Unit(String name, SkillManager skillManager, TalentManager talentManager, Stats stats, Potentials potentials, TrustStats trustStats) {
        this.name = name;
        this.skillManager = skillManager;
        this.talentManager = talentManager;
        this.stats = stats;
        this.potentials = potentials;
        this.trustStats = trustStats;
    }

    public Unit(String name, int rarity, String position, String attackType, String classType, List<String> tags, List<String> archetype, String trait, SkillMasteryMaterialCostManager skillMasteryMaterialCostManager, SkillMaterialCost skillMaterialCost, EliteMaterialCostManager eliteMaterialCostManager, SkillManager skillManager, TalentManager talentManager, Stats stats, Potentials potential, TrustStats trustStats) {
        this.name = name;
        this.rarity = rarity;
        this.position = position;
        this.attackType = attackType;
        this.classType = classType;
        this.tags = tags;
        this.archetype = archetype;
        this.trait = trait;
        this.skillManager = skillManager;
        this.talentManager = talentManager;
        this.stats = stats;
        this.potentials = potential;
        this.trustStats = trustStats;

        this.skillMasteryMaterialCostManager = skillMasteryMaterialCostManager;
        this.skillMaterialCost = skillMaterialCost;
        this.eliteMaterialCostManager = eliteMaterialCostManager;
    }

    public Unit(String name, int rarity, String position, String attackType, String classType, List<String> tags,
                List<String> archetype, String trait, SkillMasteryMaterialCostManager skillMasteryMaterialCostManager,
                SkillMaterialCost skillMaterialCost, EliteMaterialCostManager eliteMaterialCostManager, SkillManager skillManager,
                TalentManager talentManager, Stats stats, Potentials potential, TrustStats trustStats, String unitImage) {
        this.name = name;
        this.rarity = rarity;
        this.position = position;
        this.attackType = attackType;
        this.classType = classType;
        this.tags = tags;
        this.archetype = archetype;
        this.trait = trait;
        this.skillManager = skillManager;
        this.talentManager = talentManager;
        this.stats = stats;
        this.potentials = potential;
        this.trustStats = trustStats;

        this.skillMasteryMaterialCostManager = skillMasteryMaterialCostManager;
        this.skillMaterialCost = skillMaterialCost;
        this.eliteMaterialCostManager = eliteMaterialCostManager;

        this.unitImage = unitImage;
    }

    public String toString(){
        String result = "";
        result+= String.format("Name: %s. Class: %s. Rarity: %s. Position: %s. AttackType: %s.\n",name,classType,rarity,position,attackType);
        result+= String.format("Tags: %s. Archetype: %s, Trait: %s.\n", tags.toString(), archetype, trait);
        result += String.format("%s\n\n%s\n\n%s\n\n%s\n\n%s",potentials,trustStats,talentManager,stats,
                Objects.toString(skillManager,"This character does not have any skills."));

        result+= String.format("%s\n\n%s\n\n%s",
                Objects.toString(skillMaterialCost,"This character does have any skills so there is no cost."),
                Objects.toString(skillMasteryMaterialCostManager,"This character does not have any mastery skills."),
                Objects.toString(eliteMaterialCostManager,"This unit is unable to go past E0."));
        return result;
    }

    public int getRarity() {
        return rarity;
    }

    public String getPosition() {
        return position;
    }

    public String getAttackType() {
        return attackType;
    }

    public String getClassType() {
        return classType;
    }

    public List<String> getTags() {
        return tags;
    }

    public List<String> getArchetype() {
        return archetype;
    }

    public String getTrait() {
        return trait;
    }

    /*
    private Skill skillOne = null;
    private Skill skillTwo = null;
    private Skill skillThree = null;


    Unit(String name){
        this.name = name;
    }


    public void setSkillOne(Skill skill){
        this.skillOne = skill;
    }

    public void setSkillTwo(Skill skill){
        this.skillTwo = skill;
    }

    public void setSkillThree(Skill skill){
        this.skillThree = skill;
    }
    
    public void displaySkill(int numb){
        Skill temp = null;
        switch(numb){
            case 1:
                temp = skillOne;
                break;
            case 2:
                temp = skillTwo;
                break;
            case 3:
                temp = skillThree;
                break;
        }
        System.out.println(temp);
    }

     */

    public String getName() {
        return name;
    }

    /*
    This returns a list of Skill Bean List where the size is equal to the number of skills (Max 3).
    Each Skill has their own list of Skill Beans.
     */
    public List<List<SkillBean>> getListOfSkillBeans(){
        List<List<SkillBean>> allSkillBeanList = new ArrayList<>();
        int numOfSkills = skillManager.size();
        for(int i = 0; i < numOfSkills; i++){
            allSkillBeanList.add(skillManager.getSkillBeanList(i));
        }
        return allSkillBeanList;
    }

    /*
    Same as above function except this is for Talents
     */
    public List<List<TalentBean>> getListOfTalentBeans(){
        List<List<TalentBean>> allTalentBeanList = new ArrayList<>();
        int numOfTalents = talentManager.size();
        for(int i = 0; i < numOfTalents; i++){
            allTalentBeanList.add(talentManager.getTalentBeanList(i));
        }
        return allTalentBeanList;
    }

    /*
    Same as above
     */
    public List<List<SkillMasteryMaterialCostBean>> getListOfSkillMasteryCostBean(){
        List<List<SkillMasteryMaterialCostBean>> allSkillMasteryBeanList = new ArrayList<>();
        int numOfMasterySkills = skillMasteryMaterialCostManager.size();
        for(int i =0; i < numOfMasterySkills; i++){
            allSkillMasteryBeanList.add( skillMasteryMaterialCostManager.getBeanList(i) );
        }
        return allSkillMasteryBeanList;
    }

    public List<StatBean> getStatBeansList(){
        return stats.generateBeanList();
    }

    public List<String> getTrustStats(){
        return trustStats.getTrustStats();
    }

    public PotentialBean getPotentialBean(){
        return potentials.generatePotentialBean();
    }

    public List<SkillMaterialCostBean> getSkillCostBeansList(){
        return skillMaterialCost.generateBeanList();
    }

    public List<EliteMaterialBean> getEliteBeansList(){return eliteMaterialCostManager.generateBeanList();}

    public String getUnitImage() {
        return unitImage;
    }
}

