package com.cn.flylo.ofpet.url.api

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface AccountApi {
    companion object {
        /**
         * 登录
         */
        const val login = "api/user/login"

        /**
         * 获取验证码
         */
        const val getCode = "api/user/getCode"

        /**
         * 注册接口
         */
        const val register = "api/user/register"

        /**
         * 找回密码
         */
        const val findPwd = "api/doctor/findPwd"

        /**
         * 退出登录
         */
        const val loginOut = "api/user/loginOut"

    }
}