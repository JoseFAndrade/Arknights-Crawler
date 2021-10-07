package Main.unit;

import java.util.List;

public class Potentials {
    private List<String> imagesURL = null;
    private List<String> descriptions = null;

    public Potentials(List<String> imagesURL, List<String> descriptions){
        this.imagesURL = imagesURL;
        this.descriptions = descriptions;
    }

    public String getDescription(int index){
        return descriptions.get(index);
    }

    public String getImageURl(int index){
        return imagesURL.get(index);
    }

    public String toString(){
        String result = "Potential List:\n";
        for(int i = 0;  i < imagesURL.size(); i++){
            result += String.format("Potential level: %s. Description: %s\n",imagesURL.get(i),descriptions.get(i));
        }
        return result.trim();
    }

    public PotentialBean generatePotentialBean(){
        PotentialBean potentialBean = new PotentialBean();
        potentialBean.setDescriptions(descriptions);
        potentialBean.setImagesURL(imagesURL);

        return potentialBean;
    }

}
