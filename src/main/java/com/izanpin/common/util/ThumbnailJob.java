package com.izanpin.common.util;

import com.baidubce.services.media.MediaClient;
import com.baidubce.services.media.model.GetThumbnailJobResponse;
import com.baidubce.services.media.model.ThumbnailCapture;
import com.baidubce.services.media.model.ThumbnailTarget;

/**
 * Created by pengyuancong on 2017/5/13.
 */
public class ThumbnailJob {
    public static String createThumbnailJob(MediaClient client, String pipelineName, String sourceKey) {
        ThumbnailTarget target = new ThumbnailTarget().withFormat("jpg").withSizingPolicy("keep");

        ThumbnailCapture capture =
                new ThumbnailCapture().withMode("auto");

        return client.createThumbnailJob(pipelineName, sourceKey, target, capture).getJobId();
    }

    public static GetThumbnailJobResponse getThumbnailJob(MediaClient client, String jobId) {
        return client.getThumbnailJob(jobId);
    }
}
