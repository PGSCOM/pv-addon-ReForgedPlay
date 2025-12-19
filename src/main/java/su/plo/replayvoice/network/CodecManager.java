package su.plo.replayvoice.network;

import lombok.experimental.UtilityClass;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public final class CodecManager {

    private static final Map<ResourceLocation, ByteArrayCodec> codecs = new HashMap<>();

    public static @NotNull ByteArrayCodec getCodec(@NotNull final ResourceLocation location) {
        return codecs.computeIfAbsent(location, key -> new ByteArrayCodec(key));
    }
}
