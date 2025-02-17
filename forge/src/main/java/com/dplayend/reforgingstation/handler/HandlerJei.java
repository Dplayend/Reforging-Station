package com.dplayend.reforgingstation.handler;

import com.dplayend.reforgingstation.ReforgingStation;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class HandlerJei implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ReforgingStation.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        //registration.addGuiContainerHandler(CuriosScreen.class, new CuriosContainerHandler());
        //registration.addGuiContainerHandler(ReforgingStationScreen.class, new ReforgingStationMenu());
    }
}
