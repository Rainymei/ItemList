package com.operationpotato.itemlist.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

object ThreadUtils {
	val SORTING_EXECUTOR: ExecutorService = Executors.newVirtualThreadPerTaskExecutor()

	fun ExecutorService.cancelAndSubmit(existingFuture: Future<*>?, task: Runnable): Future<*>? {
		if (existingFuture != null && !existingFuture.isDone) existingFuture.cancel(true)
		return this.submit(task)
	}
}
