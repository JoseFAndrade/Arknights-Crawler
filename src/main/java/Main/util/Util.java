package Main.util;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

public class Util {

    //todo say that this function can throw an exception
    //todo make sure to change the pictures for the updated attack ranges
    //this function will be used for both Attack Ranges for operators as well as skill ranges for operators
    static public int[][] getRange(WebElement driver) throws NoSuchElementException {
        WebElement rangeBox = driver.findElement(By.className("range-box"));
        //2d array is rows by column

        List<WebElement> columns = rangeBox.findElements(By.className("range-cell"));
        int columnSize = columns.size();
        int rowSize = columns.get(0).findElements(By.xpath(".//span")).size();

        int[][] skillTiles = new int[rowSize][columnSize];

        //System.out.println("column and row: " + columnSize +" "+ rowSize);

        int c = 0;
        //we end up inside every range-cell div
        for(WebElement rangeCell: columns){
            int r = 0;
            for(WebElement box: rangeCell.findElements(By.xpath(".//span"))){
                switch(box.getAttribute("class")){
                    case Constants.characterBox:
                        skillTiles[r][c] = 1;
                        break;
                    case Constants.attackGrid:
                        skillTiles[r][c] = 0;
                        break;
                    case Constants.nullGrid:
                        skillTiles[r][c] = 3;
                        break;
                }
                r++;
            }
            c++;
        }
        return skillTiles;
    }


    static public String rangeToString(int[][] rangeTiles){
        if(rangeTiles == null)
            return "";
        String result = "";
        for(int row = 0; row < rangeTiles.length; row++){
            for(int column = 0; column < rangeTiles[0].length; column++){
                result+=rangeTiles[row][column] + " ";
            }
            result +="\n";
        }
        return result;
    }
}
