package xyz.funnyboy.srb.oss.service;

import java.io.InputStream;

/**
 * 文件上传Service
 *
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 15:20:06
 */
public interface FileService
{
    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param module      模块
     * @param fileName    文件名
     * @return {@link String}
     */
    String uploadFile(InputStream inputStream, String module, String fileName);

    /**
     * 删除文件
     *
     * @param url 网址
     */
    void removeFile(String url);
}
