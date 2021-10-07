package Main.skill;

import Main.Manager.MaterialFactoryManager;

import java.util.ArrayList;
import java.util.List;

public class SkillMaterialCostBean {
    private String levelUpgrade = "";
    private String requisiteLevel = "";
    private String requisiteRarityURL = "";
    private MaterialFactoryManager materialFactoryManager = null;

    public String getLevelUpgrade() {
        return levelUpgrade;
    }

    public String getRequisiteLevel() {
        return requisiteLevel;
    }

    public String getRequisiteRarityURL() {
        return requisiteRarityURL;
    }

    public MaterialFactoryManager getMaterialFactoryManager() {
        return materialFactoryManager;
    }

    public void setLevelUpgrade(String levelUpgrade) {
        this.levelUpgrade = levelUpgrade;
    }

    public void setRequisiteLevel(String requisiteLevel) {
        this.requisiteLevel = requisiteLevel;
    }

    public void setRequisiteRarityURL(String requisiteRarityURL) {
        this.requisiteRarityURL = requisiteRarityURL;
    }

    public void setMaterialFactoryManager(MaterialFactoryManager materialFactoryManager) {
        this.materialFactoryManager = materialFactoryManager;
    }
}
