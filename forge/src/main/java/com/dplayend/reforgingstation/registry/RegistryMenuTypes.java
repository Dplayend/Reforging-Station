package com.dplayend.reforgingstation.registry;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.common.screen.ReforgingStationMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryMenuTypes {
    public static final DeferredRegister<MenuType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.MENU_TYPES, ReforgingStation.MOD_ID);

    public static final RegistryObject<MenuType<ReforgingStationMenu>> REFORGING_STATION = REGISTER.register("reforging_station_menu", () -> IForgeMenuType.create(ReforgingStationMenu::new));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
