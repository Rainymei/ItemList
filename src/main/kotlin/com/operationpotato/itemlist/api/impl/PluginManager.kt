package com.operationpotato.itemlist.api.impl

import com.operationpotato.itemlist.api.ExclusionZone
import com.operationpotato.itemlist.api.Plugin
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.client.input.KeyEvent
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.ApiStatus
import tech.thatgravyboat.skyblockapi.helpers.McScreen

@ApiStatus.Internal
object PluginManager {
	private val plugins: List<Plugin> =
		FabricLoader.getInstance().getEntrypoints("skyblock-item-list", Plugin::class.java)
	private val exclusionZoneManager = ExclusionZoneManagerImpl()
	private val hoveredItemManager = HoveredItemManagerImpl()

	init {
		registerPlugins()
	}

	fun registerPlugins() {
		for (plugin in plugins) {
			plugin.registerExclusionZones(exclusionZoneManager)
			plugin.registerHoveredItems(hoveredItemManager)
		}
	}

	fun refreshExclusionZones() {
		exclusionZoneManager.calculateExclusionZones()
	}

	fun getExclusionZones(): List<ExclusionZone> {
		return exclusionZoneManager.getExclusionZones()
	}

	fun didExclusionZonesChange(): Boolean {
		return exclusionZoneManager.getHasChanged()
	}

	fun provideHoveredItem(stack: ItemStack, keyEvent: KeyEvent): Boolean {
		return hoveredItemManager.provideHoveredItem(McScreen.self!!, stack, keyEvent)
	}
}
