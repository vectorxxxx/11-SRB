package xyz.funnyboy.srb.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import xyz.funnyboy.srb.oss.service.FileService;
import xyz.funnyboy.srb.oss.util.OssProperties;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author VectorX
 * @version V1.0
 * @date 2024-01-24 15:21:07
 */
@Service
public class FileServiceImpl implements FileService
{
    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param module      模块
     * @param fileName    文件名
     * @return {@link String}
     */
    @Override
    public String uploadFile(InputStream inputStream, String module, String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);
        // 判断oss实例是否存在：如果不存在则创建，如果存在则获取
        if (!ossClient.doesBucketExist(OssProperties.BUCKET_NAME)) {
            //创建bucket
            ossClient.createBucket(OssProperties.BUCKET_NAME);
            //设置oss实例的访问权限：公共读
            ossClient.setBucketAcl(OssProperties.BUCKET_NAME, CannedAccessControlList.PublicRead);
        }

        // 构建日期路径：avatar/2024/01/24/文件名
        final String folder = new DateTime().toString("yyyy/MM/dd");

        // 文件名：uuid.扩展名
        fileName = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf("."));

        // 文件根路径
        final String key = module + "/" + folder + "/" + fileName;

        // 文件上传至阿里云
        ossClient.putObject(OssProperties.BUCKET_NAME, key, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();

        //阿里云文件绝对路径
        return "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT + "/" + key;
    }

    /**
     * 删除文件
     *
     * @param url 网址
     */
    @Override
    public void removeFile(String url) {
        // 创建OSSClient实例。
        final OSS client = new OSSClientBuilder().build(OssProperties.ENDPOINT, OssProperties.KEY_ID, OssProperties.KEY_SECRET);

        // 文件名（服务器上的文件路径）
        String host = "https://" + OssProperties.BUCKET_NAME + "." + OssProperties.ENDPOINT + "/";
        String fileName = url.substring(host.length());

        // 删除文件。
        client.deleteObject(OssProperties.BUCKET_NAME, fileName);

        // 关闭OSSClient。
        client.shutdown();
    }
}
