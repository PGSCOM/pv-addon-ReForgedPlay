package su.plo.replayvoice;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod("pv-addon-replaymod")
public class ReplayVoiceNeoForge {

    public ReplayVoiceNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        // Reuse existing initializer code. Further adaptation of networking
        // (ClientPlayNetworking) may be required for NeoForge.
        try {
            ReplayVoiceAddon.INSTANCE.onInitializeClient();
            ReplayVoiceAddon.INSTANCE.onAddonInitialize();
        } catch (Throwable t) {
            // swallow to avoid crashing mod class load; developer should adjust
            // implementation to NeoForge APIs and network registration.
            t.printStackTrace();
        }
    }
}
