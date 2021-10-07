package Main.skill;

import java.io.Serializable;

//kind of like a struct in c++
public class SkillBean implements Serializable {
    private String name = null;
    private String spCostInfo = null;
    private String initialSpInfo = null;
    private String skillUtilizationInfo = null;
    private String skillEffectInfo = null;
    private String spChargeType = null;
    private String spSkillActivation = null;
    private int level;
    int[][] skillTiles = null;

    public void setName(String name) {
        this.name = name;
    }

    public void setSpCostInfo(String spCostInfo) {
        this.spCostInfo = spCostInfo;
    }

    public void setInitialSpInfo(String initialSpInfo) {
        this.initialSpInfo = initialSpInfo;
    }

    public void setSkillUtilizationInfo(String skillUtilizationInfo) {
        this.skillUtilizationInfo = skillUtilizationInfo;
    }

    public void setSkillEffectInfo(String skillEffectInfo) {
        this.skillEffectInfo = skillEffectInfo;
    }

    public void setSpChargeType(String spChargeType) {
        this.spChargeType = spChargeType;
    }

    public void setSpSkillActivation(String spSkillActivation) {
        this.spSkillActivation = spSkillActivation;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setSkillTiles(int[][] skillTiles) {
        this.skillTiles = skillTiles;
    }

    public String getName() {
        return name;
    }

    public String getSpCostInfo() {
        return spCostInfo;
    }

    public String getInitialSpInfo() {
        return initialSpInfo;
    }

    public String getSkillUtilizationInfo() {
        return skillUtilizationInfo;
    }

    public String getSkillEffectInfo() {
        return skillEffectInfo;
    }

    public String getSpChargeType() {
        return spChargeType;
    }

    public String getSpSkillActivation() {
        return spSkillActivation;
    }

    public int getLevel() {
        return level;
    }

    public int[][] getSkillTiles() {
        return skillTiles;
    }
}
