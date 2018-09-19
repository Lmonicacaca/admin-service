package com.mbr.admin.common.utils;

import org.apache.commons.lang3.StringUtils;

import javax.activation.MimeType;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 描述:MIME类型判断
 *
 * @outhor liuji
 * @create 2018-06-26 14:24
 */
public final class MimeUtils {

    private static transient final Map<String, String> MIME_TYPE_EXTENSION = new HashMap<String, String>();

    private static transient final String DEFAULT_MIME_TYPE = "application/octet-stream";

    static {
        //Images
        MIME_TYPE_EXTENSION.put("image/gif", "gif");
        MIME_TYPE_EXTENSION.put("image/bmp", "bmp");
        MIME_TYPE_EXTENSION.put("image/x-ms-bmp", "bmp");
        MIME_TYPE_EXTENSION.put("image/x-ico", "ico");
        MIME_TYPE_EXTENSION.put("image/webp", "webp");
        MIME_TYPE_EXTENSION.put("image/jpeg", "jpg");
        MIME_TYPE_EXTENSION.put("image/png", "png");
        MIME_TYPE_EXTENSION.put("image/tiff", "tif");
        MIME_TYPE_EXTENSION.put("image/x-targa", "tga");
        MIME_TYPE_EXTENSION.put("image/vnd.adobe.photoshop", "psd");

        //video
        MIME_TYPE_EXTENSION.put("video/mp4", "mp4");
        MIME_TYPE_EXTENSION.put("video/x-msvideo", "avi");
        MIME_TYPE_EXTENSION.put("video/x-dv", "dv");
        MIME_TYPE_EXTENSION.put("video/mpeg", "mpeg");
        MIME_TYPE_EXTENSION.put("video/quicktime", "mov");
        MIME_TYPE_EXTENSION.put("video/x-ms-wmv", "wm");
        MIME_TYPE_EXTENSION.put("video/x-matroska", "mkv");
        MIME_TYPE_EXTENSION.put("video/x-flv", "flv");
        //audio
        MIME_TYPE_EXTENSION.put("audio/mpeg", "mp3");
        MIME_TYPE_EXTENSION.put("audio/x-wav", "wav");
        MIME_TYPE_EXTENSION.put("audio/mp4", "mp4a");
        MIME_TYPE_EXTENSION.put("audio/ogg", "ogg");
        MIME_TYPE_EXTENSION.put("audio/x-ms-wma", "wma");
        MIME_TYPE_EXTENSION.put("audio/midi", "mid");
        //other
        MIME_TYPE_EXTENSION.put("application/x-gzip", "gz");
        MIME_TYPE_EXTENSION.put("application/x-bzip2", "bz2");
        MIME_TYPE_EXTENSION.put("application/x-tar", "tar");
        MIME_TYPE_EXTENSION.put("application/x-7z-compressed", "7z");
        MIME_TYPE_EXTENSION.put("application/zip", "zip");
        MIME_TYPE_EXTENSION.put("application/x-rar", "rar");
        MIME_TYPE_EXTENSION.put("text/html", "html");
        MIME_TYPE_EXTENSION.put("application/pdf", "pdf");
        MIME_TYPE_EXTENSION.put("application/vnd.ms-powerpoint", "ppt");
        MIME_TYPE_EXTENSION.put("application/vnd.openxmlformats-officedocument.presentationml.presentation", "pptx");
        MIME_TYPE_EXTENSION.put("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "xlsx");
        MIME_TYPE_EXTENSION.put("application/vnd.ms-excel", "xls");
        MIME_TYPE_EXTENSION.put("application/msword", "doc");
        MIME_TYPE_EXTENSION.put("application/vnd.openxmlformats-officedocument.wordprocessingml.document", "docx");
        MIME_TYPE_EXTENSION.put("text/plain", "txt");
        MIME_TYPE_EXTENSION.put("application/x-javascript", "js");
        // default tmp file
        MIME_TYPE_EXTENSION.put("application/octet-stream", "");
    }

    /**
     * 根据文件MIME类型获取后缀
     *
     * @param mimeType
     * @return
     */
    public static final String getFileExtension(String mimeType) {
        if (StringUtils.isEmpty(mimeType)) {
            return MIME_TYPE_EXTENSION.get(DEFAULT_MIME_TYPE);
        }
        return Optional.ofNullable(MIME_TYPE_EXTENSION.get(mimeType.toLowerCase())).orElse(MIME_TYPE_EXTENSION.get
                (DEFAULT_MIME_TYPE));
    }

    /**
     * 根据文件MIME类型获取后缀
     *
     * @param mimeType
     * @return
     */
    public static final String getFileExtension(MimeType mimeType) {
        return Optional.ofNullable(mimeType).map(e -> getFileExtension(e.getBaseType())).orElse(getFileExtension(""));
    }


    public static final boolean isVideo(String mimeType) {
        return is(mimeType, "video");
    }


    public static final boolean isImage(String mimeType) {

        return is(mimeType, "image");
    }


    public static final boolean isAudio(String mimeType) {
        return is(mimeType, "audio");
    }


    /**
     * @param mimeType
     * @param primaryType
     * @return
     */
    public static final boolean is(MimeType mimeType, String primaryType) {
        if (mimeType != null) {
            return is(mimeType.getBaseType(), primaryType);
        }
        return false;
    }

    /**
     * 判断mime的主类型application、audio、video
     *
     * @param mimeType
     * @param primaryType
     * @return
     */
    public static final boolean is(String mimeType, String primaryType) {
        if (!StringUtils.isEmpty(mimeType)) {
            String pri = mimeType.split("/")[0];
            return pri.equalsIgnoreCase(primaryType);
        }
        return false;
    }
}
