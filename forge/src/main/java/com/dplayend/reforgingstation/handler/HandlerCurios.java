package com.dplayend.reforgingstation.handler;

import com.dplayend.reforgingstation.common.quality.Quality;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;

import static com.dplayend.reforgingstation.common.quality.Quality.*;

public class HandlerCurios {
    public static boolean isModLoaded() {
        return ModList.get().isLoaded("curios");
    }

    public static void randomizerAccessoryQuality(ItemStack stack) {
        if (isModLoaded()) stack.getOrCreateTag().putString("quality", getRandomQuality(Quality.accessoryQualityList()).quality());
    }

    public static void createAccessoryQuality(ItemStack stack) {
        if (isModLoaded()) createQualities(stack, getRandomQuality(Quality.accessoryQualityList()).quality());
    }

    public static boolean accessoryQuality(LivingEntity livingEntity) {
        for (ItemStack stack : accessoryStackList(livingEntity)) {
            if (!stack.isEmpty()) return true;
        }
        return false;
    }

    public static boolean accessoryQuality(ItemStack stack) {
        if (isModLoaded()) {
            return !stack.isEmpty() && !CuriosApi.getCuriosHelper().getCurioTags(stack.getItem()).isEmpty();
        }
        return false;
    }

    public static List<ItemStack> accessoryStackList(LivingEntity livingEntity) {
        List<ItemStack> list = new ArrayList<>();
        if (isModLoaded()) {
            CuriosApi.getCuriosInventory(livingEntity).ifPresent(handler -> {
                for (int i = 0; i < handler.getEquippedCurios().getSlots(); i++) {
                    list.add(handler.getEquippedCurios().getStackInSlot(i));
                }
            });
        }
        return list;
    }
}
