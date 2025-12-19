package com.replaymod.lib.de.johni0702.minecraft.gui.utils;

/**
 * Minimal stub to satisfy compilation when the shaded ReplayMod GUI helper
 * classes are not present on the NeoForge 1.21.1 classpath.
 */
public class EventRegistrations {

    /**
     * No-op registration helper; the actual ReplayMod library provides proper
     * event wiring. Here we only keep the signature used by VoicechatModule.
     */
    public <E> void on(Object event, E handler) {
        // no-op stub
    }

    /**
     * Complete registration (noop stub).
     */
    public void register() {
        // no-op stub
    }
}
