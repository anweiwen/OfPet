package com.cn.ql.frame.tool.gson

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken

import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.ArrayList


/**
 * Created by AXW on 2018/3/28.
 * 注：GSON工具类
 */

object GsonTool {

    fun <T> getGson(json: String, parentClass: Class<T>, childClass: Class<*>, type: Int): T? {
        return if (type == 0) {
            getBean<T>(json, parentClass, childClass)
        } else {
            getListBean<T>(json, parentClass, childClass)
        }
    }

    fun <T> getBean(json: String, cls: Class<*>, clazz: Class<*>): T? {
        val jsonType = type(cls, clazz)
        return Gson().fromJson<T>(json, jsonType)
    }

    fun <T> getBean(json: String, cls: Class<T>): T {
        return Gson().fromJson(json, cls)
    }

    fun <T> getListBean(json: String, cla: Class<*>, clazz: Class<*>): T? {
        val gson = Gson()
        val objectType = type(cla, clazz)
        return gson.fromJson<T>(json, objectType)
    }

    fun <T> getListBean(json: String, cla: Class<T>): T? {
        val gson = Gson()
        val objectType = type(cla)
        return gson.fromJson<T>(json, objectType)
    }

    fun <T> getList(json: String, cls: Class<T>): List<T> {
        val gson = Gson()
        val list = ArrayList<T>()
        val array = JsonParser().parse(json).asJsonArray
        for (elem in array) {
            list.add(gson.fromJson(elem, cls))
        }
        return list
    }

    fun <T> getMapList(json: String): List<Map<String, T>>? {
        var list: List<Map<String, T>>? = null
        val gson = Gson()
        list = gson.fromJson<List<Map<String, T>>>(json, object : TypeToken<List<Map<String, T>>>() {

        }.type)
        return list
    }


    fun toJSONString(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj)
    }

    private fun type(raw: Class<*>, vararg args: Type): ParameterizedType {
        return object : ParameterizedType {
            override fun getRawType(): Type {
                return raw
            }

            override fun getActualTypeArguments(): Array<out Type> {
                return args
            }

            override fun getOwnerType(): Type? {
                return null
            }
        }
    }

}
