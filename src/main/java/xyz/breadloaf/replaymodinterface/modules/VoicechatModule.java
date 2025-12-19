package xyz.breadloaf.replaymodinterface.modules;

import com.replaymod.core.KeyBindingRegistry;
import com.replaymod.core.Module;

/**
 * VoicechatModule stub for ReForgedPlay compatibility.
 * ReForgedPlay may not have all the same events as ReplayMod,
 * so this is a minimal implementation that compiles but doesn't
 * register any event handlers.
 */
public class VoicechatModule implements Module {
    @Override
    public void initCommon() {
        // no-op for NeoForge build
    }

    @Override
    public void initClient() {
        // Event registration would go here if ReForgedPlay supports the same event system
        // For now, this is a stub to allow compilation
    }

    @Override
    public void registerKeyBindings(KeyBindingRegistry registry) {
        // no-op
    }
}

