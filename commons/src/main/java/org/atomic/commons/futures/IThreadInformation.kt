package org.atomic.commons.futures

sealed interface IThreadInformation<T> {

    public fun name() : String

    public fun identifier() : Long

    public fun future() : IFuturePromise<T>

}