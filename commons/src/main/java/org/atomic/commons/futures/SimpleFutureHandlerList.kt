package org.atomic.commons.futures

import java.util.function.Consumer
import kotlin.collections.ArrayList

class SimpleFutureHandlerList<T>(private val future: IFuturePromise<T>) : IFutureHandlerList<T> {

    private val handlerList: ArrayList<Pair<FutureEvent, Consumer<IFuturePromise<T>>>> = ArrayList()

    override fun future(): IFuturePromise<T> = this.future

    override fun on(event: FutureEvent, consumer: Consumer<IFuturePromise<T>>): IFutureHandlerList<T> {
        handlerList.add(Pair(event, consumer))
        return this
    }

    override fun count(): Int = this.handlerList.size

    override fun flush() = this.handlerList.clear()

    override fun iterator(): Iterator<Pair<FutureEvent, Consumer<IFuturePromise<T>>>> {
        return this.handlerList.iterator()
    }

}