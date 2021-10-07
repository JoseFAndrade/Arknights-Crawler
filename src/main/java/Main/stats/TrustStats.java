package Main.stats;

import java.util.List;

public class TrustStats {

    List<String> statList = null;

    public TrustStats(List<String> statList) {
        this.statList = statList;
    }

    public int length(){
        return statList.size();
    }

    public String getTrustStat(int index){
        return statList.get(index);
    }

    public List<String> getTrustStats(){
        return statList;
    }

    public String toString(){
        String result = "Trust Extra Status:\n";
        for(String each:statList)
            result += String.format("\t%s",each);
        return result;
    }
}
