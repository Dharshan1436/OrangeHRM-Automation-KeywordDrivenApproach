package com.qa.orangehrm.keywordEngine;
import com.qa.orangehrm.Base.Base;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class KeywordEngine {
    public WebDriver driver;
    public Properties properties;
    public Base base;
    public WebElement element;

    public static Workbook workbook;
    public static Sheet sheet;
    public final String SCENARIO_SHEET_PATH="C:\\Users\\dhars\\IdeaProjects\\KeywordDrivenFrameWork\\src\\main\\java\\com\\qa\\orangehrm\\TestData\\OrangeHRMKeywordDriven.xlsx";

    public void startExecution(String sheetName) throws InterruptedException {
        String locatorName=null;
        String locatorValue=null;
        FileInputStream file=null;
        try {
            file=new FileInputStream(SCENARIO_SHEET_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            workbook= WorkbookFactory.create(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sheet=workbook.getSheet(sheetName);
        int k=0;
        for(int i=0;i<sheet.getLastRowNum();i++){
            try {
                String locatorColValue = sheet.getRow(i + 1).getCell(k + 1).toString().trim();
                if (!locatorColValue.equalsIgnoreCase("NA")) {
                    locatorName = locatorColValue.split("=")[0].trim();
                    locatorValue = locatorColValue.split("=")[1].trim();

                }
                String action = sheet.getRow(i + 1).getCell(k + 2).toString().trim();
                String value = sheet.getRow(i + 1).getCell(k + 3).toString().trim();

                switch (action) {
                    case "open browser":
                        base = new Base();
                        properties = base.init_Properties();
                        if (value.isEmpty() || value.equals("NA")) {
                            driver = base.init_driver(properties.getProperty("browser"));
                        } else {
                            driver = base.init_driver(value);
                        }
                        break;
                    case "enter url":
                        if (value.isEmpty() || value.equals("NA")) {
                            driver.get(properties.getProperty("url"));
                        } else {
                            driver.get(value);
                        }
                        Thread.sleep(2000);
                        break;
                    case "quit":
                        Thread.sleep(26000);
                        driver.quit();
                        break;
                    default:
                        break;
                }
                if(!locatorColValue.equalsIgnoreCase("NA")) {
                    switch (locatorName) {
                        case "name":
                            element = driver.findElement(By.name(locatorValue));
                            if (action.equalsIgnoreCase("sendkeys")) {
                                element.clear();
                                element.sendKeys(value);
                            } else if (action.equalsIgnoreCase("click")) {
                                element.click();
                            }
                            locatorName = null;
                            break;
                        case "tagName":
                            element = driver.findElement(By.tagName(locatorValue));
                            element.click();
                            locatorName = null;
                            break;
                        default:
                            break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}