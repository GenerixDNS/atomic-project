package org.atomic.commons.futures

import java.util.Random

class SimpleFutureFactory : FutureFactory {

    companion object {
        private val generator = Random()
    }

    private val handles: List<Long> = listOf()

    override fun handles(): List<Long> = this.handles

    override fun allocate(): Long {
        val random : () -> Long = { generator.nextLong() }
        var r = random()
        while (handles().contains(r)) {
            r = random()
        }
        return r
    }

    override fun <T : Any> from(runtime: FutureRuntime): FuturePromise<T> {
        if (runtime == FutureRuntime.SIMPLE_OS) {
            return OSFuturePromiseImpl(0)
        }
        TODO("Not yet implemented")
    }

    override fun <T: Any> from(): FuturePromise<T> = OSFuturePromiseImpl(0)

    override fun <T: Any> execute(action: FutureAction<T>): FuturePromise<T> = from<T>().action(action).execute()


}