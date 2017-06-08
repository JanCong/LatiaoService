package com.izanpin.service.impl;

import com.baidubce.BceClientConfiguration;
import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.http.RetryPolicy;
import com.baidubce.services.bos.BosClient;
import com.baidubce.services.bos.BosClientConfiguration;
import com.baidubce.services.bos.model.PutObjectResponse;
import com.baidubce.services.media.MediaClient;
import com.baidubce.services.media.model.GetThumbnailJobResponse;
import com.izanpin.common.util.ThumbnailJob;
import com.izanpin.entity.Image;
import com.izanpin.repository.ImageRepository;
import com.izanpin.service.ImageService;
import com.izanpin.common.util.SnowFlake;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.processing.OperationManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import com.qiniu.util.UrlSafeBase64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Smart on 2017/1/30.
 */
@Service
@Transactional
public class ImageServiceImpl implements ImageService {
    @Autowired
    ImageRepository imageRepository;

//    String endpoint = "https://gz.bcebos.com";
//    //    String ACCESS_KEY_ID = "6c82105cbe4e485788564c32aed7831a";                   // 用户的Access Key ID
//    String ACCESS_KEY_ID = "563a853a0b394b67b10bcb82a301b867";                   // 用户的Access Key ID
//    //    String SECRET_ACCESS_KEY = "6f3c21dcbbf947eeb7a65ea9e6194913";           // 用户的Secret Access Key
//    String SECRET_ACCESS_KEY = "dc1365f93fe94ab8bad3d8883ae1e0b8";           // 用户的Secret Access Key
//    String bucketName = "latiao1";

    String ACCESS_KEY = "c_dDAms5xIs12Vs9_uWK651hk4aJiXOs_MkZULUo";
    String SECRET_KEY = "JEA2Q5_cUfX_JGNN9ft_ft8apmMiOeVg7-j7fhSn";
    String BUCKET = "latiao";
    String DOMAIN = "http://storage.izanpin.com";

    static Logger logger = LogManager.getLogger();

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void addImage(String strUrl, Long articleId) {
        String[] urls = strUrl.split("/");
        String objectKeyId = sdf.format(new Date()) + "/" + String.valueOf(new SnowFlake(0, 0).nextId());
        String extName = getExtensionName(urls[urls.length - 1]);
        String objectKey = objectKeyId + "." + extName;
        objectKey = objectKey.toLowerCase();

        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(strUrl).openConnection();
            DataInputStream stream = new DataInputStream(connection.getInputStream());


            Response response = uploadManager.put(stream, objectKey, upToken, null, null);

//            URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
            String finalUrl = String.format("%s/%s", DOMAIN, objectKey);

            String thumbnailUrl = finalUrl.replace(objectKey, objectKey + "-thumbnail");

            Image image = new Image();
            SnowFlake snowFlake = new SnowFlake(0, 0);
            image.setId(snowFlake.nextId());
            image.setArticleId(articleId);
            image.setUrl(finalUrl);
            image.setThumbnailUrl(thumbnailUrl);
            image.setIsVideo(objectKey.toLowerCase().contains("mp4"));
            image.setCreateTime(new Date());

            if (image.getIsVideo()) {
                //数据处理指令，支持多个指令
                String saveJpgEntry = String.format("%s:%s.jpg", BUCKET, objectKeyId);
                String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));

                OperationManager operationManager = new OperationManager(auth, cfg);
                operationManager.pfop(BUCKET, objectKey, vframeJpgFop, "latiao", true);

                image.setThumbnailUrl(finalUrl.replace(extName, "jpg-thumbnail"));
            } else {
                image.setThumbnailUrl(finalUrl.replace(objectKey, objectKey + "-thumbnail"));
            }

            imageRepository.add(image);

        } catch (Exception e) {
            logger.error("", e);
        }

    }

    @Override
    public Image addImage(MultipartFile file) throws Exception {
        if (file == null) {
            throw new Exception("图片为空");
        }

        String objectKeyId = sdf.format(new Date()) + "/" + String.valueOf(new SnowFlake(0, 0).nextId());
        String extName = getExtensionName(file.getOriginalFilename());
        String objectKey = objectKeyId + "." + extName;
        objectKey = objectKey.toLowerCase();

        // 初始化一个BosClient
//        BosClientConfiguration config = new BosClientConfiguration();
//        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
//        config.setEndpoint(endpoint);
//        config.setConnectionTimeoutInMillis(0);
//        config.setSocketTimeoutInMillis(0);
//        BosClient client = new BosClient(config);
//
//        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file.getBytes());
//        putObjectFromFileResponse.getETag();

        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);

        Response response = uploadManager.put(file.getInputStream(), objectKey, upToken, null, null);

//        URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
//        String url = String.format("%s/%s/%s", endpoint, bucketName, objectKey);

        String finalUrl = String.format("%s/%s", DOMAIN, objectKey);

        Image img = new Image();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        img.setId(snowFlake.nextId());
        img.setUrl(finalUrl);
        img.setIsVideo(file.getContentType().toLowerCase().contains("video"));

        if (img.getIsVideo()) {
//            BceClientConfiguration bceClientConfig = new BceClientConfiguration();
//            bceClientConfig.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
//            bceClientConfig.setEndpoint("http://media.gz.baidubce.com");
//            MediaClient mediaClient = new MediaClient(bceClientConfig);
//            ThumbnailJob.createThumbnailJob(mediaClient, "latiao_video", objectKey);

            //数据处理指令，支持多个指令
            String saveJpgEntry = String.format("%s:%s.jpg", BUCKET, objectKeyId);
            String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));

            //String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", objectKeyId + ".jpg");
            OperationManager operationManager = new OperationManager(auth, cfg);
            operationManager.pfop(BUCKET, objectKey, vframeJpgFop, "latiao", true);

            img.setThumbnailUrl(finalUrl.replace(extName, "jpg-thumbnail"));
            img.setIsVideo(true);
        } else {
            img.setThumbnailUrl(finalUrl.replace(objectKey, objectKey + "-thumbnail"));
            img.setIsVideo(false);
        }
        img.setCreateTime(new Date());
        imageRepository.add(img);

        return img;
    }

    @Override
    public void addFeedbackImage(MultipartFile file, Long feedbackId) throws Exception {
        if (file == null) {
            throw new Exception("图片为空");
        }

        String objectKeyId = sdf.format(new Date()) + "/" + String.valueOf(new SnowFlake(0, 0).nextId());
        String extName = getExtensionName(file.getOriginalFilename());
        String objectKey = objectKeyId + "." + extName;
        objectKey = objectKey.toLowerCase();

        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);

        Response response = uploadManager.put(file.getInputStream(), objectKey, upToken, null, null);

        String finalUrl = String.format("%s/%s", DOMAIN, objectKey);

        Image img = new Image();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        img.setId(snowFlake.nextId());
        img.setFeedbackId(feedbackId);
        img.setUrl(finalUrl);
        img.setIsVideo(file.getContentType().toLowerCase().contains("video"));
        img.setThumbnailUrl(finalUrl.replace(objectKey, objectKey + "-thumbnail"));
        img.setIsVideo(false);
        img.setCreateTime(new Date());
        imageRepository.add(img);
    }

    @Override
    public void addImage(MultipartFile file, Long articleId) throws Exception {
        if (file == null) {
            throw new Exception("图片为空");
        }

        String objectKeyId = sdf.format(new Date()) + "/" + String.valueOf(new SnowFlake(0, 0).nextId());
        String extName = getExtensionName(file.getOriginalFilename());
        String objectKey = objectKeyId + "." + extName;
        objectKey = objectKey.toLowerCase();

        // 初始化一个BosClient
//        BosClientConfiguration config = new BosClientConfiguration();
//        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
//        config.setEndpoint(endpoint);
//        config.setConnectionTimeoutInMillis(0);
//        config.setSocketTimeoutInMillis(0);
//        BosClient client = new BosClient(config);
//
//        PutObjectResponse putObjectFromFileResponse = client.putObject(bucketName, objectKey, file.getBytes());
//        putObjectFromFileResponse.getETag();

        Configuration cfg = new Configuration(Zone.zone2());
        UploadManager uploadManager = new UploadManager(cfg);

        Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        String upToken = auth.uploadToken(BUCKET);

        Response response = uploadManager.put(file.getInputStream(), objectKey, upToken, null, null);

        //        URL url = client.generatePresignedUrl(bucketName, objectKey, -1);
//        String url = String.format("%s/%s/%s", endpoint, bucketName, objectKey);

        String finalUrl = String.format("%s/%s", DOMAIN, objectKey);

        Image img = new Image();
        SnowFlake snowFlake = new SnowFlake(0, 0);
        img.setId(snowFlake.nextId());
        img.setArticleId(articleId);
        img.setUrl(finalUrl);
        img.setIsVideo(file.getContentType().toLowerCase().contains("video"));

        if (img.getIsVideo()) {
//            BceClientConfiguration bceClientConfig = new BceClientConfiguration();
//            bceClientConfig.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
//            bceClientConfig.setEndpoint("http://media.gz.baidubce.com");
//            MediaClient mediaClient = new MediaClient(bceClientConfig);
//            ThumbnailJob.createThumbnailJob(mediaClient, "latiao_video", objectKey);

            String saveJpgEntry = String.format("%s:%s.jpg", BUCKET, objectKeyId);
            String vframeJpgFop = String.format("vframe/jpg/offset/1|saveas/%s", UrlSafeBase64.encodeToString(saveJpgEntry));

            OperationManager operationManager = new OperationManager(auth, cfg);
            operationManager.pfop(BUCKET, objectKey, vframeJpgFop, "latiao", true);

            img.setThumbnailUrl(finalUrl.replace(extName, "jpg-thumbnail"));
            img.setIsVideo(true);
        } else {
            img.setThumbnailUrl(finalUrl.replace(objectKey, objectKey + "-thumbnail"));
            img.setIsVideo(false);
        }

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
