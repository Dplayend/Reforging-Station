package com.dplayend.reforgingstation.handler;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.common.quality.QualityConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HandlerConfig {
    public static final File modFolder = new File( FMLPaths.CONFIGDIR.get() + "/" + ReforgingStation.MOD_ID);
    public static Data DATA = new Data();

    public static void init() {
        createConfigFile();
        QualityConfig.createQualityFiles();
        QualityConfig.loadQualityFiles();
    }

    public static void createConfigFile() {
        File configFile = new File(modFolder, "config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!configFile.exists()) {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdir();
            }
            try {
                FileWriter fileWriter = new FileWriter(configFile);
                fileWriter.write(gson.toJson(new Data()));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                FileReader fileReader = new FileReader(configFile);
                DATA = gson.fromJson(fileReader, Data.class);
                fileReader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class Data {
        public static String defaultAllMatchingMaterial = "minecraft:nether_star";

        public String allMatchingMaterial = defaultAllMatchingMaterial;
        public boolean showNormalQuality = false;
    }
}
