package org.atomic.commons.futures

sealed interface ThreadInformation<T> {

    public fun name() : String

    public fun identifier() : Long

    public fun future() : FuturePromise<T>

}