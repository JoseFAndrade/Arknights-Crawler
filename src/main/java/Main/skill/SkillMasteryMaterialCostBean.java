package Main.skill;

import Main.Manager.MaterialFactoryManager;

import java.util.ArrayList;
import java.util.List;

public class SkillMasteryMaterialCostBean {

    String imageUrlLevel; //icon of the mastery level
    String levelRequisite;
    String rarityRequisiteUrl; //icon of the rarity needed to increase skill mastery
    String timeToUpgrade;
    MaterialFactoryManager materialFactoryManager = null;

    public String getImageUrlLevel() {
        return imageUrlLevel;
    }

    public String getLevelRequisite() {
        return levelRequisite;
    }

    public String getRarityRequisiteUrl() {
        return rarityRequisiteUrl;
    }

    public String getTimeToUpgrade() {
        return timeToUpgrade;
    }

    public MaterialFactoryManager getMaterialFactoryManager() {
        return materialFactoryManager;
    }

    public void setImageUrlLevel(String imageUrlLevel) {
        this.imageUrlLevel = imageUrlLevel;
    }

    public void setLevelRequisite(String levelRequisite) {
        this.levelRequisite = levelRequisite;
    }

    public void setRarityRequisiteUrl(String rarityRequisiteUrl) {
        this.rarityRequisiteUrl = rarityRequisiteUrl;
    }

    public void setTimeToUpgrade(String timeToUpgrade) {
        this.timeToUpgrade = timeToUpgrade;
    }

    public void setMaterialFactoryManager(MaterialFactoryManager materialFactoryManager) {
        this.materialFactoryManager = materialFactoryManager;
    }
}
