package com.operationpotato.itemlist.api;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;

import java.util.List;

public interface ExclusionZoneProvider<T extends Screen> {
	List<Rect2i> provide(T screen);
}
