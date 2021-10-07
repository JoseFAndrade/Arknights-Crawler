package Crawler;

import Main.skill.SkillMasteryMaterialCost;
import Main.skill.SkillMaterialCost;
import Main.unit.EliteMaterialCost;
import Main.util.MaterialFactory;
import Main.Manager.EliteMaterialCostManager;
import Main.Manager.MaterialFactoryManager;
import Main.Manager.SkillMasteryMaterialCostManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class MaterialCostUtilCrawler {

    static public EliteMaterialCostManager obtainEliteUpgradeCost(WebDriver driver){
        WebElement eliteUpgradeTable = null;
        try{
            eliteUpgradeTable = driver.findElement(By.className("table-3"));
        }
        catch (Exception e){
            System.err.println("Unit seems to not have Elite Upgrade Cost Table.");
            return null;
        }
        EliteMaterialCostManager eliteMaterialCostManager = new EliteMaterialCostManager();

        List<WebElement> tableRows = eliteUpgradeTable.findElements(By.tagName("tr"));
        for(int i = 1; i < tableRows.size(); i++){
            List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
            //first column = Level | second column = Materials
            List<WebElement> eliteLevelUrl = columns.get(0).findElements(By.tagName("img"));
            List<WebElement> materials = columns.get(1).findElements(By.className("material-cell"));
            MaterialFactoryManager materialFactoryManager = sortThroughMaterials(materials);

            String fromIcon = eliteLevelUrl.get(0).getAttribute("Src").trim();
            String toIcon = eliteLevelUrl.get(1).getAttribute("Src").trim();

            /*
            System.out.println(
              String.format("From: %s . To: %s . ",eliteLevelUrl.get(0).getAttribute("Src"), eliteLevelUrl.get(1).getAttribute("Src"))
            );
               */
            EliteMaterialCost eliteMaterialCost = new EliteMaterialCost(fromIcon,toIcon, materialFactoryManager);
            eliteMaterialCostManager.add(eliteMaterialCost);
        }

        return eliteMaterialCostManager;
    }

    //todo create the skillMaterialCost class in here at some point
    static public SkillMaterialCost obtainRegularSkillUpgrade(WebDriver driver){
        //where the info will be stored
        SkillMaterialCost skillMaterialCost = new SkillMaterialCost();
        //this function will go through the first table and obtain information out of it
        WebElement upgradeCostDiv = null;
        try{
            //upgradeCostTable = driver.findElement(By.className("upgrade-cost-table"));
            upgradeCostDiv = driver.findElement(By.xpath(".//h2[contains(text(), 'Skill Upgrade Costs')]/following-sibling::div"));
        }
        catch (Exception e){
            System.err.println("Unit does not seem to have an upgrade skill table.");
            return null;
        }
        List<WebElement> tables = upgradeCostDiv.findElements(By.className("table-1"));
        List<WebElement> tableRows = tables.get(0).findElements(By.xpath(".//tr"));
        //we can ignore the first element because those are the headers

        for(int i = 1; i < tableRows.size(); i++){
            List<WebElement> columns = tableRows.get(i).findElements(By.xpath(".//td"));
            //we are going to have 3 items in that column
            //first one is the level base to increasement
            String levelUpgrade = columns.get(0).getAttribute("textContent").trim();
            //second one is the Level requisite and the rarity
            String levelRequisite = columns.get(1).getAttribute("textContent").trim();
            String levelURl = columns.get(1).findElement(By.tagName("img")).getAttribute("src");
            //third one is the div that contains the element so im handling this in another function
            List<WebElement> materialDivList = columns.get(2).findElements(By.className("material-cell"));
            MaterialFactoryManager materialFactoryManager = sortThroughMaterials(materialDivList);
            skillMaterialCost.addSkillCost(levelUpgrade,levelRequisite,levelURl,materialFactoryManager);
        }
        return skillMaterialCost;
    }


    //todo make sure that this function is not hard coded in the future
    /*
   int rowSpan = -1;
        //we can ignore the first element because those are the headers
        for(int i = 1; i < tableRows.size(); i++){
            List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
            if(rowSpan == -1){ //we know it has not be set
                rowSpan = Integer.getInteger(columns.get(0).getAttribute("rowspan"));
            }
        }
     */

    static public SkillMasteryMaterialCostManager obtainMasterySkillUpgrade(WebDriver driver){
        //this function will go through the second table and obtain information out of it
        List<WebElement> tableRows = null;
        try{
            WebElement upgradeCostDiv = driver.findElement(By.xpath(".//h2[contains(text(), 'Skill Upgrade Costs')]/following-sibling::div"));
            //the table is called table-2 so we can easily be able to tell if it exists or not. We are also able to just extract all the tr info in here
            tableRows = upgradeCostDiv.findElement(By.className("table-2")).findElements(By.xpath(".//tr"));
        }
        catch (Exception e){
            System.err.println("This unit does not seem to have skill mastery table.");
            return new SkillMasteryMaterialCostManager();
        }
        SkillMasteryMaterialCostManager skillMasteryMaterialCostManager = new SkillMasteryMaterialCostManager();
        //we can ignore the first element because those are the headers
        SkillMasteryMaterialCost skill = null;
        for(int i = 1; i < tableRows.size(); i++){
            List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
            int levelIndex = -1;
            int requisitesIndex = -1;
            int materialsIndex = -1;
            if(columns.size() == 4) //means that we moved on to the next skill so add in the previous skill to the Manager
            {
                if(skill != null)
                    skillMasteryMaterialCostManager.addSkillMastery(skill);
                skill = new SkillMasteryMaterialCost();
                levelIndex = 1;
                requisitesIndex = 2;
                materialsIndex = 3;
            }
            else {
                levelIndex = 0;
                requisitesIndex = 1;
                materialsIndex = 2;
            }

            String mIconUrl = columns.get(levelIndex).findElement(By.tagName("img")).getAttribute("src").trim();
            WebElement requisiteElement = columns.get(requisitesIndex);
            String requisiteLevel = requisiteElement.findElement(By.className("level-cell")).getText().trim();
            String requisiteRarityUrl = requisiteElement.findElement(By.className("level-image-cell"))
                    .findElement(By.tagName("img")).getAttribute("src").trim();
            String requisiteUpgradeTime = requisiteElement.findElement(By.className("time-cell")).getText().trim();

            MaterialFactoryManager materialFactoryManager = sortThroughMaterials(columns.get(materialsIndex).findElements(By.className("material-cell")));
            skill.addSkillCost(mIconUrl,requisiteLevel,requisiteRarityUrl,requisiteUpgradeTime,materialFactoryManager);
        }
        //todo fix this code so it isnt this messy
        skillMasteryMaterialCostManager.addSkillMastery(skill);
        return skillMasteryMaterialCostManager;
    }

    //this functions get given a td tag  of a lot of hyperlink (a) tags
    //within each a tag there is a div tag that contains the information that we need from the materials
    //todo have this return a materialFactoryManager
    static public MaterialFactoryManager sortThroughMaterials(List<WebElement> materialElements){
        List<MaterialFactory> materialList = new ArrayList<>();

        for(WebElement each: materialElements)
        {
            String nameOfItem = each.getAttribute("data-item").trim();
            String backgroundURl = each.getCssValue("background-image").trim().split("\"")[1];
            String imageURL = each.findElement(By.tagName("img")).getAttribute("src");
            String quantityString = each.findElement(By.tagName("span")).getAttribute("textContent").trim();
            int quantity = Integer.parseInt(quantityString.substring(1));
            materialList.add(new MaterialFactory(nameOfItem,quantity,imageURL,backgroundURl));
        }

        return new MaterialFactoryManager(materialList);


    }

    //updated function

    /*
    static public void sortThroughMaterials(List<WebElement> materialElements){
        for(WebElement each: materialElements)
        {
            String nameOfItem = each.getAttribute("data-item").trim();
            String backgroundURl = each.getCssValue("background-image").trim().split("\"")[1];
            String imageURL = each.findElement(By.tagName("img")).getAttribute("src");
            String quantity = each.findElement(By.tagName("span")).getAttribute("textContent").trim();
            String result = String.format("Name: %s. BackgroundUrl: %s . ImageUrl: %s . Quantity: %s.",
                    nameOfItem,backgroundURl,imageURL,quantity);
            System.out.println(result);
        }
    }
    */

    /*
         div for skill costs |  class = upgrade-cost-table
            first table = Level 1 - 7 upgrade costs
                tr
                    td - Level of the skill upgrading from and to what u are getting
                    td - requisite where it contains a div with txt and an img source with the img
                    td - the item which is within a div tag
                        this div tag is called a material cell
                        data-item property contains the name
                            within this div tag is the img with the url
                            a span tag with the quantity, class = material-quantity <x5>
                tr
            second table = m1 - m3 or level 8 - 10
                -follows pretty much the same order except that there is another td for the number of the skill
                and there is also no text within the second td only an image
                -requisuites contains an additional  item which is the time it takes so we will also need to take care
                of this
            table 3 = total costs //we might not even need this tbh


       How to keep this all in a class?
            - maybe the objects can be kept within a dictionary of sorts where name = key and value = int
            - this will allow us to easily be able to count the number of items in there
            - this will also be nice if we are able to prob have some form of class for every item
                this is to keep some tier of rarity to the items the class wouldn't be anything complex
                can create some form of factory function where we give the object a name, rarity, count and it gets
                created.
                    todo: this is a very good idea

     */
}
