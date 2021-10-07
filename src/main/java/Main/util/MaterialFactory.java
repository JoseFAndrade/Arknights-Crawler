package Main.util;

public class MaterialFactory implements Comparable<MaterialFactory>{
    private String materialName;
    private int count;
    private String imageURL;
    private String backgroundURL;
    private int rarity;

    public MaterialFactory(String materialName, int count, String imageURL, String backgroundURL) {
        this.materialName = materialName;
        this.count = count;
        this.imageURL = imageURL;
        this.backgroundURL = backgroundURL;
        String[] backgroundList = backgroundURL.split("/");
        String nameOfPicture = backgroundList[backgroundList.length-1];
        String rarityString = nameOfPicture.split("-")[1].split("_")[0];
        this.rarity = Integer.parseInt(rarityString);
    }

    private MaterialFactory(String materialName, String imageURL, String backgroundURL){
        this.materialName = materialName;
        this.imageURL = imageURL;
        this.backgroundURL = backgroundURL;
    }

    public MaterialFactory add(MaterialFactory materialFactory) throws Exception {
        if( !this.materialName.equals(materialFactory.materialName))
            throw new Exception("These items don't have the same name.");
        int newCount = this.count + materialFactory.count;

        MaterialFactory result = new MaterialFactory(materialFactory.materialName, newCount,materialFactory.imageURL,materialFactory.backgroundURL);
        return result;
    }

    public MaterialFactory subtract(MaterialFactory materialFactory) throws Exception {
        if( !this.materialName.equals(materialFactory.materialName))
            throw new Exception("These items don't have the same name.");
        MaterialFactory result = new MaterialFactory(materialFactory.materialName,materialFactory.imageURL,materialFactory.backgroundURL);
        int newCount = this.count + materialFactory.count;
        result.count = newCount;
        return result;
    }

    public String toString(){
        return String.format("Material Name: %s. Count: %s.",materialName,count);
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getCount() {
        return count;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getBackgroundURL() {
        return backgroundURL;
    }

    public int getRarity() {
        return rarity;
    }

    @Override
    public int compareTo(MaterialFactory materialFactory){
        return this.rarity - materialFactory.rarity;
    }
}
