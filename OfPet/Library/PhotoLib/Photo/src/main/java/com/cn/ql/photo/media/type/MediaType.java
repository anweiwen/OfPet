package com.cn.ql.photo.media.type;

/**
 * @author axw_an
 * @create on 2019/3/19
 * @refer url：
 * @description:
 * @update: axw_an:2019/3/19:
 */
public enum MediaType {
    ALL(0x01),AUDIO(0x02),IMAGE(0x03),VIDEO(0x04),VIDEO_IMAGE(0x05);

    int value;
    MediaType(int value) {
        this.value = value;
    }

    public int get() {
        return value;
    }

    public static MediaType matchOpCode(int i) {
        for (MediaType opCode : MediaType.values()) {
            if (opCode.get() == i) {
                return opCode;
            }
        }
        return MediaType.ALL;
    }

}
