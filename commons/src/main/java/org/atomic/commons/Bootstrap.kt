package org.atomic.commons

import java.io.File
import java.lang.management.ManagementFactory

class Bootstrap {
    private val operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean()
    private val libraries = arrayOf("core")
    private var loaded = false
    private val resources: File = File(Bootstrap::class.java.classLoader.getResource(".")!!.file + "/libraries")

    fun loadLibraries() {
        if (!loaded) {
            if (operatingSystemMXBean.name.contains("windows", ignoreCase = true)) {
                for (item in libraries) {
                    Runtime.getRuntime().load(path(item, "dll").absolutePath)
                }
                loaded = true
            } else {
                if (operatingSystemMXBean.name.equals("linux", ignoreCase = true)) {
                    for (item in libraries) {
                        Runtime.getRuntime().load(path(item, "so").absolutePath)
                    }
                    loaded = true
                }
            }
        } else throw UnsupportedOperationException("This operation cannot execute two times!")
    }

    private fun path(name: String, s: String): File = File(resources.absolutePath + "/" + "lib" + name + "_port." + s)

    companion object {
        fun enable() {
            val b = Bootstrap()
            b.loadLibraries()
        }
    }
}