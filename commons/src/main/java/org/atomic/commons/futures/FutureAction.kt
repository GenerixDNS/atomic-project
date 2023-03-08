package org.atomic.commons.futures

interface FutureAction<T> {

    public fun run(fcc: FutureChannelContext<T>) : T

}