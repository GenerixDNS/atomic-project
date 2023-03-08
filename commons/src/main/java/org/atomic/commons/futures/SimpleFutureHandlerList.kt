package org.atomic.commons.futures

import java.util.function.Consumer
import kotlin.collections.ArrayList

class SimpleFutureHandlerList<T>(private val future: FuturePromise<T>) : FutureHandlerList<T> {

    private val handlerList: ArrayList<Pair<Event, Consumer<FuturePromise<T>>>> = ArrayList()

    override fun future(): FuturePromise<T> = this.future

    override fun on(event: Event, consumer: Consumer<FuturePromise<T>>): FutureHandlerList<T> {
        handlerList.add(Pair(event, consumer))
        return this
    }

    override fun count(): Int = this.handlerList.size

    override fun flush() = this.handlerList.clear()

    override fun iterator(): Iterator<Pair<Event, Consumer<FuturePromise<T>>>> {
        return this.handlerList.iterator()
    }

}