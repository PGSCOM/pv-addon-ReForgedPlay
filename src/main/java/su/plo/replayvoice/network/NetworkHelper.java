package su.plo.replayvoice.network;

import lombok.experimental.UtilityClass;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientCommonPacketListener;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public final class NetworkHelper {

    public static Packet<ClientCommonPacketListener> createS2CPacket(@NotNull ResourceLocation resourceLocation, byte[] data) {
        ByteArrayCodec codec = CodecManager.getCodec(resourceLocation);
        return new ClientboundCustomPayloadPacket(new ByteArrayPayload(codec.getType(), data));
    }
}
