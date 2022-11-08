package com.sun.mall.thirdparty.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.sun.mall.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author Sun Jianda
 * @Date 2022/11/3
 */

@RestController
@RequestMapping("/oss")
@SuppressWarnings("ALL")
public class OSSController {

    @Autowired
    private OSS ossClient;

    @Value("${spring.cloud.alicloud.oss.bucket}")
    private String bucket;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endpoint;

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    /**
     * 获取上传 Policy
     */
    @RequestMapping("/policy")
    protected R policy() {
        // 文件上传后的访问地址：https://mall-study-sun.oss-cn-shanghai.aliyuncs.com/test/mountain.jpg（https://bucket.endpoint/objectName）
        String host = "https://" + bucket + "." + endpoint; // host = bucket.endpoint
        String dir = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM-dd")) + "/";
        Map<String, String> respMap = null;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConditions = new PolicyConditions();
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConditions.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return R.ok().put("data", respMap);
    }
}
