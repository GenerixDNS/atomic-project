package org.atomic.commons.futures

interface ISingleCoreFuturePool : Iterable<IPooledFuturePromise> {

    public fun count() : Byte

    public fun shutdown(type: FutureShutdownType) : ISingleCoreFuturePool

    public fun usage() : Byte

    public fun futures(condition: FutureCondition) : Collection<IPooledFuturePromise>

    public fun queue() : Collection<FutureTask<*>>

}