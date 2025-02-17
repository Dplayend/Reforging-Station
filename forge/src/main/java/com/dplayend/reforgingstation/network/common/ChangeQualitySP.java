package com.dplayend.reforgingstation.network.common;

import com.dplayend.reforgingstation.common.quality.QualityUtil;
import com.dplayend.reforgingstation.handler.HandlerCurios;
import com.dplayend.reforgingstation.common.quality.Quality;
import com.dplayend.reforgingstation.network.ServerNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.*;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ChangeQualitySP {
    public ChangeQualitySP() {
    }

    public ChangeQualitySP(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ItemStack stack = player.containerMenu.getSlot(36).getItem();
                if (stack.hasTag()) {
                    if (stack.getTag() != null && !stack.getTag().getString("quality").isEmpty()) {
                        switch (QualityUtil.getQuality(stack)) {
                            case "helmet" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.helmetQualityList()).quality()); break;
                            case "chestplate" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.chestplateQualityList()).quality()); break;
                            case "leggings" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.leggingsQualityList()).quality()); break;
                            case "boots" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.bootsQualityList()).quality()); break;
                            case "shield" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.shieldQualityList()).quality()); break;
                            case "pet" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.petQualityList()).quality()); break;
                            case "bow" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.bowQualityList()).quality()); break;
                            case "rod" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.rodQualityList()).quality()); break;
                            case "tool" : stack.getTag().putString("quality", Quality.getRandomQuality(Quality.toolQualityList()).quality()); break;
                            case "accessory" : HandlerCurios.randomizerAccessoryQuality(stack); break;
                        }
                        player.containerMenu.getSlot(37).getItem().setCount(player.containerMenu.getSlot(37).getItem().getCount() - 1);
                        player.containerMenu.getSlot(36).set(stack);
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }

    public static void send() {
        ServerNetworking.sendToServer(new ChangeQualitySP());
    }
}
