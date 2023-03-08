package org.atomic.commons.futures;

public interface FutureAction<T> {

    public T run(FutureChannelContext<T> fcc);

    public default T then(Runnable runnable, FutureChannelContext<T> fcc) {
        final T r = run(fcc);
        runnable.run();
        return r;
    }

}
