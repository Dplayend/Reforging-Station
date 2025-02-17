package com.dplayend.reforgingstation.mixin;

import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.common.quality.QualityUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin({ItemStack.class})
public abstract class MixItemStack {
    @Inject(method = {"inventoryTick"}, at = @At(value = "TAIL"))
    private void inventoryTick(Level level, Entity entity, int slot, boolean selected, CallbackInfo info) {
        if (entity instanceof ServerPlayer player) {
            player.containerMenu.slots.forEach(playerSlots -> QualityUtil.createQuality(playerSlots.getItem()));
        }
        //if (entity instanceof LivingEntity self) Quality.addModifiers(self);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/TooltipFlag;isAdvanced()Z", ordinal = 2, shift = At.Shift.BEFORE), method = "getTooltipLines", locals = LocalCapture.CAPTURE_FAILHARD)
    private void getTooltipLines(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> info, List<Component> list) {
        ItemStack that = (ItemStack) (Object) this;
        if (player != null) {
            if (!(player.containerMenu instanceof CraftingMenu) && !(player.containerMenu instanceof InventoryMenu)
                    || ((player.containerMenu instanceof CraftingMenu || player.containerMenu instanceof InventoryMenu)
                    && !player.containerMenu.getSlot(0).getItem().equals(that))) {
                QualityUtil.createTooltip(that, list, false);
            }
        }
    }
}
