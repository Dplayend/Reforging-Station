package com.dplayend.reforgingstation.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HandlerMinecraft {
    public static final Minecraft client = Minecraft.getInstance();

    @OnlyIn(Dist.CLIENT)
    public static void playSound(SoundEvent soundEvent) {
        client.getSoundManager().play(SimpleSoundInstance.forUI(soundEvent, 1.0F));
    }
}
