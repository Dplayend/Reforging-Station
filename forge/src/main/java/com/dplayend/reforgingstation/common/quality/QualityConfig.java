package com.dplayend.reforgingstation.common.quality;

import com.dplayend.reforgingstation.handler.HandlerJustPotionRings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static com.dplayend.reforgingstation.handler.HandlerConfig.modFolder;

public class QualityConfig {
    private static final File qualityFolder = new File(modFolder, "quality");
    public static Quality[] DATA;

    public static void loadQualityFiles() {
        try {
            List<Path> list = Files.list(qualityFolder.toPath()).filter(Files::isRegularFile).toList();
            DATA = new Quality[list.size()];
            for (int i = 0; i < list.size(); i++) {
                Path path = list.get(i);
                File qualityFiles = path.toFile();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                if (qualityFiles.exists()) {
                    try {
                        FileReader fileReader = new FileReader(qualityFiles);
                        DATA[i] = gson.fromJson(fileReader, Quality.class);
                        fileReader.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createQualityFiles() {
        if (!modFolder.exists()) modFolder.mkdir();
        if (!qualityFolder.exists()) qualityFolder.mkdir();

        createQualityMaterial("quality_from_diamond", createQuality(Items.DIAMOND, Items.DIAMOND_HORSE_ARMOR));
        createQualityMaterial("quality_from_iron_ingot", createQuality(Items.IRON_INGOT, Items.IRON_HORSE_ARMOR, Items.SHIELD));
        createQualityMaterial("quality_from_gold_ingot", createQuality(Items.GOLD_INGOT, Items.GOLDEN_HORSE_ARMOR));
        createQualityMaterial("quality_from_leather", createQuality(Items.LEATHER, Items.LEATHER_HORSE_ARMOR));
        createQualityMaterial("quality_from_scute", createQuality(Items.SCUTE, Items.TURTLE_HELMET));
        createQualityMaterial("quality_from_stick", createQuality(Items.STICK, Items.BOW, Items.CROSSBOW, Items.FISHING_ROD));
        createQualityMaterial("quality_from_prismarine_shard", createQuality(Items.PRISMARINE_SHARD, Items.TRIDENT));

        HandlerJustPotionRings.qualityMaterial();
    }

    public static void createQualityMaterial(String fileName, Quality quality) {
        File qualityFiles = new File(qualityFolder, fileName + ".json");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        if (!qualityFiles.exists()) {
            if (!qualityFiles.getParentFile().exists()) qualityFiles.getParentFile().mkdir();
            try {
                FileWriter fileWriter = new FileWriter(qualityFiles);
                fileWriter.write(gson.toJson(quality));
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Quality createQuality(Item material, Item... values) {
        List<String> valueList = new ArrayList<>();
        for (Item value : values) valueList.add(ForgeRegistries.ITEMS.getKey(value).toString());
        return new Quality(ForgeRegistries.ITEMS.getKey(material).toString(), valueList);
    }

    public static class Quality {
        public String material;
        public List<String> values;

        public Quality(String material, List<String> values) {
            this.material = material;
            this.values = values;
        }
    }
}
