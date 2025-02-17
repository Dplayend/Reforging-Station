package com.dplayend.reforgingstation.mixin;

import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({AbstractArrow.class})
public class MixAbstractArrow {
    @Redirect(method = "onHitEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
    private boolean projectileDamageAttribute(Entity entity, DamageSource source, float baseDamage) {
        AbstractArrow self = (AbstractArrow) (Object) this;
        float damagePercent = 0.0F;
        if (self.getOwner() instanceof Player player) {
            for (AttributeModifier modifier : player.getAttribute(RegistryAttributes.PROJECTILE_DAMAGE.get()).getModifiers().stream().toList()) {
                damagePercent += (float) modifier.getAmount();
            }
        }
        return entity.hurt(source, baseDamage + (baseDamage * damagePercent));
    }
}
