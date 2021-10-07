package Main.unit;

import Main.Manager.MaterialFactoryManager;

public class EliteMaterialBean {

    String fromIcon;
    String toIcon;

    MaterialFactoryManager materialFactoryManager;

    public String getFromIcon() {
        return fromIcon;
    }

    public String getToIcon() {
        return toIcon;
    }

    public MaterialFactoryManager getMaterialFactoryManager() {
        return materialFactoryManager;
    }

    public void setFromIcon(String fromIcon) {
        this.fromIcon = fromIcon;
    }

    public void setToIcon(String toIcon) {
        this.toIcon = toIcon;
    }

    public void setMaterialFactoryManager(MaterialFactoryManager materialFactoryManager) {
        this.materialFactoryManager = materialFactoryManager;
    }
}
