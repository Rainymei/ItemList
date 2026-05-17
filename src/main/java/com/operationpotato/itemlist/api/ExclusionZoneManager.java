package com.operationpotato.itemlist.api;

import net.minecraft.client.gui.screens.Screen;

public interface ExclusionZoneManager {
	<T extends Screen> void addProvider(Class<T> screenClass, ExclusionZoneProvider<T> provider);
}
