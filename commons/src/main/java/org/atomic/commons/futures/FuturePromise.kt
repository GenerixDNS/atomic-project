package org.atomic.commons.futures

import java.util.Optional

interface FuturePromise<T> {

    public fun handle() : Long

    public fun delay(millis: Int) : FuturePromise<T>

    public fun execute() : FuturePromise<T>

    public fun join() : FuturePromise<T>

    public fun get() : Optional<T>

    public fun getNow() : FuturePromise<T>

    public fun shutdown()

    public fun condition() : FutureCondition

    public fun action(runnable: FutureAction<T>) : FuturePromise<T>

    public fun reboot()

    public fun promise() : FuturePromise<T>

    public fun runtime() : FutureRuntime

}