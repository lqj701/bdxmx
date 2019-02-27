package com.ik.service.miniprogram.controller.api.pc;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ik.crm.commons.dto.ResultResponse;
import com.ik.service.miniprogram.controller.api.mp.MpUserController;
import com.ik.service.miniprogram.prop.FileProperty;

@RestController
@RequestMapping(value = "/api/file", produces = "application/json;utf-8")
public class FileController {
    private static Logger logger = LoggerFactory.getLogger(MpUserController.class);

    @Autowired
    private FileProperty fileProperty;

    @RequestMapping(value = "multiFileUpload", method = RequestMethod.POST)
    public ResultResponse multiFileUpload(@RequestParam("file") MultipartFile[] files) {
        JSONArray data = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        StringBuilder builder = new StringBuilder();
        for (MultipartFile file : files) {
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "-" + file.getOriginalFilename();
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
            String path = "/tmp";
            if ("png".equalsIgnoreCase(fileType) || "jpg".equalsIgnoreCase(fileType)
                    || "jpeg".equalsIgnoreCase(fileType) || "gif".equalsIgnoreCase(fileType)
                    || "ico".equalsIgnoreCase(fileType)) {
                path = fileProperty.getPrefix() + fileProperty.getImage();
            }
            if ("mp3".equalsIgnoreCase(fileType) || "wav".equalsIgnoreCase(fileType)
                    || "flac".equalsIgnoreCase(fileType)) {
                path = fileProperty.getPrefix() + fileProperty.getAudio();
            }
            if ("rm".equalsIgnoreCase(fileType) || "rmvb".equalsIgnoreCase(fileType) || "avi".equalsIgnoreCase(fileType)
                    || "wmv".equalsIgnoreCase(fileType) || "MKV".equalsIgnoreCase(fileType)
                    || "MP4".equalsIgnoreCase(fileType) || "VOB".equalsIgnoreCase(fileType)
                    || "MOV".equalsIgnoreCase(fileType) || "FLV".equalsIgnoreCase(fileType)) {
                path = fileProperty.getPrefix() + fileProperty.getVideo();
            }
            try {
                file.transferTo(new File(path, fileName));
            } catch (IOException e) {
                continue;
            }
            jsonObject.put("url", fileProperty.getHost() + "/" + fileName);
            jsonObject.put("name", fileName);
            data.add(jsonObject);
        }

        return ResultResponse.success(data);
    }
}
