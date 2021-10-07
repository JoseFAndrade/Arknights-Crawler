package Main.Manager;

import Main.skill.SkillMasteryMaterialCost;
import Main.skill.SkillMasteryMaterialCostBean;

import java.util.ArrayList;
import java.util.List;

public class SkillMasteryMaterialCostManager {
    List<SkillMasteryMaterialCost> skillMasteryMaterialCosts = new ArrayList<>();


    public SkillMasteryMaterialCostManager(SkillMasteryMaterialCost... skills){
        for(SkillMasteryMaterialCost each: skills){
            skillMasteryMaterialCosts.add(each);
        }
    }

    public SkillMasteryMaterialCostManager(){};

    //this function will be here in case it is used over the constructor
    public void addSkillMastery(SkillMasteryMaterialCost skill){
        this.skillMasteryMaterialCosts.add(skill);
    }

    @Override
    public String toString(){
        String result = "Skill Mastery Cost:\n";
        int count = 0;
        for(SkillMasteryMaterialCost each: this.skillMasteryMaterialCosts){
            count++;
            result+= String.format("This is Skill: %s\n",count);
            result+= each.toString();
            //result+="\n";
        }
        return result;
    }

    public MaterialFactoryManager getTotalCount(){
        List<MaterialFactoryManager> result = new ArrayList<>();
        for(SkillMasteryMaterialCost each: skillMasteryMaterialCosts){
            result.addAll(each.getMaterialFactoryManagers());
        }

        System.out.println(MaterialFactoryManager.combineMaterialCost(result));
        return MaterialFactoryManager.combineMaterialCost(result);
    }

    public int size(){
        return skillMasteryMaterialCosts.size();
    }

    //todo remove this from here at some point.... idk?
    //index is for the skill
    public List<SkillMasteryMaterialCostBean> getBeanList(int index){
        List<SkillMasteryMaterialCostBean> skillMasteryMaterialCostBeanList = new ArrayList<>();
        SkillMasteryMaterialCost skillMasteryMaterialCost = skillMasteryMaterialCosts.get(index);

        for(int i = 0; i < skillMasteryMaterialCost.size(); i++){
            skillMasteryMaterialCostBeanList.add(skillMasteryMaterialCost.getBean(i));
        }

        return skillMasteryMaterialCostBeanList;
    }


}
