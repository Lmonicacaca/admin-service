package com.mbr.admin.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileUpload {
    /**
     * 中转文件
     *
     *            上传的文件
     * @return 响应结果
     */
    public static String httpClientUploadFile(List<MultipartFile> fileList,String origin,Long userId) {
        final String remote_url = "http://47.100.18.6:9998/uploadFile/"+origin+"/"+userId;// 第三方服务器请求地址
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(remote_url);
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for(MultipartFile file:fileList) {
                String fileName = file.getOriginalFilename();
                builder.addBinaryBody("file", file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
                builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            HttpResponse response = httpClient.execute(httpPost);// 执行提交
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                // 将响应内容转换为字符串
                result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static List<MultipartFile> getFile(HttpServletRequest request){
        List<MultipartFile> list = new ArrayList<>();
        CommonsMultipartResolver cmr = new CommonsMultipartResolver(request.getServletContext());
        if (cmr.isMultipart(request)) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
            Iterator<String> files = mRequest.getFileNames();
            while (files.hasNext()) {
                MultipartFile mFile = mRequest.getFile(files.next());
                if (mFile != null) {
                    list.add(mFile);
                }
            }
        }
        return  list;
    }
}
