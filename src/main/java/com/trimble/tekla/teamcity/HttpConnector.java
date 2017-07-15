/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trimble.tekla.teamcity;

import com.atlassian.bitbucket.setting.Settings;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author jocs
 */
public class HttpConnector {
    
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger("BitbucketTeamcityHook");
    
    public void Post(TeamcityConfiguration conf, String url, Map<String, String> parameters, Settings settings) {
        
        try {                  
            
            String urlstr = conf.getUrl() + url;

            URL urldata = new URL(urlstr);
            logger.warn("Hook Request: "  + urlstr);
            
            String authStr = conf.getUserName() + ":" + conf.getPassWord();
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());
            
            HttpURLConnection connection = (HttpURLConnection) urldata.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            
            StringBuilder dataout = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                dataout.append(line);
            }
            
            TeamcityLogger.logMessage(settings, "Hook Reply: "  + line);
            
        } catch (Exception e) {
            TeamcityLogger.logMessage(settings, "Hook Exception: "  + e.getMessage());
            e.printStackTrace();
        }         
    }

    public String Get(TeamcityConfiguration conf, String url, Settings settings) throws MalformedURLException, IOException {
        
            String urlstr = conf.getUrl() + url;

            URL urldata = new URL(urlstr);
            TeamcityLogger.logMessage(settings, "Hook Request: "  + urlstr);
            
            String authStr = conf.getUserName() + ":" + conf.getPassWord();
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());
            
            HttpURLConnection connection = (HttpURLConnection) urldata.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            
            StringBuilder dataout = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                dataout.append(line);
            }
            
            TeamcityLogger.logMessage(settings, "Hook Reply: "  + line);
            
            return dataout.toString();
       
    }
    
    public String Get(String url, Settings settings) throws MalformedURLException, IOException {
        
            String urlstr = url;

            URL urldata = new URL(urlstr);
            TeamcityLogger.logMessage(settings, "Hook Request: "  + urlstr);
            
            
            HttpURLConnection connection = (HttpURLConnection) urldata.openConnection();

            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            //connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            
            StringBuilder dataout = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                dataout.append(line);
            }
            
            TeamcityLogger.logMessage(settings, "Hook Reply: "  + line);
            
            return dataout.toString();
       
    }
    
    public void PostPayload(TeamcityConfiguration conf, String url, String payload, Settings settings) {
        
        try {                  
            
            String urlstr = conf.getUrl() + url;

            URL urldata = new URL(urlstr);
            TeamcityLogger.logMessage(settings, "Hook Request: "  + urlstr);
            
            String authStr = conf.getUserName() + ":" + conf.getPassWord();
            String authEncoded = Base64.encodeBase64String(authStr.getBytes());
            
            HttpURLConnection connection = (HttpURLConnection) urldata.openConnection();

            connection.setRequestMethod("POST");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + authEncoded);
            
            if (payload != null) {
                connection.setRequestProperty("Content-Type", "application/xml; charset=utf-8");
                connection.setRequestProperty("Content-Length", Integer.toString(payload.length()));
                connection.getOutputStream().write(payload.getBytes("UTF8"));
            }

            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   = 
                new BufferedReader (new InputStreamReader (content));
            
            StringBuilder dataout = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                dataout.append(line);
            }
            
            TeamcityLogger.logMessage(settings, "Hook Reply: "  + line);
            
        } catch (Exception e) {
            TeamcityLogger.logMessage(settings, "Hook Exception: "  + e.getMessage());
            e.printStackTrace();
        }         
    }    
}
