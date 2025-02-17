package com.dplayend.reforgingstation.common.screen;

import com.dplayend.reforgingstation.common.entity.ReforgingStationBlockEntity;
import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.registry.RegistryMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ReforgingStationMenu extends AbstractContainerMenu {
    private final ReforgingStationBlockEntity blockEntity;

    public ReforgingStationMenu(int syncId, Inventory inventory, FriendlyByteBuf byteBuf) {
        super(RegistryMenuTypes.REFORGING_STATION.get(), syncId);
        this.blockEntity = (ReforgingStationBlockEntity) inventory.player.level().getBlockEntity(byteBuf.readBlockPos());
        createInventorySlots(inventory);
        createResultSlots();
    }

    public boolean canChangeQuality() {
        return isMatchingMaterial() && this.slots.get(36).getItem().getTag() != null && !this.slots.get(36).getItem().getTag().getString("quality").isEmpty();
    }

    public boolean isMatchingMaterial() {
        return this.slots.get(37).getItem().is(Quality.getAllMatchingMaterial()) || Quality.matchingMaterial(this.slots.get(36).getItem(), this.slots.get(37).getItem());
    }

    private void createResultSlots() {
        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(iItemHandler -> {
            this.addSlot(new SlotItemHandler(iItemHandler, 0, 80, 19) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return stack.getTag() != null && !stack.getTag().getString("quality").isEmpty();
                }

                @Override
                public boolean mayPickup(@NotNull Player player) {
                    return true;
                }
            });

            this.addSlot(new SlotItemHandler(iItemHandler, 1, 80, 59) {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return true;
                }

                @Override
                public boolean mayPickup(@NotNull Player player) {
                    return true;
                }
            });
        });
    }

    private void createInventorySlots(Inventory inventory) {
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < 36) {
            if (!moveItemStackTo(sourceStack, 36, 38, false)) {
                return ItemStack.EMPTY;
            }
        } else if (index < 38) {
            if (!moveItemStackTo(sourceStack, 0, 36, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() != 0) {
            sourceSlot.setChanged();
        } else {
            sourceSlot.set(ItemStack.EMPTY);
        }
        sourceSlot.onTake(player, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return true;
    }
}