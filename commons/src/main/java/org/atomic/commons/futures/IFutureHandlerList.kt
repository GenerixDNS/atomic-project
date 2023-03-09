package org.atomic.commons.futures

import java.util.function.Consumer

interface IFutureHandlerList<T> : Iterable<Pair<FutureEvent, Consumer<IFuturePromise<T>>>> {

    public fun future() : IFuturePromise<T>

    public fun on(event: FutureEvent, consumer: Consumer<IFuturePromise<T>>) : IFutureHandlerList<T>

    public fun count() : Int

    public fun flush()

}