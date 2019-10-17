package com.cn.ql.frame.tool.log

interface Printer {

    val formatLog: String

    fun init(): XLogConfig

    fun d(message: String, vararg args: Any)

    fun e(message: String, vararg args: Any)

    fun e(throwable: Throwable, message: String, vararg args: Any)

    fun w(message: String, vararg args: Any)

    fun i(message: String, vararg args: Any)

    fun v(message: String, vararg args: Any)

    fun wtf(message: String, vararg args: Any)

    fun json(json: String)

    fun xml(xml: String)

    fun map(map: Map<*, *>)

    fun list(list: List<*>)
}
