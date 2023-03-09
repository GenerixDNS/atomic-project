package org.atomic.commons.futures

import org.atomic.commons.IHandle
import java.util.Optional

interface IFuturePromise<T> : IHandle {

    /**
     * tells the future thread to wait for a while.
     * This time is given in milliseconds.
     * If the future task is running on the main thread, then this will also wait for a certain amount of time.
     */
    public fun delay(millis: Int) : IFuturePromise<T>

    /**
     * Runs the task asynchronously.
     */
    public fun execute() : IFuturePromise<T>

    public fun join() : IFuturePromise<T>

    public fun get() : Optional<T>

    public fun getNow() : IFuturePromise<T>

    public fun shutdown()

    public fun condition() : FutureCondition

    public fun action(runnable: FutureAction<T>) : IFuturePromise<T>

    public fun reboot()

    public fun promise() : IFuturePromise<T>

    public fun runtime() : FutureRuntime

    public fun enableHandler() : IFuturePromise<T>

    public fun handler() : IFutureHandlerList<T>

}