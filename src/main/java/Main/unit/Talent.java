package Main.unit;


import java.util.List;

public class Talent {
    String talentName;

    List<String> operatorLevels;
    List<String> rarityURLS;
    List<String> potentialURLS;
    List<String> talentDescriptions;
    int length;

    public Talent(String name,List<String> level, List<String> rarityUrl, List<String> potentialUrl, List<String> talentDescription){
        this.talentName = name;
        this.operatorLevels = level;
        this.rarityURLS = rarityUrl;
        this.potentialURLS = potentialUrl;
        this.talentDescriptions = talentDescription;
        this.length = rarityUrl.size();
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < this.length; i++){
            result+= talentName + " " + operatorLevels.get(i) + " " + rarityURLS.get(i) + " " + potentialURLS.get(i) + "\n\t";
            result+= talentDescriptions.get(i) + "\n";
        }
        return result.trim();
    }

    public TalentBean getTalentBean(int index){
        TalentBean talentBean = new TalentBean();
        talentBean.setOperatorLevel(operatorLevels.get(index));
        talentBean.setPotentialURL(potentialURLS.get(index));
        talentBean.setRarityURL(potentialURLS.get(index));
        talentBean.setTalentDescription(talentDescriptions.get(index));
        talentBean.setTalentName(talentName);
        return talentBean;
    }

    public int getLength() {
        return length;
    }
}
