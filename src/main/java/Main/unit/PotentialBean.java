package Main.unit;

import java.util.List;

public class PotentialBean {
    private List<String> imagesURL = null;
    private List<String> descriptions = null;

    public List<String> getImagesURL() {
        return imagesURL;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setImagesURL(List<String> imagesURL) {
        this.imagesURL = imagesURL;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }

    public int size(){
        return imagesURL.size();
    }
}
