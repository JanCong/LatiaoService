package com.izanpin.service.impl;

import com.baidubce.Protocol;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.izanpin.entity.Image;
import com.izanpin.repository.ImageRepository;
import com.izanpin.service.ImageService;
import com.izanpin.common.util.SnowFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

    String endpoint = "https://bj.bcebos.com";
    String ACCESS_KEY_ID = "6c82105cbe4e485788564c32aed7831a";                   // 用户的Access Key ID
    String SECRET_ACCESS_KEY = "6f3c21dcbbf947eeb7a65ea9e6194913";           // 用户的Secret Access Key
    String bucketName = "wuliaoa";

    @Override
    public void addImage(String strUrl, long articleId) {
        String[] urls = strUrl.split("/");
        String objectKey = String.valueOf(new SnowFlake(0, 0).nextId()) + "." + getExtensionName(urls[urls.length - 1]);

        // 初始化一个BosClient
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(endpoint);
        BosClient client = new BosClient(config);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(strUrl).openConnection();
            DataInputStream stream = new DataInputStream(connection.getInputStream());

            PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, stream);
            putObjectFromFileResponse.getETag();

            URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
            String thumbnailUrl = url.toString().replace(objectKey, objectKey + "@!thumbnail");

            Image image = new Image();
            SnowFlake snowFlake = new SnowFlake(0, 0);
            image.setId(snowFlake.nextId());
            image.setArticleId(articleId);
            image.setUrl(url.toString());
            image.setThumbnailUrl(thumbnailUrl);
            image.setCreateTime(new Date());


            imageRepository.add(image);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public Image addImage(MultipartFile file) throws Exception {
        if (file == null) {
            throw new Exception("图片为空");
        }

        String objectKey = String.valueOf(new SnowFlake(0, 0).nextId()) + "." + getExtensionName(file.getOriginalFilename());

        // 初始化一个BosClient
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(endpoint);
        BosClient client = new BosClient(config);

        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file.getBytes());
        putObjectFromFileResponse.getETag();

        URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
        String thumbnailUrl = url.toString().replace(objectKey, objectKey + "@!thumbnail");

        Image img = new Image();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        img.setId(snowFlake.nextId());
        img.setUrl(url.toString());
        img.setThumbnailUrl(thumbnailUrl);
        img.setCreateTime(new Date());
        imageRepository.add(img);

        return img;
    }

    @Override
    public void addImage(MultipartFile image, long articleId) throws Exception {
        if (image == null) {
            throw new Exception("图片为空");
        }

        String objectKey = String.valueOf(new SnowFlake(0, 0).nextId()) + "." + getExtensionName(image.getOriginalFilename());

        // 初始化一个BosClient
        BosClientConfiguration config = new BosClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(endpoint);
        BosClient client = new BosClient(config);

        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, image.getBytes());
        putObjectFromFileResponse.getETag();

        URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
        String thumbnailUrl = url.toString().replace(objectKey, objectKey + "@!thumbnail");

        Image img = new Image();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        img.setId(snowFlake.nextId());
        img.setArticleId(articleId);
        img.setUrl(url.toString());
        img.setThumbnailUrl(thumbnailUrl);
        img.setCreateTime(new Date());
        imageRepository.add(img);
    }

    private String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }
}
