package com.dplayend.reforgingstation.registry;

import com.dplayend.reforgingstation.ReforgingStation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryAttributes {
    private static final DeferredRegister<Attribute> REGISTER = DeferredRegister.create(ForgeRegistries.Keys.ATTRIBUTES, ReforgingStation.MOD_ID);

    public static final RegistryObject<Attribute> CRITICAL_DAMAGE = REGISTER.register("critical_damage", () -> new RangedAttribute("attribute.name.critical_damage", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> PROJECTILE_DAMAGE = REGISTER.register("projectile_damage", () -> new RangedAttribute("attribute.name.projectile_damage", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DIG_SPEED = REGISTER.register("dig_speed", () -> new RangedAttribute("attribute.name.dig_speed", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> JUMP_HEIGHT = REGISTER.register("jump_height", () -> new RangedAttribute("attribute.name.jump_height", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> DAMAGE_RESISTANCE = REGISTER.register("damage_resistance", () -> new RangedAttribute("attribute.name.damage_resistance", 0.0D, 0.0D, 1024.0D).setSyncable(true));
    public static final RegistryObject<Attribute> MAGIC_RESIST = REGISTER.register("magic_resist", () -> new RangedAttribute("attribute.name.magic_resist", 0.0D, 0.0D, 1024.0D).setSyncable(true));

    public static void load(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
