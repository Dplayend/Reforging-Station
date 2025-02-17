package com.dplayend.reforgingstation.registry;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.common.block.ReforgingStationBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlocks {
    public static final DeferredRegister<Block> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCKS, ReforgingStation.MOD_ID);

    public static final RegistryObject<ReforgingStationBlock> REFORGING_STATION = REGISTER.register("reforging_station", ReforgingStationBlock::new);

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
