package com.cn.ql.photo.media.type;

import android.media.MediaMetadataRetriever;
import android.text.TextUtils;

import java.io.File;

public final class MimeType {

    public static int ofAll() {
        return MediaType.ALL.get();
    }

    public static int ofImage() {
        return MediaType.IMAGE.get();
    }

    public static int ofVideo() {
        return MediaType.VIDEO.get();
    }

    public static int ofAudio() {
        return MediaType.AUDIO.get();
    }

    public static int getFileType(String mimeType) {

        switch (mimeType) {
            case "image/png":
            case "image/PNG":
            case "image/jpeg":
            case "image/JPEG":
            case "image/webp":
            case "image/WEBP":
            case "image/gif":
            case "image/GIF":
                return MediaType.IMAGE.get();
            case "video/3gp":
            case "video/3gpp":
            case "video/avi":
            case "video/mp4":
            case "video/x-msvideo":
                return MediaType.VIDEO.get();
            case "audio/mpeg":
            case "audio/x-ms-wma":
            case "audio/x-wav":
            case "audio/amr":
            case "audio/wav":
            case "audio/aac":
            case "audio/mp4":
            case "audio/quicktime":
                return MediaType.AUDIO.get();
            default:
                break;
        }
        return MediaType.IMAGE.get();
    }

    public static boolean isGif(String mimeType) {

        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }

        switch (mimeType) {
            case "image/gif":
            case "image/GIF":
                return true;
            default:
                break;
        }
        return false;
    }

    public static boolean isVideo(String mimeType) {

        if (TextUtils.isEmpty(mimeType)) {
            return false;
        }

        switch (mimeType) {
            case "video/3gp":
            case "video/3gpp":
            case "video/avi":
            case "video/mp4":
            case "video/x-msvideo":
                return true;
            default:
                break;
        }
        return false;
    }

    public static boolean isHttp(String path) {
        if (!TextUtils.isEmpty(path)) {
            if (path.startsWith("http")
                    || path.startsWith("https")) {
                return true;
            }
        }
        return false;
    }

    public static String fileToType(File file) {
        if (file != null) {
            String name = file.getName();
            if (name.endsWith(".mp4") || name.endsWith(".avi")
                    || name.endsWith(".3gpp") || name.endsWith(".3gp")) {
                return "video/mp4";
            } else if (name.endsWith(".PNG") || name.endsWith(".png") || name.endsWith(".jpeg")
                    || name.endsWith(".gif") || name.endsWith(".GIF") || name.endsWith(".jpg")
                    || name.endsWith(".webp") || name.endsWith(".WEBP") || name.endsWith(".JPEG")) {
                return "image/jpeg";
            } else if (name.endsWith(".mp3") || name.endsWith(".amr")
                    || name.endsWith(".aac") || name.endsWith(".war")
                    || name.endsWith(".flac")) {
                return "audio/mpeg";
            }
        }
        return "image/jpeg";
    }

    public static boolean mimeToEqual(String p1, String p2) {
        return getFileType(p1) == getFileType(p2);
    }

    public static String createImageType(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                String fileName = file.getName();
                int last = fileName.lastIndexOf(".") + 1;
                String temp = fileName.substring(last, fileName.length());
                return "image/" + temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "image/jpeg";
        }
        return "image/jpeg";
    }

    public static String createVideoType(String path) {
        try {
            if (!TextUtils.isEmpty(path)) {
                File file = new File(path);
                String fileName = file.getName();
                int last = fileName.lastIndexOf(".") + 1;
                String temp = fileName.substring(last, fileName.length());
                return "video/" + temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "video/mp4";
        }
        return "video/mp4";
    }

    public static int pictureToVideo(String mimeType) {
        if (!TextUtils.isEmpty(mimeType)) {
            if (mimeType.startsWith("video")) {
                return MediaType.VIDEO.get();
            } else if (mimeType.startsWith("audio")) {
                return MediaType.AUDIO.get();
            }
        }
        return MediaType.IMAGE.get();
    }

    public static int getLocalVideoDuration(String videoPath) {
        int duration;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(videoPath);
            duration = Integer.parseInt(mmr.extractMetadata
                    (MediaMetadataRetriever.METADATA_KEY_DURATION));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return duration;
    }
}