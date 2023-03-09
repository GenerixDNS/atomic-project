package org.atomic.commons.futures

interface IFutureFactory {

    companion object {

        private val default = SimpleFutureFactory()

        @JvmStatic
        public fun getFutureFactory() = default

    }

    public fun handles() : List<Long>

    public fun allocate() : Long

    public fun <T: Any> from(runtime: FutureRuntime) : IFuturePromise<T>

    public fun <T: Any> from() : IFuturePromise<T>

    public fun <T: Any> execute(action: FutureAction<T>) : IFuturePromise<T>

}