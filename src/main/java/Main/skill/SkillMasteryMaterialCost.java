package Main.skill;

import Main.Manager.MaterialFactoryManager;

import java.util.ArrayList;
import java.util.List;

//This will end up holding the Material Cost for one of the Mastery Skills
//The SkillMasteryMaterialCostManager will be in charge of holding all of the 3 SkillMasteryMaterialCost classes
public class SkillMasteryMaterialCost {

    List<String> imageUrlLevel = new ArrayList<>(); //icon of the mastery level
    List<String> levelRequisite = new ArrayList<>();
    List<String> rarityRequisiteUrl = new ArrayList<>(); //icon of the rarity needed to increase skill mastery
    List<String> timeToUpgrade = new ArrayList<>();
    List<MaterialFactoryManager> materialFactoryManagers = new ArrayList<>();

    public void addSkillCost(String levelUpgradeString, String requisiteLevelString, String requisiteRarityUrlString,
                             String upgradeTime, MaterialFactoryManager materialFactoryManager){
        this.imageUrlLevel.add(levelUpgradeString);
        this.levelRequisite.add(requisiteLevelString);
        this.rarityRequisiteUrl.add(requisiteRarityUrlString);
        this.materialFactoryManagers.add(materialFactoryManager);
        this.timeToUpgrade.add(upgradeTime);
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < imageUrlLevel.size(); i++){
            result+= String.format("Level: %s. Level Requirement: %s. Elite Requirement: %s.\n",
                    imageUrlLevel.get(i), levelRequisite.get(i), rarityRequisiteUrl.get(i)   );
            result+= materialFactoryManagers.get(i).toString() + "\n";
        }
        return result;
    }

    public int size(){
        return imageUrlLevel.size();
    }

    public List<MaterialFactoryManager> getMaterialFactoryManagers() {
        return materialFactoryManagers;
    }

    public SkillMasteryMaterialCostBean getBean(int index){
        SkillMasteryMaterialCostBean skillMasteryMaterialCostBean = new SkillMasteryMaterialCostBean();

        skillMasteryMaterialCostBean.setImageUrlLevel(imageUrlLevel.get(index));
        skillMasteryMaterialCostBean.setLevelRequisite(levelRequisite.get(index));
        skillMasteryMaterialCostBean.setMaterialFactoryManager(materialFactoryManagers.get(index));
        skillMasteryMaterialCostBean.setRarityRequisiteUrl(rarityRequisiteUrl.get(index));
        skillMasteryMaterialCostBean.setTimeToUpgrade(timeToUpgrade.get(index));

        return skillMasteryMaterialCostBean;
    }


}
