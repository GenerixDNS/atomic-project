@file:Suppress("ReplaceCallWithBinaryOperator", "DuplicatedCode")

package org.atomic.commons.futures

import java.lang.NullPointerException
import java.lang.UnsupportedOperationException
import java.util.Optional

class OSFuturePromiseImpl<T : Any>(private var futureDelay: Int) : IFuturePromise<T> {

    private val handle = IFutureFactory.getFutureFactory().allocate()
    private lateinit var runnable: FutureTask<T>
    private var condition = FutureCondition.NOT_STARTED
    private var storage : Optional<T> = Optional.empty()
    private var handler: Optional<IFutureHandlerList<T>> = Optional.empty();

    private external fun e(handle: Long, d: Int, runnable: IPrimitiveRunnable<T>)
    private external fun d(handle: Long, runnable: IPrimitiveRunnable<T>)
    private external fun c(handle: Long) : Short
    private external fun j(handle: Long) : T
    private external fun s()
    private external fun a(runnable: Runnable)

    internal external fun n(handle: Long) : String
    internal external fun i(handle: Long) : Int

    companion object {
        private var s: Boolean = false;
    }

    init {
        if (!s) {
            s();
            s = !s;
        }
    }

    override fun handle(): Long = this.handle

    override fun delay(millis: Int): IFuturePromise<T> {
        this.futureDelay = millis;
        return this
    }

    override fun promise(): IFuturePromise<T> = this

    override fun runtime(): FutureRuntime = FutureRuntime.SIMPLE_OS

    override fun enableHandler(): IFuturePromise<T> {
        this.handler = Optional.of(SimpleFutureHandlerList(this))
        return this
    }

    override fun handler(): IFutureHandlerList<T> = this.handler.get()

    private fun t() {
        this.handler.get().forEach { item ->
            if (item.first.equals(FutureEvent.ON_ASYNC_FINISH) || item.first.equals(FutureEvent.ON_FINISH))
                item.second.accept(this)
        }
    }

    override fun execute(): IFuturePromise<T> {
        if (this.condition != FutureCondition.FLUSHED) {
            if (this.futureDelay == 0) {
                this.condition = FutureCondition.PREPARING
                if (this.handler.isPresent)
                    this.handler.get().forEach { item ->
                        if (item.first.equals(FutureEvent.ON_BOOT))
                            item.second.accept(this)
                    }
                this.d(this.handle(), object : IPrimitiveRunnable<T> {
                    override fun run(): T {
                        return if (!handler.isPresent)
                            runnable.run(SimpleFutureChannelContext(SimpleThreadInformation(handle, promise()), true))
                        else {
                            runnable.then({ t() } ,SimpleFutureChannelContext(SimpleThreadInformation(handle, promise()), true))
                        }
                    }
                })
                return this
            } else {
                if (this.futureDelay > 0) {
                    this.condition = FutureCondition.PREPARING
                    if (this.handler.isPresent)
                        this.handler.get().forEach { item ->
                            if (item.first.equals(FutureEvent.ON_BOOT))
                                item.second.accept(this)
                        }
                    this.e(this.handle(), this.futureDelay, object : IPrimitiveRunnable<T> {
                        override fun run(): T {
                            return if (!handler.isPresent)
                                runnable.run(SimpleFutureChannelContext(SimpleThreadInformation(handle, promise()), true))
                            else runnable.then({ t() } ,SimpleFutureChannelContext(SimpleThreadInformation(handle, promise()), true))
                        }
                    })
                    return this
                }
                throw UnsupportedOperationException("it is not possible to execute a task with a negative delay value!")
            }
        }
        return this
    }

    override fun join(): IFuturePromise<T> {
        if (this.condition() == FutureCondition.RUNNING || this.condition() == FutureCondition.FINISHED) {
            this.storage = Optional.of(this.j(this.handle))
            this.condition = FutureCondition.FLUSHED
        }
        return this
    }

    override fun get(): Optional<T> = this.storage

    override fun getNow(): IFuturePromise<T> {
        this.condition = FutureCondition.RUNNING_SYNC
        if (this.futureDelay == 0) {
            if (this.handler.isPresent)
                this.handler.get().forEach { item ->
                    if (item.first.equals(FutureEvent.ON_BOOT))
                        item.second.accept(this)
                }
            this.storage = Optional.of(
                    this.runnable.run(
                            SimpleFutureChannelContext(
                                    SimpleThreadInformation(
                                            0, this
                                    ), false)
                    )
            )
            this.condition = FutureCondition.FINISHED
            if (this.handler.isPresent)
                this.handler.get().forEach { item ->
                    if (item.first.equals(FutureEvent.ON_SYNC_FINISH) || item.first.equals(FutureEvent.ON_FINISH))
                        item.second.accept(this)
                }
            return this
        }
        if (this.futureDelay > 0) {
            if (this.handler.isPresent)
                this.handler.get().forEach { item ->
                    if (item.first.equals(FutureEvent.ON_BOOT))
                        item.second.accept(this)
                }
            Thread.sleep(this.futureDelay.toLong())
            this.storage = Optional.of(
                    this.runnable.run(
                            SimpleFutureChannelContext(
                                    SimpleThreadInformation(
                                            0, this
                                    ), false)
                    )
            )
            this.condition = FutureCondition.FINISHED
            if (this.handler.isPresent)
                this.handler.get().forEach { item ->
                    if (item.first.equals(FutureEvent.ON_SYNC_FINISH) || item.first.equals(FutureEvent.ON_FINISH))
                        item.second.accept(this)
                }
            return this
        } else throw UnsupportedOperationException("it is not possible to execute a task with a negative delay value!")
    }

    override fun shutdown() {
        if (this.condition == FutureCondition.RUNNING) {
            TODO("Not yet implemented")
        }
    }

    override fun condition(): FutureCondition {
        if (this.condition == FutureCondition.PREPARING) {
            return try {
                val r = c(this.handle);

                if (r == 1.toShort()) {
                    this.condition = FutureCondition.FINISHED
                    FutureCondition.FINISHED
                } else {
                    FutureCondition.RUNNING
                }
            } catch (exc: NullPointerException) {
                FutureCondition.PREPARING
            }
        } else {
            return this.condition
        }
    }

    override fun reboot() {
        TODO("Not yet implemented")
    }

    override fun action(runnable: FutureTask<T>) : IFuturePromise<T> {
        this.runnable = runnable
        return this
    }

}