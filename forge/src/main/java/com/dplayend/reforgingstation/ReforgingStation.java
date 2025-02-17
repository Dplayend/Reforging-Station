package com.dplayend.reforgingstation;

import com.dplayend.reforgingstation.handler.HandlerConfig;
import com.dplayend.reforgingstation.network.ServerNetworking;
import com.dplayend.reforgingstation.registry.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ReforgingStation.MOD_ID)
public class ReforgingStation {
    public static final String MOD_ID = "reforgingstation";

    public ReforgingStation() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        RegistryItems.load(eventBus);
        RegistryBlocks.load(eventBus);

        RegistryBlockEntities.load(eventBus);
        RegistryMenuTypes.load(eventBus);

        RegistryAttributes.load(eventBus);
        MinecraftForge.EVENT_BUS.register(this);
        ServerNetworking.init();

        eventBus.addListener(this::afterLoad);
        eventBus.addListener(this::addCreativeTab);
    }

    public void afterLoad(FMLCommonSetupEvent event) {
        HandlerConfig.init();
    }

    public void addCreativeTab(BuildCreativeModeTabContentsEvent output) {
        if (output.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS)) {
            output.accept(RegistryItems.REFORGING_STATION.get());
        }
    }
}
