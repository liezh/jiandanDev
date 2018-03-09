package com.liezh.controller;

import com.google.common.collect.Maps;
import com.liezh.domain.common.MdUploadResult;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.service.IFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/9.
 */
@RestController
@RequestMapping("/api/file")
public class FileController {

    @Value("${ftp.server.http.prefix}")
    private String ftpServicePrefix;

    @Autowired
    private IFileService fileService;

    @PostMapping("/upload")
//    @PreAuthorize("authenticated")
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
    public MdUploadResult mdUpload(MultipartFile file, HttpServletRequest req) {
        MdUploadResult result = new MdUploadResult();
        String path = req.getSession().getServletContext().getRealPath("upload");
        ServerResponse sp = fileService.uploadInLocal(file, path);
        if (sp.isSuccess()) {
            Map fileMap = Maps.newHashMap();
            fileMap = (Map) sp.getData();
            result.setUrl(fileMap.get("url").toString());
            return result;
        }
        result.setSuccess(0);
        result.setMessage(sp.getMsg());
        return result;
    }

}
