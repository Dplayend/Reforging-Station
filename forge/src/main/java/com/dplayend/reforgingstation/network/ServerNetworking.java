package com.dplayend.reforgingstation.network;

import com.dplayend.reforgingstation.ReforgingStation;
import com.dplayend.reforgingstation.network.common.ChangeQualitySP;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class ServerNetworking {
    private static int packetId = 0;
    public static SimpleChannel instance;

    public ServerNetworking() {
    }

    private static int id() {
        return packetId++;
    }

    public static void init() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ReforgingStation.MOD_ID, "network")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions((s) -> true).serverAcceptedVersions((s) -> true).simpleChannel();
        instance = net;
        net.messageBuilder(ChangeQualitySP.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(ChangeQualitySP::new).encoder(ChangeQualitySP::toBytes).consumerMainThread(ChangeQualitySP::handle).add();
    }

    public static <MSG> void sendToServer(MSG message) {
        instance.sendToServer(message);
    }
}
