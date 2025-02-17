package com.dplayend.reforgingstation.mixin;

import com.dplayend.reforgingstation.registry.RegistryAttributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({Player.class})
public class MixPlayer {
    @Redirect(method = {"getDigSpeed"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
    private float digSpeedAttribute(Inventory instance, BlockState blockState) {
        Player self = (Player) (Object) this;
        float digSpeedPercent = 0.0F;
        for (AttributeModifier modifier : self.getAttribute(RegistryAttributes.DIG_SPEED.get()).getModifiers().stream().toList()) {
            digSpeedPercent += (float) modifier.getAmount();
        }
        return self.getInventory().getDestroySpeed(blockState) + (self.getInventory().getDestroySpeed(blockState) * digSpeedPercent);
    }

    @Redirect(method = {"attack"}, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/event/entity/player/CriticalHitEvent;getDamageModifier()F"))
    private float criticalDamageAttribute(CriticalHitEvent instance) {
        Player self = (Player) (Object) this;
        float criticalDamagePercent = 0.0F;
        for (AttributeModifier modifier : self.getAttribute(RegistryAttributes.CRITICAL_DAMAGE.get()).getModifiers().stream().toList()) {
            criticalDamagePercent += (float) modifier.getAmount();
        }
        return instance.getDamageModifier() + (criticalDamagePercent);
    }
}
