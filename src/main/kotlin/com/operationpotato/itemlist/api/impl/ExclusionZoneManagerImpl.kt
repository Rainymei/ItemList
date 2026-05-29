package com.operationpotato.itemlist.api.impl

import com.operationpotato.itemlist.api.ExclusionZone
import com.operationpotato.itemlist.api.ExclusionZoneManager
import com.operationpotato.itemlist.api.ExclusionZoneProvider
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import net.minecraft.client.gui.screens.Screen
import org.jetbrains.annotations.ApiStatus
import tech.thatgravyboat.skyblockapi.helpers.McScreen
import java.util.*

@ApiStatus.Internal
class ExclusionZoneManagerImpl : ExclusionZoneManager {
	private var providers: Object2ObjectOpenHashMap<ExclusionZoneProvider<Screen?>, Class<Screen?>> = Object2ObjectOpenHashMap()

	private var exclusionZones: MutableList<ExclusionZone> = mutableListOf()
	private var previous: List<ExclusionZone> = listOf()

	private var previousHash = previous.hashCode()
	private var hasChanged: Boolean = false

	override fun <T : Screen> addProvider(screenClass: Class<T>, provider: ExclusionZoneProvider<T>) {
		@Suppress("UNCHECKED_CAST")
		providers[provider as ExclusionZoneProvider<Screen?>] = screenClass as Class<Screen?>
	}

	fun getExclusionZones(): List<ExclusionZone> {
		return exclusionZones
	}

	fun calculateExclusionZones() {
		clearExclusionZones()
		val currentScreen = McScreen.self ?: return
		providers.forEach { (provider, screenClass) ->
			if (screenClass.isInstance(currentScreen)) {
				val zones = provider.provide(screenClass.cast(currentScreen))
				zones.forEach { exclusionZones.add(ExclusionZone(it)) }
			}
		}
		hasChanged = previousHash != hash(exclusionZones)
	}

	fun hash(list: List<ExclusionZone>): Int {
		var hash = 17
		list.forEach { zone ->
			val rect = zone.area
			val zoneHash = Objects.hash(rect.x, rect.y, rect.width, rect.height)
			hash = 31 * hash + zoneHash
		}
		return hash
	}

	fun clearExclusionZones() {
		previous = exclusionZones
		previousHash = hash(previous)
		exclusionZones = mutableListOf()
	}

	fun getHasChanged(): Boolean {
		return hasChanged
	}
}
