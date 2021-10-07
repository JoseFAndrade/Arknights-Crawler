package Main;

import Main.Manager.UnitManager;
import Main.database.Database;
import Main.unit.Unit;

public class Main {

    public static void main (String[] args) throws InterruptedException {
        //Main.Crawler.testGoogleSearch();
        Crawler crawler = new Crawler();
        //crawler.getOperatorInfo("https://gamepress.gg/arknights/operator/thorns#status");
        //crawler.getOperatorInfo("https://gamepress.gg/arknights/operator/eyjafjalla");
        UnitManager unitManager = crawler.getAllUnits();
        /*
        for(Unit each: unitManager.getActualUnits())
            Database.addUnit(each);*/
        //System.out.print(unitManager);
        //Database.getMaterials();
        //Database.addMaterial("Test",1,"testing");
    }
}
