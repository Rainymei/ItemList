package com.operationpotato.itemlist.utils

import net.minecraft.network.chat.Component

object ComponentUtils {
	fun getCycleEnumOptions(selected: SkyBlockItemCategory): List<Component> {
		val values = SkyBlockItemCategory.entries.toTypedArray()
		val lines = mutableListOf<Component>()
		val start = values.sliceArray(selected.ordinal + 1 until values.size)
		val remaining = values.sliceArray(0 until selected.ordinal)
		start.forEach { lines.add(it.asComponent()) }
		remaining.forEach { lines.add(it.asComponent()) }
		return lines
	}

	fun joinComponents(components: List<Component>, separator: String = "\n"): Component {
		var component = Component.empty()
		components.forEach { component = component.append(separator).append(it) }
		return component
	}
}
