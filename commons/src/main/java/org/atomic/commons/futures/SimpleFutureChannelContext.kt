package org.atomic.commons.futures

class SimpleFutureChannelContext<T>(private val meta: ThreadInformation<T>, private val async: Boolean) : FutureChannelContext<T> {

    override fun meta(): ThreadInformation<T> = this.meta

    override fun isAsynchronous(): Boolean = this.async
}