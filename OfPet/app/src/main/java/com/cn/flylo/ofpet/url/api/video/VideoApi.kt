package com.cn.flylo.ofpet.url.api

/**
 * @author axw_an
 * @create on 2019/5/26
 * @refer url：
 * @description:
 * @update: axw_an:2019/5/18:
 */
interface VideoApi {
    companion object {
        /**
         *
         */
        const val getFollowVideos = "pets/video/getFollowVideos"

        /**
         * 获取热门视频
         */
        const val getPopularVideo = "pets/video/getPopularVideo"

        /**
         * 获取一条视频信息
         */
        const val getVideo = "pets/video/getVideo"

        /**
         * 获取视频评论列表
         */
        const val getVideoDisList = "pets/video/getVideoDisList"

        /**
         * 评论点赞
         */
        const val saveInfoDispra = "pets/video/saveInfoDispra"

        /**
         * 保存编辑视频评论
         */
        const val saveVideoDiscuss = "pets/video/saveVideoDiscuss"

        /**
         * 保存编辑子评论
         */
        const val saveChildDis = "pets/video/saveChildDis"

        /**
         * 视频点赞
         */
        const val videoPraise = "pets/video/videoPraise"

        /**
         * 获取最新/闲置视频列表
         */
        const val getNewVideos = "pets/video/getNewVideos"

        /**
         * 子评论点赞
         */
        const val saveChildDispra = "pets/video/saveChildDispra"



    }
}