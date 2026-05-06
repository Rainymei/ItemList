package com.operationpotato.itemlist.utils

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object ThreadUtils {
	val SORTING_EXECUTOR: ExecutorService = Executors.newVirtualThreadPerTaskExecutor()
}
