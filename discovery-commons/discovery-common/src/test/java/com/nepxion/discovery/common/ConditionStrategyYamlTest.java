package com.nepxion.discovery.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

/**
 * <p>Title: Nepxion Discovery</p>
 * <p>Description: Nepxion Discovery</p>
 * <p>Copyright: Copyright (c) 2017-2050</p>
 * <p>Company: Nepxion</p>
 * @author Haojun Ren
 * @version 1.0
 */

import org.yaml.snakeyaml.Yaml;

import com.nepxion.discovery.common.constant.DiscoveryConstant;
import com.nepxion.discovery.common.entity.ConditionStrategy;

public class ConditionStrategyYamlTest {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        ConditionStrategy conditionStrategy = yaml.loadAs(testFile("sample.yaml"), ConditionStrategy.class);
        
        System.out.println(conditionStrategy);
    }
    
    public static String testFile(String fileName) {
        File file = new File("src/test/resources/" + fileName);
        InputStream inputStream = null;
        String rule = null;
        try {
            inputStream = new FileInputStream(file);
            rule = IOUtils.toString(inputStream, DiscoveryConstant.ENCODING_UTF_8);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }

        return rule;
    }
}