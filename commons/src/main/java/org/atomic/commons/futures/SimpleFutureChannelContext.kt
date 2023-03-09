package org.atomic.commons.futures

class SimpleFutureChannelContext<T>(private val meta: IThreadInformation<T>, private val async: Boolean) : IFutureChannelContext<T> {

    override fun meta(): IThreadInformation<T> = this.meta

    override fun isAsynchronous(): Boolean = this.async
}