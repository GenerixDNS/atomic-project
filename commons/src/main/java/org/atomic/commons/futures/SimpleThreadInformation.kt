package org.atomic.commons.futures

class SimpleThreadInformation<T>(private val handle: Long, private val future: IFuturePromise<T>) : IThreadInformation<T> {

    override fun name(): String {
        return if (this.handle.toInt() != 0) {
            (future as OSFuturePromiseImpl).n(this.handle)
        } else Thread.currentThread().name
    }

    override fun identifier(): Long {
        return if (this.handle.toInt() != 0) {
            (future as OSFuturePromiseImpl).i(this.handle).toLong()
        } else Thread.currentThread().id
    }

    override fun future(): IFuturePromise<T> = this.future

}