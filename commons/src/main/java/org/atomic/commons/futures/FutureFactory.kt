package org.atomic.commons.futures

interface FutureFactory {

    companion object {

        private val default = SimpleFutureFactory()

        @JvmStatic
        public fun getFutureFactory() = default

    }

    public fun handles() : List<Long>

    public fun allocate() : Long

    public fun <T: Any> from(runtime: FutureRuntime) : FuturePromise<T>

    public fun <T: Any> from() : FuturePromise<T>

    public fun <T: Any> execute(action: FutureAction<T>) : FuturePromise<T>

}