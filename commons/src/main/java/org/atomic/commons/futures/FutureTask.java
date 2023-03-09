package org.atomic.commons.futures;

public interface FutureTask<T> {

    public T run(IFutureChannelContext<T> fcc);

    public default T then(Runnable runnable, IFutureChannelContext<T> fcc) {
        final T r = run(fcc);
        runnable.run();
        return r;
    }

}
