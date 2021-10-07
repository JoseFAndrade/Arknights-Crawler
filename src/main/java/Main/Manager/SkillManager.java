package Main.Manager;

import Main.skill.Skill;
import Main.skill.SkillBean;

import java.util.ArrayList;
import java.util.List;

public class SkillManager {

    List<Skill> skillList = new ArrayList<>();

    public SkillManager(Skill... skills){
        for(Skill each: skills){
            skillList.add(each);
        }
    }

    @Override
    public String toString(){
        String result = "";
        for(Skill each: this.skillList){
            result+= each.toString();
            result+="\n";
        }
        return result;
    }

    public int size(){
        return skillList.size();
    }

    public void  add(Skill skill){
        skillList.add(skill);
    }

    public List<SkillBean> getSkillBeanList(int skillIndex){
        List<SkillBean> skillBeanList = new ArrayList<>();
        Skill skill = skillList.get(skillIndex);
        for(int i = 0; i < skill.size(); i++){
            skillBeanList.add(skill.getSkillBean(i));
        }
        return skillBeanList;
    }


}
