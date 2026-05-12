package com.operationpotato.itemlist.gui

import com.operationpotato.itemlist.utils.SkyBlockItemCategory
import com.operationpotato.itemlist.utils.SkyBlockItems
import tech.thatgravyboat.skyblockapi.utils.lazy.registryBoundLazy

class EntireListWidget(width: Int, height: Int) : AbstractItemList(width, height) {
	var visibleChildren: List<StackDisplay> = listOf()
	var currentFilter: SkyBlockItemCategory = SkyBlockItemCategory.ALL

	fun filterChildren(category: SkyBlockItemCategory) {
		currentFilter = category
		visibleChildren = if (currentFilter == SkyBlockItemCategory.ALL) {
			children
		} else {
			children.filter { it.type == currentFilter }
		}
	}

	override fun getItems(): List<StackDisplay> {
		if (visibleChildren.isEmpty()) filterChildren(currentFilter)
		return visibleChildren
	}

	companion object {
		private val children: List<StackDisplay> by registryBoundLazy { getItems() }

		fun getItems(): List<StackDisplay> {
			val displays: MutableList<StackDisplay> = mutableListOf()

			SkyBlockItems.items.forEach { (stack, category) ->
				val display = StackDisplay(stack, category)
				displays.add(display)
			}

			return displays
		}
	}
}
