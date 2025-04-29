package com.qa.orangehrm.Base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Base {
    public WebDriver driver;
    public Properties properties;

    public WebDriver init_driver(String browserName){
        if(browserName.equals("chrome")){
            if(properties.getProperty("headless").equals("yes")){
                ChromeOptions options=new ChromeOptions();
                options.addArguments("--headless");
                driver=new ChromeDriver(options);
            }else{
                driver=new ChromeDriver();
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public Properties init_Properties(){
        properties=new Properties();
        try {
            FileInputStream file=new FileInputStream("C:\\Users\\dhars\\IdeaProjects\\KeywordDrivenFrameWork\\src\\main\\java\\com\\qa\\orangehrm\\Config\\config.properties");
            properties.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return properties;
    }
}