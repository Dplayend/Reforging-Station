package com.dplayend.reforgingstation.registry;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.common.entity.ReforgingStationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ReforgingStation.MOD_ID);

    public static final RegistryObject<BlockEntityType<ReforgingStationBlockEntity>> REFORGING_STATION = REGISTER.register("reforging_station_entity", () -> BlockEntityType.Builder.of(ReforgingStationBlockEntity::new, RegistryBlocks.REFORGING_STATION.get()).build(null));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
