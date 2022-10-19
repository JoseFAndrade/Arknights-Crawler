package Main;

import Main.database.Database;
import Main.skill.Skill;
import Main.skill.SkillMaterialCost;
import Main.skill.SkillUtilCrawler;
import Main.stats.ChangeableStats;
import Main.stats.Stats;
import Main.stats.TrustStats;
import Main.unit.Potentials;
import Main.unit.Talent;
import Main.unit.Unit;
import Main.util.Constants;
import Main.util.Util;
import Main.Manager.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Crawler.MaterialCostUtilCrawler;
public class Crawler {

    private static boolean tester = true;

    Crawler(){
        System.setProperty("webdriver.chrome.driver", "./RequiredExe/chromedriver.exe");
    }

    public UnitManager getAllUnits(){
        UnitManager unitManager = new UnitManager();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<String> notAllowed = new ArrayList<>(Arrays.asList("stormeye",
                                "pith", "touch","reserve-operator-caster",
                                "sharp","reserve-operator-logistics",
                                "reserve-operator-sniper",
                                "reserve-operator-melee"
        ));

        //List<String> unitUrls = getOperatorUrlList(driver);



        List<String> unitUrls = new ArrayList<>(Arrays.asList(//"https://gamepress.gg/arknights/operator/thrm-ex",
                                                             //"https://gamepress.gg/arknights/operator/12f",
                                                            //"https://gamepress.gg/arknights/operator/castle-3",
                                                         //   "https://gamepress.gg/arknights/operator/popukar",
                                                          //  "https://gamepress.gg/arknights/operator/shaw",
                                                        //"https://gamepress.gg/arknights/operator/orchid",
                                                            "https://gamepress.gg/arknights/operator/thorns"));



        for(String eachUrl: unitUrls){
            String[] split = eachUrl.split("/");
            String name = split[split.length-1].trim();

            if(notAllowed.contains(split[split.length-1].trim())){
                System.out.println("Unit was not allowed: "+ eachUrl);
            }
            else{
                if(Database.checkName(name) && !tester){
                    System.out.println("Name: " + name +" was skipped.");
                    continue;
                }

                System.out.println("Name not skipped: " + name);

                try{
                    Unit unit = getOperatorInfo(driver, eachUrl);
                    Database.addUnit(unit);
                    unitManager.addUnit(unit.getName(),unit);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        driver.quit();
        return unitManager;
    }

    /**
     * Returns a List of Operator Url's that the crawler will use in order to crawl for info
     * @param driver The web driver
     * @return list of string operator url's
     */
    public List<String> getOperatorUrlList(WebDriver driver ) {
        List<String> resultUrl = new ArrayList<String>();
        driver.get(Constants.operatorList);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement divOpList = driver.findElement(By.id("operators-list"));

        List<WebElement> opList = divOpList.findElements(By.className("operators-row"));
        for(WebElement e:opList) {
            String name = e.getAttribute("data-name");
            if (e.getAttribute("style").equals("display: none;")) {
                System.out.println( name );
            }
            else {
                WebElement operatorTitle = e.findElement(By.className("operator-title"));
                String operatorUrl = operatorTitle.findElement(By.tagName("a")).getAttribute("href");
                System.out.println(operatorUrl);
                resultUrl.add(operatorUrl);
            }
        }
        return resultUrl;
    }


    public Unit getOperatorInfo(WebDriver driver,String url ) {
        driver.get(url);
        //Unit Name
        WebElement pageTitle = driver.findElement(By.id("page-title"));
        String name = pageTitle.findElement(By.tagName("h1")).getText();

        //Unit Rarirty
        WebElement rarityCell = driver.findElement(By.className(Constants.opRarity));
        List<WebElement> imgSrcs = rarityCell.findElements(By.tagName("img"));
        int operatorRarity = imgSrcs.size();

        //Class type
        String classType = driver.findElement(By.className("profession-title")).getText();

        //Rest of the information

        SkillMaterialCost skill = MaterialCostUtilCrawler.obtainRegularSkillUpgrade(driver);
        SkillMasteryMaterialCostManager skillMastery = MaterialCostUtilCrawler.obtainMasterySkillUpgrade(driver);
        EliteMaterialCostManager eliteMaterialCostManager = MaterialCostUtilCrawler.obtainEliteUpgradeCost(driver);


        //checking if 6 star
        boolean ifSix = false;
        if(operatorRarity == 6)
            ifSix = true;

        //obtain an image of the unit
        //TODO we dont need to do this
        String unitImage = getUnitImageUrl(driver,operatorRarity);

        //todo go back and change the obtainAllSkillInformation and remove the ifSix boolean from it
        Unit unit = new Unit(name,operatorRarity,getPosition(driver),getAttackType(driver),classType,getTags(driver),getArchetype(driver),
                getTrait(driver),skillMastery,skill,eliteMaterialCostManager,obtainAllSkillInformation(ifSix,driver),obtainAllTalentTypes(driver),
                obtainStats(driver),getPotentials(driver),getTrustStats(driver), unitImage );

        System.out.println("The unit has been created");
        System.out.println("Unit is: "+ unit.getName());
        System.out.println(unit);
        return unit;
    }
    /*
    TODO: ADD THESE Things TO CONSTANTS
     */


    public String getPosition(WebDriver driver){
        WebElement positionCell = driver.findElement(By.className("position-cell"));
        String position = positionCell.findElement(By.className("text-content-cell")).getText();
        return position;
    }

    public String getAttackType(WebDriver driver){
        WebElement traitsCell = driver.findElement(By.className("traits-cell"));
        String attackType = traitsCell.findElement(By.className("text-content-cell")).getText();
        return attackType;
    }

    public String getUnitImageUrl(WebDriver driver, int rarity){
        //contains e1, e2, <any skin>,  images in that order
        List<WebElement> cells = driver.findElements(By.xpath(".//div[contains(@class,'operator-image')]"));
        WebElement divElement;
        System.err.println(rarity);
        try{
            if( rarity >= 4 )
                divElement = cells.get(2); //e2 image subtract 2 because we arent obtaining e0 image
            else
                divElement = cells.get(1); //e1 image
        }
        catch(Exception e){
            divElement = cells.get(cells.size()-1); //just grab the last one if it fails in special cases
        }

        WebElement imageElement = divElement.findElement(By.tagName("img"));
        System.err.println(imageElement.getAttribute("src"));
        return imageElement.getAttribute("src");
    }



    //todo combine this function with the archetype so it works better and doesn't repeat code
    //todo: change to xpath
    public List<String> getTags(WebDriver driver){
        //WebElement tagCell = driver.findElement(By.className("tag-cell"));
        List<WebElement> tagCells = driver.findElements(By.className("tag-cell"));
        WebElement tagCell = tagCells.get(0); //the first one is always the tags
        List<String> tags = new ArrayList<>();
        for(WebElement each: tagCell.findElements(By.className("tag-title"))){
            //System.out.println(each.getText());
            tags.add(each.getText());
        }

        return tags;
    }

    //todo make sure to change this function or combine it with the get tags
    //todo: change to xpath and update the constants too pls
    public List<String> getArchetype(WebDriver driver){
        try{
            WebElement archetypeTagCell = driver.findElement(By.xpath(".//h3[contains(text(),'Archetype')]/following-sibling::div"));
            List<String> tags = new ArrayList<>();
            for(WebElement each: archetypeTagCell.findElements(By.className("tag-title"))){
                tags.add(each.getText());
            }
            return tags;
        }
       catch (Exception e ){
            return null;
       }
    }

    public String getTrait(WebDriver driver){
        WebElement traitDescription = driver.findElement(By.xpath(".//h3[contains(text(),'Traits')]//following-sibling::div"));
        return traitDescription.getText();
    }

    //todo This is a very bad way to get the element. Make sure to edit this function before using it
    public String operatorDescription(WebDriver driver){
        WebElement traitDescription = driver.findElement(By.xpath("//*[@id=\"node-19141\"]/div/div[5]"));
        return traitDescription.getText();
    }

    public Potentials getPotentials(WebDriver driver){
        //contains what stats the unit will gain
        List<String> potentialTextList = new ArrayList<>();
        //contains the image of the potential
        //todo Can make a general potential image to only store 5 of them in the database instead of multiple
        List<String> potentialLevelUrlList = new ArrayList<>();

        WebElement potentialCell = driver.findElement(By.className("potential-cell"));
        for(WebElement each: potentialCell.findElements(By.className("potential-list"))){
            String potentialLevelUrl = each.findElement(By.tagName("img")).getAttribute("src");
            String text = each.getText().replace("\n"," ");
            potentialLevelUrlList.add(potentialLevelUrl);
            potentialTextList.add(text);
        }
        return new Potentials(potentialLevelUrlList,potentialTextList);
    }

    public TrustStats getTrustStats(WebDriver driver){
        List<String> trustArr = new ArrayList<>();
        WebElement trustCell = driver.findElement(By.className("trust-cell"));
        for(WebElement each: trustCell.findElements(By.xpath("//div[@class='potential-list trust-title']"))){
            String text = each.getText().replace("\n"," ");
            trustArr.add(text);
        }

        return new TrustStats(trustArr);
    }

    //todo: make sure to double check this code because it will not work when there are more than two talent-cells
    public TalentManager obtainAllTalentTypes(WebDriver driver){
        List<WebElement> talentCells = driver.findElements(By.className("talent-cell"));
        List<Talent> talentList = new ArrayList<Talent>();
        for(WebElement each: talentCells){
            Talent talent = talentList(each);
            talentList.add(talent);
        }

        return new TalentManager(talentList);
    }

    public Talent talentList(WebElement talentCell){
        String name = "";
        List<String> operatorLevelList = new ArrayList<>();
        List<String> rarityUrlList = new ArrayList<>();
        List<String> potentialUrlList = new ArrayList<>();
        List<String> talentDescriptionList = new ArrayList<>();
        List<String> talentArr = new ArrayList<>();
        for(WebElement each: talentCell.findElements(By.className("talent-child"))){

            String talentName = each.findElement(By.className("talent-title")).getText();
            String operatorLevel = each.findElement(By.className("operator-level")).getText();
            WebElement eliteImage = each.findElement(By.className("elite-level")).findElement(By.tagName("img"));
            WebElement potentialImage = each.findElement(By.className("potential-level")).findElement(By.tagName("img"));
            WebElement talentDescription = each.findElement(By.className("talent-description"));

            //todo: split this into its own seperate fucntion because we will need to download the images
            String eliteUrl = eliteImage.getAttribute("src");
            /*
            String[] eliteImageFullName = eliteUrl.split("/");
            String eliteImageName = eliteImageFullName[eliteImageFullName.length-1];
            */
            String potentialUrl = potentialImage.getAttribute("src");
            /*
            String[] potentialImageFullName = potentialUrl.split("/");
            String potentialImageName = potentialImageFullName[potentialImageFullName.length-1];
            */
            name = talentName;
            operatorLevelList.add(operatorLevel);
            rarityUrlList.add(eliteUrl);
            potentialUrlList.add(potentialUrl);
            talentDescriptionList.add(talentDescription.getText());

        }
        Talent talent = new Talent(name,operatorLevelList,rarityUrlList,potentialUrlList,talentDescriptionList);
        return talent;
    }

    /*
    //TODO MAKE SURE TO REMOVE THIS OUTDATED FUNCTION
    public List<String> talentList(WebDriver driver){
        List<String> talentArr = new ArrayList<>();
        WebElement talentCell = driver.findElement(By.className("talent-cell")); //todo: fix this to work with multiple
        for(WebElement each: talentCell.findElements(By.className("talent-child"))){

            String talentName = each.findElement(By.className("talent-title")).getText();
            String operatorLevel = each.findElement(By.className("operator-level")).getText();
            WebElement eliteImage = each.findElement(By.className("elite-level")).findElement(By.tagName("img"));
            WebElement potentialImage = each.findElement(By.className("potential-level")).findElement(By.tagName("img"));
            WebElement talentDescription = each.findElement(By.className("talent-description"));

            //todo: split this into its own seperate fucntion because we will need to download the images
            String eliteUrl = eliteImage.getAttribute("src");
            /*
            String[] eliteImageFullName = eliteUrl.split("/");
            String eliteImageName = eliteImageFullName[eliteImageFullName.length-1];

            String potentialUrl = potentialImage.getAttribute("src");
            /*
            String[] potentialImageFullName = potentialUrl.split("/");
            String potentialImageName = potentialImageFullName[potentialImageFullName.length-1];


            System.out.println(talentName + operatorLevel + eliteUrl + potentialUrl);
            System.out.println(talentDescription.getText());
        }
        return talentArr;
    }*/


    public Stats obtainStats(WebDriver driver) {

        //e0
        ChangeableStats E0L1 = createChangeableStatsFromPage("E0L1",driver);
        changeToMaxLevel(driver);
        ChangeableStats E0LM = createChangeableStatsFromPage("E0MaxLevel",driver);
        changeToMinLevel(driver);

        //e1
        ChangeableStats E1L1 = null;
        ChangeableStats E1LM = null;

        try{
            pressEliteOneStatButton(driver);
            E1L1 = createChangeableStatsFromPage("E1L1",driver);
            changeToMaxLevel(driver);
            E1LM = createChangeableStatsFromPage("E1MaxLevel",driver);
        }
        catch (Exception e){
            System.err.println("This unit does not have E1.");
        }
        changeToMinLevel(driver);

        //e2
        ChangeableStats E2L1 = null;
        ChangeableStats E2LM = null;

        try{
            pressEliteTwoStatButton(driver);
            E2L1 = createChangeableStatsFromPage("E2L1",driver);
            changeToMaxLevel(driver);
            E2LM = createChangeableStatsFromPage("E2MaxLevel",driver);
        }
        catch (Exception e){
            System.err.println("This unit does not have E2.");
        }

        //redeploy time, attack interval will not change so it is a bit harder to obtain these two
        WebElement otherStatCell = driver.findElement(By.className("other-stat-cell"));

        //second element is redeploy time; 5th element is attack interval
        List<WebElement> secondaryStats = otherStatCell.findElements(By.className("other-stat-value-cell"));

        WebElement redeployTime = secondaryStats.get(1).findElement(By.className("effect-description"));
        WebElement attackInterval = secondaryStats.get(4).findElement(By.className("effect-description"));

        Stats stats = new Stats(redeployTime.getAttribute("textContent"), attackInterval.getAttribute("textContent"));
        stats.setChangeableStats("E0L1", E0L1);
        stats.setChangeableStats("E0LM", E0LM);
        stats.setChangeableStats("E1L1", E1L1);
        stats.setChangeableStats("E1LM", E1LM);
        stats.setChangeableStats("E2L1", E2L1);
        stats.setChangeableStats("E2LM", E2LM);
        return stats;
    }

    public ChangeableStats createChangeableStatsFromPage(String level,WebDriver driver){
        //these are ones that will change depending on level
        WebElement hp = driver.findElement(By.id(Constants.statHP));
        WebElement atk = driver.findElement(By.id(Constants.statAttack));
        WebElement def = driver.findElement(By.id(Constants.statDef));
        WebElement artRes = driver.findElement(By.id(Constants.artsResist));
        WebElement dpCost = driver.findElement(By.id(Constants.operatorCost));
        WebElement blockCount = driver.findElement(By.id(Constants.operatorBlock));

        ChangeableStats changeableStats = new ChangeableStats(level,hp.getText(),atk.getText(),def.getText(),
                artRes.getText(),dpCost.getText(),blockCount.getText()  );
        return changeableStats;
    }

    public void changeToMaxLevel(WebDriver driver){
        WebElement slider = driver.findElement(By.id("myRange"));
        WebElement to = driver.findElement(By.xpath(".//i[@class='fa fa-arrow-right']"));
        Actions move = new Actions(driver);
        Action action = (Action) move.dragAndDrop(slider,to).build();

        action.perform();
    }

    public void changeToMinLevel(WebDriver driver){
        WebElement slider = driver.findElement(By.id("myRange"));

        WebElement to = driver.findElement(By.xpath(".//i[@class='fa fa-arrow-left']"));
        Actions move = new Actions(driver);
        Action action = (Action) move.dragAndDrop(slider,to).build();

        action.perform();
    }

    public void pressEliteOneStatButton(WebDriver driver) {
        WebElement eliteOne = driver.findElement(By.xpath("//button[@data-tab='e1']"));
        eliteOne.click();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pressEliteTwoStatButton(WebDriver driver){
        WebElement eliteTwo = driver.findElement(By.xpath("//button[@data-tab='e2']"));
        eliteTwo.click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public SkillManager obtainAllSkillInformation(boolean ifSix, WebDriver driver){
        SkillManager skillManager = new SkillManager();
        if(driver.findElements(By.id(Constants.skillOneID)).size() == 0)
            return null;

        WebElement skillOne = driver.findElement(By.id(Constants.skillOneID));
        Skill firstSkill = obtainSkillInformation(skillOne);
        skillManager.add(firstSkill);

        if(driver.findElements(By.id(Constants.skillTwoID)).size() != 0)
        {
            WebElement skillTwo = driver.findElement(By.id(Constants.skillTwoID));
            Skill secondSkill = obtainSkillInformation(skillTwo);
            skillManager.add(secondSkill);

        }
        if(ifSix){
            WebElement skillThree = driver.findElement(By.id(Constants.skillThreeID));
            Skill thirdSkill = obtainSkillInformation(skillThree);
            skillManager.add(thirdSkill);
            //System.out.println(thirdSkill);
        }
        return skillManager;
    }

    public Skill obtainSkillInformation(WebElement driver){
        List<String> spCostInfo = SkillUtilCrawler.getSpCostInfo(driver);
        List<String> initialSpInfo = SkillUtilCrawler.getInitialSpInfo(driver);
        List<String> skillUtilizationInfo = SkillUtilCrawler.getUtilizationTypeInfo(driver);
        List<String> skillEffectInfo = SkillUtilCrawler.getSkillEffectInfo(driver);
        String spChargeType = SkillUtilCrawler.getSpChargeType(driver);
        String spSkillActivation = SkillUtilCrawler.getSkillActivationType(driver);
        int[][] skillTiles = null;
        try {
            skillTiles = Util.getRange(driver);
        }
        catch (Exception e){
            skillTiles = null;
        }
        String name = SkillUtilCrawler.getSkillName(driver);

        return new Skill(name,spCostInfo,initialSpInfo,skillUtilizationInfo,skillEffectInfo,spChargeType,spSkillActivation,
                skillTiles);
    }


   /*
    //where the webElement coming in will be the div of the specific Main.skill.Skill #
    public List<String> getUtilizationTypeInfo(WebElement driver){
        List<String> utilization = new ArrayList<>();
        //todo make sure to add this to the constants
        List<WebElement> skillChargesPerLevel = driver.findElements(By.className("skill-description-rem"));
        if(skillChargesPerLevel.isEmpty()){
            return getSkillDurationInfo(driver);
        }
        else{
            return getChargesInfo(skillChargesPerLevel);
        }
    }
    */

    public void obtainEliteOneAttackRange(WebDriver driver){
        WebElement div = driver.findElement(By.id("image-tab-2"));
        Util.getRange(div);
    }

    public void obtainEliteTwoAttackRange(WebDriver driver){
        WebElement div = driver.findElement(By.id("image-tab-3"));
        Util.getRange(div);
    }
}
