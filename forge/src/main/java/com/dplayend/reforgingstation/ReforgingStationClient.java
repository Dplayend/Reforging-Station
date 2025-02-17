package com.dplayend.reforgingstation;

import com.dplayend.reforgingstation.common.entity.ReforgingStationRenderer;
import com.dplayend.reforgingstation.common.screen.ReforgingStationScreen;
import com.dplayend.reforgingstation.registry.RegistryBlockEntities;
import com.dplayend.reforgingstation.registry.RegistryMenuTypes;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ReforgingStation.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ReforgingStationClient {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MenuScreens.register(RegistryMenuTypes.REFORGING_STATION.get(), ReforgingStationScreen::new);
    }

    @SubscribeEvent
    public static void registerBER(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RegistryBlockEntities.REFORGING_STATION.get(), ReforgingStationRenderer::new);
    }
}
