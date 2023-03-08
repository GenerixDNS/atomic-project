package org.atomic.commons.futures

import java.util.function.Consumer

interface FutureHandlerList<T> : Iterable<Pair<Event, Consumer<FuturePromise<T>>>> {

    public fun future() : FuturePromise<T>

    public fun on(event: Event, consumer: Consumer<FuturePromise<T>>) : FutureHandlerList<T>

    public fun count() : Int

    public fun flush()

}