/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package huyvq.connection;

import static com.sun.activation.registries.LogSupport.log;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Dell
 */
public class RegexProcess {
     private String path;
    private InputStream is;
    private String result;

    public RegexProcess(String path) {
        this.path = path;
    }
    
    //truy cập url
    public RegexProcess access() {
        try {
            URL url = new URL(this.path);
            this.is = url.openStream();
            String s = "";
            Scanner sc = new Scanner(is, "UTF-8");
            while (sc.hasNext()) {
                s += sc.nextLine() + "\n";
            }
            this.result = s;
        } catch (MalformedURLException ex) {
            log("MalformedURLException " + ex.getMessage());
        } catch (IOException ex) {
            log("IOException " + ex.getMessage());
        }
        return this;
    }
    
    public RegexProcess match(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(this.result);
        this.result = "";
        while (matcher.find()) {
            this.result += matcher.group();
        }
        return this;
    }
    
    public RegexProcess clean(String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.result = pattern.matcher(this.result).replaceAll("");
        return this;
    }
    
    public RegexProcess replace(String regex, String with) {
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        this.result = pattern.matcher(this.result).replaceAll(with);
        return this;
    }
    
    public RegexProcess append(String s) {
        this.result += s;
        return this;
    }
    
    @Override
    public String toString() {
        try {
            this.is.close();
        } catch (IOException ex) {
            log("IO " + ex.getMessage());
        }
        return this.result;
    }
    
}
