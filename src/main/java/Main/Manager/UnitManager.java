package Main.Manager;

import Main.unit.Unit;

import java.util.*;

public class UnitManager {

    List<String> unitOrder = new ArrayList<>();
    Map<String, Unit> unitMap = new HashMap<String,Unit>();

    //this will replace any previous value if there was one
    public void addUnit(String name, Unit unit){
        unitMap.put(name,unit);
    }

    public boolean ifExist(String name){
        return unitMap.containsKey(name);
    }

    public Set<String> getUnits(){
        return unitMap.keySet();
    }

    public Collection<Unit> getActualUnits(){return unitMap.values();}

}
