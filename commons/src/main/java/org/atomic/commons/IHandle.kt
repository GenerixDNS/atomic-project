package org.atomic.commons

interface IHandle {

    /**
     * returns the identification number of the internal thread,
     * this number will be reset as soon as this future is cleared and might appear again by accident.
     */
    public fun handle() : Long

}