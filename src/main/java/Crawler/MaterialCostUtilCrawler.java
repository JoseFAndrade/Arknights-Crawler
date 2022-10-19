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

    /**
     * This function seems to return the Elite Upgrade Cost for the unit. If not found then it will not do it.
     * @param driver
     * @return
     */
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

        //seems to run for the amount of elite that it goes up to
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

    /**
     * This function will obtain the SKill Material Cost for the 1 - 7 Skills. The Mastery 1 - 3 will be done in another function.
     * @param driver The WebDriver that is currently within the unit page.
     * @return
     */
    static public SkillMaterialCost obtainRegularSkillUpgrade(WebDriver driver){
        //where the info will be stored
        SkillMaterialCost skillMaterialCost = new SkillMaterialCost();
        WebElement upgradeCostDiv = null;
        try{
            //upgradeCostTable = driver.findElement(By.className("upgrade-cost-table"));
            upgradeCostDiv = driver.findElement(By.xpath(".//h2[contains(text(), 'Skill Upgrade Costs')]/following-sibling::div"));
        }
        catch (Exception e){
            System.err.println("Unit does not seem to have an upgrade skill table.");
            return null;
        }

        //Selecting all of the tr elements within the Skill Upgrade Costs First Table
        List<WebElement> tableRows = upgradeCostDiv.findElements(By.xpath(".//*[contains(@class,'table-1')]/tbody/tr"));

        for(int i = 1; i < tableRows.size(); i++){
            List<WebElement> columns = tableRows.get(i).findElements(By.xpath(".//td"));
            //we are going to have 3 items in that column
            //first one is the level base to increment
            String levelUpgrade = columns.get(0).getAttribute("textContent").trim();
            //second one is the Level requisite and the rarity
            String levelRequisite = columns.get(1).getAttribute("textContent").trim();
            String levelURl = columns.get(1).findElement(By.tagName("img")).getAttribute("src");
            //third one is the div that contains the Materials so im handling this in another function
            List<WebElement> materialElements = columns.get(2).findElements(By.className("material-cell"));
            MaterialFactoryManager materialFactoryManager = sortThroughMaterials(materialElements);

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

    /**
     * This function is similar to SkillMaterialCost except this function is created for Mastery Material Cost. Some of
     * the columns change here and we have to accommodate that here.
     * @param driver The driver is just the driver for the unit page.
     * @return Will return a SkillMasteryMaterialCostManager with all of the information for the skills.
     */
    static public SkillMasteryMaterialCostManager obtainMasterySkillUpgrade(WebDriver driver){
        //this function will go through the second table and obtain information out of it
        try{
            //the table is called table-2 so we can easily be able to tell if it exists or not. We are also able to just extract all the tr info in here
            SkillMasteryMaterialCostManager skillMasteryMaterialCostManager = new SkillMasteryMaterialCostManager();
            SkillMasteryMaterialCost skill = null;

            List<WebElement> tableRows = driver.findElements(By.xpath(".//*[contains(@class,'table-2')]/tbody/tr"));

            for(int i = 1; i < tableRows.size(); i++){
                List<WebElement> columns = tableRows.get(i).findElements(By.tagName("td"));
                //depending on the column size these indexes will change -> default size will be 3 column size
                int levelIndex = 0;
                int requisitesIndex = 1;
                int materialsIndex = 2;

                if(columns.size() == 4) //means that we moved on to the next skill so add in the previous skill to the Manager
                {
                    //to prevent the first skill to be added
                    if(skill != null)
                        skillMasteryMaterialCostManager.addSkillMastery(skill);
                    skill = new SkillMasteryMaterialCost();
                    levelIndex++;
                    requisitesIndex++;
                    materialsIndex++;
                }

                //getting icon url
                String mIconUrl = columns.get(levelIndex).findElement(By.tagName("img")).getAttribute("src").trim();

                //within the requisite column
                WebElement requisiteElement = columns.get(requisitesIndex);
                String requisiteLevel = requisiteElement.findElement(By.className("level-cell")).getText().trim();
                String requisiteRarityUrl = requisiteElement.findElement(By.className("level-image-cell"))
                        .findElement(By.tagName("img")).getAttribute("src").trim();
                String requisiteUpgradeTime = requisiteElement.findElement(By.className("time-cell")).getText().trim();

                //creating a materialFactoryManager
                List<WebElement> materialElements = columns.get(materialsIndex).findElements(By.xpath(".//div[contains(@class,'material-cell')]"));
                MaterialFactoryManager materialFactoryManager = sortThroughMaterials( materialElements );
                skill.addSkillCost(mIconUrl,requisiteLevel,requisiteRarityUrl,requisiteUpgradeTime,materialFactoryManager);
            }
            //todo fix this code so it isnt this messy
            skillMasteryMaterialCostManager.addSkillMastery(skill);
            return skillMasteryMaterialCostManager;
        }
        catch (Exception e){
            System.err.println("This unit does not seem to have skill mastery table.");
            e.printStackTrace();
            return new SkillMasteryMaterialCostManager();
        }
    }

    //this functions get given a td tag of hyperlinks (a) tags
    //within each a tag there is a div tag that contains the information that we need from the materials
    //todo have this return a materialFactoryManager

    /**
     * This function takes in a td tag in that can contain multiple ahrefs of materials. This function goes through the
     * a tags and then breaks them up into pieces that can be used for the code. These pieces include: name of material,
     * background image, image URl, and finally the quantity of the material.
     * @param materialElements A List of Div Web Elements in the form of:
     *                        <div class="material-cell" data-item="Skill Summary - 3" data-link="/arknights/item/skill-summary-3" style="background: url(/arknights/sites/arknights/files/2019-11/item-4_0.png)">
     *                                 <img class="item-image" src="/arknights/sites/arknights/files/game-images/items/MTL_SKILL3.png">
     *                                  <span class="material-quantity">x8</span>
     *                        </div>
     * @return A Material Factory Manager that contains all of the information.
     */
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
