package com.operationpotato.itemlist.compat

import com.operationpotato.itemlist.config.ConfigScreen
import com.terraformersmc.modmenu.api.ConfigScreenFactory
import com.terraformersmc.modmenu.api.ModMenuApi
import net.minecraft.client.gui.screens.Screen

class ModMenuCompatibility : ModMenuApi {
	override fun getModConfigScreenFactory(): ConfigScreenFactory<Screen> {
		return { parent: Screen? ->
			ConfigScreen.createScreen(parent)
		}
	}
}
