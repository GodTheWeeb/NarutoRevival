package com.godhenko.narutomod.network.packets.serverpackets;

import com.godhenko.narutomod.data.capabilities.CapabilityData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketGenjutsuServer {
    private int data;

    public PacketGenjutsuServer() {
    }

    public PacketGenjutsuServer(int data) {
        this.data = data;
    }

    public static void encode(PacketGenjutsuServer message, PacketBuffer buf) {
        buf.writeInt(message.data);
    }

    public static PacketGenjutsuServer decode(PacketBuffer buffer) {
        return new PacketGenjutsuServer(buffer.readInt());
    }

    public static void handle(PacketGenjutsuServer message, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if (ctx.get().getDirection().getReceptionSide().isServer() && ctx.get().getDirection().getOriginationSide().isClient()) {
                PlayerEntity player = ctx.get().getSender();
                player.getCapability(CapabilityData.ENTITY_DATA_CAPABILITY).ifPresent(data1 -> {
                    data1.setGenjutsu(message.data);
                });
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
