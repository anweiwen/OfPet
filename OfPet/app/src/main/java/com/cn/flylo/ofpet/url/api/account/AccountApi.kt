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
        const val pLogin = "pets/user/pLogin"

        /**
         * 获取验证码
         */
        const val sendVcode = "pets/user/sendVcode"

        /**
         * 注册接口
         */
        const val pResgist = "pets/user/pResgist"

        /**
         * 找回密码
         */
        const val resetPwd = "pets/user/resetPwd"

        /**
         * 退出登录
         */
        const val loginOut = "api/user/loginOut"

    }
}