package Main.Manager;

import Main.util.MaterialFactory;

import java.util.*;

public class MaterialFactoryManager {
    //private List<MaterialFactory> materialList;
    private Map<String,MaterialFactory> materialMap = new HashMap<>();

    public MaterialFactoryManager(MaterialFactory... materialFactories)  {
        for(MaterialFactory each: materialFactories){

            if(materialMap.containsKey(each.getMaterialName())){
                try{
                    materialMap.replace(each.getMaterialName(), each.add(materialMap.get(each.getMaterialName())));
                }
                catch (Exception e){
                    System.out.println(".... ");
                }
            }
            else{
                materialMap.put(each.getMaterialName(), each);
            }
        }
    }

    public MaterialFactoryManager(List<MaterialFactory> materialFactories){
        for(MaterialFactory each: materialFactories){

            if(materialMap.containsKey(each.getMaterialName())){
                try{
                    materialMap.replace(each.getMaterialName(), each.add(materialMap.get(each.getMaterialName())));
                }
                catch (Exception e){
                    System.out.println(".... ");
                }
            }
            else{
                materialMap.put(each.getMaterialName(), each);
            }
        }
    }

    public Collection<MaterialFactory> getMaterials(){
        return materialMap.values();
    }


    public String toString(){
        String result = "";
        for(MaterialFactory material: materialMap.values()){
            result += material.toString() + "\n";
        }
        return result;
    }

    static private HashMap<String,MaterialFactory> combineMaterialMap(MaterialFactoryManager m1, MaterialFactoryManager m2){
        Map<String,MaterialFactory> result = new HashMap<>();
        result.putAll(m1.materialMap);
        for(MaterialFactory each: m2.materialMap.values()){
            String name = each.getMaterialName();
            if(result.containsKey(name)){
                try {
                    MaterialFactory combined = each.add(result.get(name));
                    //now replace it
                    result.replace(name, combined);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                result.put(name, each);
            }
        }
        //shallow copy is fine here
        return new HashMap<String,MaterialFactory>(result);
    }

    static public MaterialFactoryManager combineMaterialCost(MaterialFactoryManager... materialFactoryManagers){
        MaterialFactoryManager materialFactoryManager = new MaterialFactoryManager();

        for (MaterialFactoryManager each: materialFactoryManagers){
            materialFactoryManager.materialMap = combineMaterialMap(materialFactoryManager,each);
        }
        return materialFactoryManager;
    }

    static public MaterialFactoryManager combineMaterialCost(List<MaterialFactoryManager> materialFactoryManagers){
        MaterialFactoryManager materialFactoryManager = new MaterialFactoryManager();

        for (MaterialFactoryManager each: materialFactoryManagers){
            materialFactoryManager.materialMap = combineMaterialMap(materialFactoryManager,each);
        }
        return materialFactoryManager;
    }

    static public void sort(MaterialFactoryManager materialFactoryManager){
        List<MaterialFactory> materialFactoryList = new ArrayList<>(materialFactoryManager.materialMap.values());
        Collections.sort(materialFactoryList);
        for(MaterialFactory each: materialFactoryList)
            System.out.println(each);
    }


    /*
    static public Comparable<MaterialFactoryManager> getComparator(){
        return new Comparator<MaterialFactoryManager>() {
            @Override int compare()
        }

    }*/
}
