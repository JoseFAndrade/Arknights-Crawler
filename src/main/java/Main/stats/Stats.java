package Main.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//change this class at some point
public class Stats {
    ChangeableStats E0L1;
    ChangeableStats E0LM;
    ChangeableStats E1L1;
    ChangeableStats E1LM;
    ChangeableStats E2L1;
    ChangeableStats E2LM;
    Integer redeployTime;
    Double attackInterval;

    public Stats(String redeployTime, String attackInterval){
        this.redeployTime = Integer.parseInt(redeployTime.trim());
        this.attackInterval = Double.parseDouble(attackInterval.trim());
    }

    public void setChangeableStats(String level, ChangeableStats cs){
        switch(level){
            case "E0L1":
                E0L1 = cs;
                break;
            case "E0LM":
                E0LM = cs;
                break;
            case "E1L1":
                E1L1 = cs;
                break;
            case "E1LM":
                E1LM = cs;
                break;
            case "E2L1":
                E2L1 = cs;
                break;
            case "E2LM":
                E2LM = cs;
                break;
        }
    }

    public ChangeableStats getChangeableStats(String level){
        switch(level){
            case "E0L1":
                return E0L1;
            case "E0LM":
                return E0LM;
            case "E1L1":
                return E1L1;
            case "E1LM":
                return E1LM;
            case "E2L1":
                return E2L1;
            case "E2LM":
                return E2LM;
        }
        return null;
    }

    public String toString(){
        String result = "Stats of the unit at different levels:\n";
        result+= String.format("\tLevel E0L1: %s\n\tLevel E0LM: %s\n\tLevel E1L1: %s\n\tLevel E1LM: %s\n\tLevel E2L1: %s\n\tLevel E2LM: %s",
                Objects.toString(E0L1,"Does not exist"),
                Objects.toString(E0LM,"Does not exist"),
                Objects.toString(E1L1,"Does not exist"),
                Objects.toString(E1LM,"Does not exist"),
                Objects.toString(E2L1,"Does not exist"),
                Objects.toString(E2LM,"Does not exist"));
        return result;
    }

    public List<StatBean> generateBeanList(){
        List<StatBean> statBeanList = new ArrayList<>();
        if(E0L1!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E0L1));
        if(E0LM!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E0LM));
        if(E1L1!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E1L1));
        if(E1LM!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E1LM));
        if(E2L1!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E2L1));
        if(E2LM!= null)
            statBeanList.add(generateStatBeanFromChangeableStat(E2LM));
        return statBeanList;
    }

    private StatBean generateStatBeanFromChangeableStat(ChangeableStats changeableStats){

        StatBean statBean = new StatBean();
        statBean.setArtRes(changeableStats.artRes);
        statBean.setAtk(changeableStats.atk);
        statBean.setBlock(changeableStats.block);
        statBean.setDef(changeableStats.def);
        statBean.setDpCost(changeableStats.dpCost);
        statBean.setHp(changeableStats.hp);
        statBean.setLevel(changeableStats.level);
        statBean.setRedeployTime(this.redeployTime);
        statBean.setAttackInterval(this.attackInterval);

        return statBean;
    }
}


