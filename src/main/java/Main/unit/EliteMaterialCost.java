package Main.unit;

import Main.util.MaterialFactory;
import Main.Manager.MaterialFactoryManager;

import java.util.List;

public class EliteMaterialCost {

    //where these are urls
    String fromIcon;
    String toIcon;

    MaterialFactoryManager materialFactoryManager;

    public EliteMaterialCost(String fromIcon, String toIcon, List<MaterialFactory> materialFactoryList) {
        this.fromIcon = fromIcon;
        this.toIcon = toIcon;
        this.materialFactoryManager = new MaterialFactoryManager(materialFactoryList);
    }

    public EliteMaterialCost(String fromIcon, String toIcon, MaterialFactoryManager materialFactoryManager) {
        this.fromIcon = fromIcon;
        this.toIcon = toIcon;
        this.materialFactoryManager = materialFactoryManager;
    }

    public String toString(){
        return String.format("From: %s . To: %s . \nMaterials: \n%s",fromIcon,toIcon,materialFactoryManager.toString());
    }

    public MaterialFactoryManager getMaterialFactoryManager() {
        return materialFactoryManager;
    }

    public EliteMaterialBean getBean(){
        EliteMaterialBean eliteMaterialBean = new EliteMaterialBean();
        eliteMaterialBean.setFromIcon(fromIcon);
        eliteMaterialBean.setToIcon(toIcon);
        eliteMaterialBean.setMaterialFactoryManager(materialFactoryManager);
        return eliteMaterialBean;
    }
}
