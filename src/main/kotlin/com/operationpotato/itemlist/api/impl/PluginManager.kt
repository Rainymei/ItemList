package com.operationpotato.itemlist.api.impl

import com.operationpotato.itemlist.api.ExclusionZone
import com.operationpotato.itemlist.api.Plugin
import net.fabricmc.loader.api.FabricLoader

object PluginManager {
	private val plugins: List<Plugin> =
		FabricLoader.getInstance().getEntrypoints("skyblock-item-list", Plugin::class.java)
	private val exclusionZoneManager = ExclusionZoneManagerImpl()

	fun refreshExclusionZones() {
		exclusionZoneManager.calculateExclusionZones()
		for (plugin in plugins) {
			plugin.registerExclusionZones(exclusionZoneManager)
		}
	}

	fun getExclusionZones(): List<ExclusionZone> {
		return exclusionZoneManager.getExclusionZones()
	}

	fun didExclusionZonesChange(): Boolean {
		return exclusionZoneManager.getHasChanged()
	}
}
