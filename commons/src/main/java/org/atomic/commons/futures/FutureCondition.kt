package org.atomic.commons.futures

enum class FutureCondition {

    NOT_STARTED,

    PREPARING,

    RUNNING,

    RUNNING_SYNC,

    FINISHED,

    FLUSHED,

}