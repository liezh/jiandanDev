package com.liezh.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Maps;
import com.liezh.domain.common.MdUploadResult;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.service.IFileService;
import com.liezh.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/9.
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    private static Logger logger = LoggerFactory.getLogger(FileController.class);

    @Value("${ftp.server.http.prefix}")
    private String ftpServicePrefix;

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
    @PreAuthorize("authenticated")
    public ServerResponse upload(MultipartFile file, HttpServletRequest req) {
        String path = req.getSession().getServletContext().getRealPath("upload");
//        String targetFileName =  fileService.upload(file, path);
//
//        String url = ftpServicePrefix + targetFileName;
//        Map fileMap = Maps.newHashMap();
//        fileMap.put("uri", targetFileName);
//        fileMap.put("url", url);
//        return ServerResponse.createBySuccess(fileMap);

        return fileService.uploadInLocal(file, path);
    }

    @PostMapping("/mdUpload")
    @PreAuthorize("authenticated")
    @ResponseBody
    public MdUploadResult mdUpload(@RequestParam("editormd-image-file") MultipartFile uploadfile, HttpServletRequest req) {
        MdUploadResult result = new MdUploadResult();
        String path = req.getSession().getServletContext().getRealPath("upload");
        ServerResponse sp = fileService.uploadInLocal(uploadfile, path);
        if (sp.isSuccess()) {
            Map fileMap = Maps.newHashMap();
            fileMap = (Map) sp.getData();
            result.setUrl(fileMap.get("url").toString());
            logger.info(JsonUtil.toJson(result));
            return result;
        }
        result.setSuccess(0);
        result.setMessage(sp.getMsg());
        logger.error(JsonUtil.toJson(result));
        return result;
    }

}
