package org.atomic.commons.futures

interface IFutureChannelContext<T> {

    public fun meta() : IThreadInformation<T>

    public fun isAsynchronous() : Boolean

}