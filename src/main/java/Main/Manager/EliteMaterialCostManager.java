package Main.Manager;

import Main.unit.EliteMaterialBean;
import Main.unit.EliteMaterialCost;

import java.util.ArrayList;
import java.util.List;

public class EliteMaterialCostManager {

    List<EliteMaterialCost> eliteMaterialCostList = new ArrayList<>();

    public void add(EliteMaterialCost eliteMaterialCost){
        eliteMaterialCostList.add( eliteMaterialCost);
    }

    public String toString(){
        String result = "Elite Material Cost:\n";
        for(EliteMaterialCost each: eliteMaterialCostList)
            result+=each.toString() + "\n";
        return result;
    }

    public MaterialFactoryManager getTotalCount(){
        List<MaterialFactoryManager> materialFactoryManagerList = new ArrayList<>();
        for( EliteMaterialCost each: eliteMaterialCostList)
            materialFactoryManagerList.add(each.getMaterialFactoryManager());

        return MaterialFactoryManager.combineMaterialCost(materialFactoryManagerList);
    }

    public List<EliteMaterialBean> generateBeanList(){
        List<EliteMaterialBean> eliteMaterialBeanList = new ArrayList<>();
        for(EliteMaterialCost each: eliteMaterialCostList)
            eliteMaterialBeanList.add(each.getBean());
        return eliteMaterialBeanList;
    }


}
