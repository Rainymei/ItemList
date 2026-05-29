package com.operationpotato.itemlist.api.impl

import com.operationpotato.itemlist.api.HoveredItemConsumer
import com.operationpotato.itemlist.api.HoveredItemManager
import com.operationpotato.itemlist.api.HoveredItemProvider
import net.minecraft.client.gui.screens.Screen
import net.minecraft.client.input.KeyEvent
import net.minecraft.world.item.ItemStack
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class HoveredItemManagerImpl : HoveredItemManager {
	val providers: MutableList<HoveredItemProvider> = mutableListOf()
	val consumers: MutableList<HoveredItemConsumer> = mutableListOf()

	override fun addProvider(provider: HoveredItemProvider) {
		providers.add(provider)
	}

	override fun addConsumer(consumer: HoveredItemConsumer) {
		consumers.add(consumer)
	}

	fun getHoveredItem(screen: Screen): ItemStack? {
		providers.forEach { provider ->
			val stack = provider.provide(screen)
			if (stack != null) return stack
		}
		return null
	}

	fun provideHoveredItem(screen: Screen, stack: ItemStack, keyEvent: KeyEvent): Boolean {
		consumers.forEach { consumer ->
			val bl = consumer.consume(screen, stack, keyEvent)
			if (bl) return true
		}
		return false
	}
}
