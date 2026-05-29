package com.operationpotato.itemlist.api;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.item.ItemStack;

public interface HoveredItemConsumer {
	boolean consume(Screen screen, ItemStack stack, KeyEvent event);
}
