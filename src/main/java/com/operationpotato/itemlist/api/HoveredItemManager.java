package com.operationpotato.itemlist.api;

import org.jetbrains.annotations.ApiStatus;

public interface HoveredItemManager {
	@ApiStatus.Experimental
	void addProvider(HoveredItemProvider provider);

	void addConsumer(HoveredItemConsumer consumer);
}
