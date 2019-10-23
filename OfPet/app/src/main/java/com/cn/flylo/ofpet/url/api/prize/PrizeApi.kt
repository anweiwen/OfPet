package com.cn.flylo.ofpet.url.api

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface PrizeApi {
    companion object {

        /**
         * 获取奖品分类
         */
        const val getPrizeClassifyList = "pets/prize/getPrizeClassifyList"

        /**
         * 获取奖品列表
         */
        const val getPrizeList = "pets/prize/getPrizeList"


    }
}