package configurationFileReader.Config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private final Properties properties;

    public ConfigReader(){
        BufferedReader reader;
        String propertyFilePath = "configuration/Config.properties";
        try{
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try{
                properties.load(reader);
                reader.close();
            }catch (IOException exception){
                exception.printStackTrace();
            }
        }catch(FileNotFoundException exception){
            exception.printStackTrace();
            throw  new RuntimeException("Config.properties not found at "+propertyFilePath);
        }
    }

    public  String getPlatformName(){
        String platformName = properties.getProperty("platformName");
        if(platformName!=null)
            return platformName;
        else
            throw new RuntimeException("platformName is not provided in Config.properties file");
    }

    public  String getAppPackage(){
        String appPackage = properties.getProperty("appPackage");
        if(appPackage!=null)
            return appPackage;
        else
            throw new RuntimeException("appPackage is not provided in Config.properties file");
    }

    public  String getAppActivity(){
        String appActivity = properties.getProperty("appActivity");
        if(appActivity!=null)
            return appActivity;
        else
            throw new RuntimeException("appActivity is not provided in Config.properties file");
    }

    public  String getAutomationName(){
        String automationName = properties.getProperty("automationName");
        if(automationName!=null)
            return automationName;
        else
            throw new RuntimeException("automationName is not provided in Config.properties file");
    }

    public  String getCommandTimeout(){
        String commandTimeout = properties.getProperty("commandTimeout");
        if(commandTimeout!=null)
            return commandTimeout;
        else
            throw new RuntimeException("commandTimeout is not provided in Config.properties file");
    }

    public  String getApkPath(){
        String apkPath = properties.getProperty("apkPath");
        if(apkPath!=null)
            return apkPath;
        else
            throw new RuntimeException("apkPath is not provided in Config.properties file");
    }

    public  String getNoReset(){
        String noReset = properties.getProperty("noReset");
        if(noReset!=null)
            return noReset;
        else
            throw new RuntimeException("noReset is not provided in Config.properties file");
    }

    public  String getAppiumServerAndroidURL(){
        String appiumServerEndpointURL = properties.getProperty("appiumServerEndpointURL");
        if(appiumServerEndpointURL!=null)
            return appiumServerEndpointURL;
        else
            throw new RuntimeException("appiumServerEndpointURL is not provided in Config.properties file");
    }

}
