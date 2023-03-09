package org.atomic.commons

interface IOptions : Iterable<IOption> {

    public fun contains(name: String) : IOptions

    public fun contains(option: IOption) : IOptions

    public fun count() : Boolean

    public fun remove(name: String)

    public fun remove(option: IOption)

}