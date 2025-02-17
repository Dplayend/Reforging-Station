package com.dplayend.reforgingstation.mixin;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LivingEntity.class})
public abstract class MixLivingEntity {

    @Inject(method = {"createLivingAttributes"}, at = @At(value = "TAIL"), cancellable = true)
    private static void createAttributes(CallbackInfoReturnable<AttributeSupplier.Builder> info) {
        info.setReturnValue(info.getReturnValue().add(RegistryAttributes.CRITICAL_DAMAGE.get()).add(RegistryAttributes.PROJECTILE_DAMAGE.get()).add(RegistryAttributes.JUMP_HEIGHT.get()).add(RegistryAttributes.DIG_SPEED.get()).add(RegistryAttributes.DAMAGE_RESISTANCE.get()).add(RegistryAttributes.MAGIC_RESIST.get()));
    }

    @Inject(method = {"tick"}, at = @At(value = "TAIL"))
    private void tick(CallbackInfo info) {
        LivingEntity self = (LivingEntity) (Object) this;
        Quality.addModifiers(self);
    }

    @Inject(method = {"getJumpPower"}, at = @At(value = "TAIL"), cancellable = true)
    private void jumpHeightAttribute(CallbackInfoReturnable<Float> info) {
        float newValue = 0;
        float baseValue = info.getReturnValue();
        LivingEntity self = (LivingEntity) (Object) this;
        if (self instanceof Player player) {
            for (AttributeModifier modifier : player.getAttribute(RegistryAttributes.JUMP_HEIGHT.get()).getModifiers().stream().toList()) {
                newValue += (float)(modifier.getAmount() * 0.21F);
            }
        }
        info.setReturnValue(baseValue + newValue);
    }

    @Inject(method = {"getDamageAfterArmorAbsorb"}, at = @At(value = "RETURN"), cancellable = true)
    private void damageAndMagicResistanceAttributes(DamageSource source, float damage, CallbackInfoReturnable<Float> info) {
        LivingEntity self = (LivingEntity) (Object) this;
        float baseValue = info.getReturnValue();
        float damageResistance = 0;
        float magicResist = 0;

        if (self instanceof Player player) {
            AttributeInstance damageResistanceInstance = player.getAttribute(RegistryAttributes.DAMAGE_RESISTANCE.get());
            damageResistance += (float) damageResistanceInstance.getValue();
            for (AttributeModifier modifier : damageResistanceInstance.getModifiers()) {
                damageResistance += (float) (modifier.getAmount());
            }

            if (source.isIndirect()) magicResist += (float)(player.getAttribute(RegistryAttributes.MAGIC_RESIST.get()).getValue());
        }

        info.setReturnValue(baseValue - (baseValue * damageResistance) - (magicResist));
    }
}
