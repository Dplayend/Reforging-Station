package com.dplayend.reforgingstation.handler;

import com.dplayend.justpotionrings.registry.RegistryItems;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.ModList;

import static com.dplayend.reforgingstation.common.quality.QualityConfig.*;

public class HandlerJustPotionRings {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("justpotionrings");
    }

    public static void qualityMaterial() {
        if (isModLoaded()) {
            createQualityMaterial("just_potion_rings_quality", createQuality(Items.GOLD_INGOT, RegistryItems.RING.get(), RegistryItems.POTION_RING.get()));
        }
    }
}
