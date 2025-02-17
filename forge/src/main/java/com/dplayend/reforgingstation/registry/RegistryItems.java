package com.dplayend.reforgingstation.registry;

import com.dplayend.reforgingstation.ReforgingStation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryItems {
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, ReforgingStation.MOD_ID);

    public static final RegistryObject<Item> REFORGING_STATION = REGISTER.register("reforging_station", () -> new BlockItem(RegistryBlocks.REFORGING_STATION.get(), new Item.Properties()));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
