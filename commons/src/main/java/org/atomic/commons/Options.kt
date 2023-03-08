package org.atomic.commons

interface Options : Iterable<Option> {

    public fun contains(name: String) : Options

    public fun contains(option: Option) : Options

    public fun count() : Boolean

    public fun remove(name: String)

    public fun remove(option: Option)

}