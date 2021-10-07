package Main.Manager;

import Main.unit.Talent;
import Main.unit.TalentBean;

import java.util.ArrayList;
import java.util.List;

public class TalentManager {

    List<Talent> talentList = new ArrayList<>();

    public TalentManager(List<Talent> talents){
        for(Talent each: talents)
            talentList.add(each);
    }

    public String toString(){
        String result = "Talent List:\n";
        for(Talent each: talentList){
            result += each.toString();
            result += "\n"; //just to add in an extra new line
        }
        return result.trim();
    }

    public List<TalentBean> getTalentBeanList(int index){
        List<TalentBean> talentBeanList = new ArrayList<>();
        for(int i = 0; i < talentList.get(index).getLength(); i++){
            talentBeanList.add (talentList.get(index).getTalentBean(i));
        }
        return talentBeanList;
    }

    public int size(){
        return talentList.size();
    }
}
