package com.operationpotato.itemlist.api;

public interface Plugin {
	default void registerExclusionZones(ExclusionZoneManager exclusionZoneManager) {}

	default void registerHoveredItems(HoveredItemManager hoveredItemManager) {}
}
