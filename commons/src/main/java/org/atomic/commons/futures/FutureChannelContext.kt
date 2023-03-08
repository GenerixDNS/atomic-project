package org.atomic.commons.futures

interface FutureChannelContext<T> {

    public fun meta() : ThreadInformation<T>

    public fun isAsynchronous() : Boolean

}