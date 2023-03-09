package org.atomic.commons.futures

class SimpleSingleCoreFuturePool(private val count: Byte) : ISingleCoreFuturePool {

    override fun count(): Byte = this.count

    override fun shutdown(type: FutureShutdownType): ISingleCoreFuturePool {
        TODO("Not yet implemented")
    }

    override fun usage(): Byte {
        TODO("Not yet implemented")
    }

    override fun futures(condition: FutureCondition): Collection<IPooledFuturePromise> {
        TODO("Not yet implemented")
    }

    override fun queue(): Collection<FutureTask<*>> {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<IPooledFuturePromise> {
        TODO("Not yet implemented")
    }
}