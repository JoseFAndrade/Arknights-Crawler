package Main.skill;

import Main.Manager.MaterialFactoryManager;

import java.util.ArrayList;
import java.util.List;

//This is where I'm going to keep the information about the material cost from Skill levels 2-7
//Does not include 1 because it does not have a material skill cost
public class SkillMaterialCost {
    List<String> levelUpgradeList = new ArrayList<>();
    List<String> requisiteLevel = new ArrayList<>();
    List<String> requisiteRarityURL = new ArrayList<>();
    List<MaterialFactoryManager> materialFactoryManager = new ArrayList<>();

    //there will be no order to this function so it is going to assume that the user is putting it in order
    public void addSkillCost(String levelUpgradeString, String requisiteLevelString, String requisiteRarityUrlString,
                             MaterialFactoryManager materialFactoryManager){
        this.levelUpgradeList.add(levelUpgradeString);
        this.requisiteLevel.add(requisiteLevelString);
        this.requisiteRarityURL.add(requisiteRarityUrlString);
        this.materialFactoryManager.add(materialFactoryManager);
    }

    public String toString(){
        String result = "Skill Material Cost (Level 2-7)\n";
            for(int i = 0; i < levelUpgradeList.size(); i++){
                result+= String.format("Level: %s. Level Requirement: %s. Elite Requirement: %s.\n",
                        levelUpgradeList.get(i), requisiteLevel.get(i), requisiteRarityURL.get(i)   );
                result+= materialFactoryManager.get(i).toString() + "\n";
            }
        return result.trim();
    }

    public MaterialFactoryManager getTotalCount(){
        System.out.println(MaterialFactoryManager.combineMaterialCost(materialFactoryManager));
        return MaterialFactoryManager.combineMaterialCost(materialFactoryManager);
    }

    public List<SkillMaterialCostBean> generateBeanList(){
        List<SkillMaterialCostBean> skillMaterialCostBeans = new ArrayList<>();
        for(int i = 0; i < levelUpgradeList.size(); i++){
            SkillMaterialCostBean skillMaterialCostBean = new SkillMaterialCostBean();

            skillMaterialCostBean.setLevelUpgrade(levelUpgradeList.get(i));
            skillMaterialCostBean.setMaterialFactoryManager( materialFactoryManager.get(i) );
            skillMaterialCostBean.setRequisiteLevel(requisiteLevel.get(i));
            skillMaterialCostBean.setRequisiteRarityURL(requisiteRarityURL.get(i));

            skillMaterialCostBeans.add(skillMaterialCostBean);
        }
        return skillMaterialCostBeans;
    }

}
