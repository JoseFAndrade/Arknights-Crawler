package Main.skill;

import Main.util.Constants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SkillUtilCrawler {

    /*
    //todo start combining the skill functions here
    public void obtainSkillInformation(WebElement driver){
        this.spCostInfo = getSpCostInfo(driver);
        this.initialSpInfo = getInitialSpInfo(driver);
        this.skillUtilizationInfo = getUtilizationTypeInfo(driver);
        this.skillEffectInfo = getSkillEffectInfo(driver);
        this.spChargeType = getSpChargeType(driver);
        this.spSkillActivation = getSkillActivationType(driver);
        try {
            Main.util.Util.getRange(driver);
        }
        catch (Exception e){
            System.out.println("There is no range for this skill");
        }

        WebElement skillname = driver.findElement(By.className(Main.util.Constants.skillTitleCell));
        this.name = skillname.getAttribute("textContent").trim();
    }*/

    static public String getSkillName(WebElement driver){
        WebElement skillName = driver.findElement(By.className(Constants.skillTitleCell));
        return skillName.getAttribute("textContent").trim();
    }


    static public List<String> getSpCostInfo(WebDriver driver){
        List<String> spCostInfoList = new ArrayList<>();
        WebElement spCost = driver.findElement(By.className(Constants.spCost));
        List<WebElement> effectDescriptions = spCost.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            System.out.println(each.getAttribute("innerHTML"));
            spCostInfoList.add(each.getAttribute("innerHTML").trim());
        }
        return spCostInfoList;
    }

    static public List<String> getSpCostInfo(WebElement driver){
        List<String> spCostInfoList = new ArrayList<>();
        WebElement spCost = driver.findElement(By.className(Constants.spCost));
        List<WebElement> effectDescriptions = spCost.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            //System.out.println(each.getAttribute("innerHTML"));
            spCostInfoList.add(each.getAttribute("innerHTML").trim());
        }
        return spCostInfoList;
    }

    static public List<String> getInitialSpInfo(WebElement driver){
        List<String> initialSpInfoList = new ArrayList<>();
        WebElement initialSp = driver.findElement(By.className(Constants.initialSp));
        List<WebElement> effectDescriptions = initialSp.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            //System.out.println(each.getAttribute("innerHTML"));
            initialSpInfoList.add(each.getAttribute("innerHTML").trim());
        }
        return initialSpInfoList;
    }

    static public List<String> getInitialSpInfo(WebDriver driver){
        List<String> initialSpInfoList = new ArrayList<>();
        WebElement initialSp = driver.findElement(By.className(Constants.initialSp));
        List<WebElement> effectDescriptions = initialSp.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            //System.out.println(each.getAttribute("innerHTML"));
            initialSpInfoList.add(each.getAttribute("innerHTML").trim());
        }
        return initialSpInfoList;
    }

    //where the webElement coming in will be the div of the specific Main.skill.Skill #
    static public List<String> getUtilizationTypeInfo(WebElement driver){
        //List<String> utilization = new ArrayList<>();
        //todo make sure to add this to the constants
        WebElement skillUtilizationWebElement = driver.findElement(By.className("skill-duration"));
        List<WebElement> skillChargesPerLevel = skillUtilizationWebElement.findElements(By.className("skill-description-rem"));
        if(skillChargesPerLevel.isEmpty()){
            return getSkillDurationInfo(driver);
        }
        else{
            return getChargesInfo(skillChargesPerLevel);
        }
    }

    static public List<String> getChargesInfo(List<WebElement> webElements){
        List<String> skillCharges = new ArrayList<>();
        for(WebElement each: webElements){
            //why getAttribute over getText?
            //because the text is not visible so that plays a big role on this
            String chargeString = each.getAttribute("textContent");
            //System.out.println(chargeString);
            skillCharges.add(chargeString);
        }
        return skillCharges;
    }


    static public List<String> getSkillDurationInfo(WebElement driver){
        List<String> skillDurations = new ArrayList<>();
        WebElement duration = driver.findElement(By.xpath("//div[@class='" + Constants.skillDuration + "']"));
        List<WebElement> effectDescriptions = duration.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            skillDurations.add(each.getAttribute("innerHTML").trim());
        }
        return skillDurations;
    }

    /*
    static public List<String> getSkillDurationInfo(WebDriver driver){
        List<String> skillDurations = new ArrayList<>();
        WebElement duration = driver.findElement(By.xpath("//div[@class='" + Constants.skillDuration + "']"));
        List<WebElement> effectDescriptions = duration.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            skillDurations.add(each.getAttribute("innerHTML").trim());
        }
        return skillDurations;
    }

    static public List<String> getSkillEffectInfo(WebDriver driver){
        List<String> skillEffects = new ArrayList<>();
        WebElement skillDescription = driver.findElement(By.className(Constants.skillDescription));
        List<WebElement> effectDescriptions = skillDescription.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            //System.out.println(each.getAttribute("textContent"));
            skillEffects.add(each.getAttribute("textContent").trim());
        }
        return skillEffects;
    }
    */

    static public List<String> getSkillEffectInfo(WebElement driver){
        List<String> skillEffects = new ArrayList<>();
        WebElement skillDescription = driver.findElement(By.className(Constants.skillDescription));
        List<WebElement> effectDescriptions = skillDescription.findElements(By.className("effect-description"));
        for(WebElement each: effectDescriptions){
            //System.out.println(each.getAttribute("textContent"));
            skillEffects.add(each.getAttribute("textContent").trim());
        }
        return skillEffects;
    }

    static public String getSpChargeType(WebElement driver){
        WebElement spChargeType = driver.findElement(By.xpath("//div[@class='" + Constants.spChargeType + "' ]"));

        return spChargeType.findElement(By.className("effect-description")).getText();
    }

    static public String getSkillActivationType(WebElement driver){
        WebElement skillActivation = driver.findElement(By.xpath("//div[@class='" + Constants.skillActivation + "']"));
        return skillActivation.findElement(By.className("effect-description")).getText();
    }
    /*
    static public String getSpChargeType(WebDriver driver){
        WebElement spChargeType = driver.findElement(By.xpath("//div[@class='" + Constants.spChargeType + "' ]"));

        return spChargeType.findElement(By.className("effect-description")).getText();
    }

    static public String getSkillActivationType(WebDriver driver){
        WebElement skillActivation = driver.findElement(By.xpath("//div[@class='" + Constants.skillActivation + "']"));
        return skillActivation.findElement(By.className("effect-description")).getText();
    }
     */
}
