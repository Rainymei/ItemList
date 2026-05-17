package com.operationpotato.itemlist.utils

import net.minecraft.client.renderer.Rect2i

object Utils {

	val Rect2i.right: Int
		get() = this.x + this.width

	val Rect2i.bottom: Int
		get() = this.y + this.height

	fun Rect2i.overlaps(other: Rect2i): Boolean {
		return this.x < other.right && this.right > other.x
			&& this.y < other.bottom && this.bottom > other.y
	}
}
