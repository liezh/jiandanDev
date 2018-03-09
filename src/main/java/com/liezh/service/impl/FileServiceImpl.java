package com.liezh.service.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liezh.domain.constant.ResponseEnum;
import com.liezh.domain.dto.ServerResponse;
import com.liezh.service.IFileService;
import com.liezh.utils.FTPUtil;
import com.liezh.utils.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/9.
 */
@Service
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${local.server.http.prefix}")
    private String localServerPrefix;

    @Override
    public String upload(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传的文件名{}，上传路径{}，新文件名{}", fileName, path, uploadFileName );

        File fileDir = new File(path);
        if(!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
            // 文件已经上传成功

            // TODO 将targetFile上传到ftp服务器上
            FTPUtil.uploadFile(Lists.newArrayList(targetFile));
            // TODO 上传成功后删除upload下的文件
            targetFile.delete();
        } catch (IOException e) {
            logger.error("上传文件异常", e);
            return null;
        }
        return targetFile.getName();
    }

    /**
     *  上传图片到当前服务器根目录下
     * @param file
     * @param path
     * @return
     */
    @Override
    public ServerResponse uploadInLocal(MultipartFile file, String path) {
        String fileName = file.getOriginalFilename();
        String fileExtensionName = fileName.substring(fileName.lastIndexOf(".")+1);
        String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
        logger.info("开始上传文件,上传的文件名{}，上传路径{}，新文件名{}", fileName, path, uploadFileName );

        File fileDir = new File(path);
        if(!fileDir.exists()) {
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        File targetFile = new File(path, uploadFileName);
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            logger.error("文件保存失败！ e: {}, msg: {}", e, e.getMessage());
            return ServerResponse.createByResponseEnum(ResponseEnum.FILE_UPLOAD_FAILURE);
        }
        Map fileMap = Maps.newHashMap();
        fileMap.put("uri", targetFile.getName());
        String childDir = path.substring(path.lastIndexOf("\\") + 1) + "/";
        fileMap.put("url", localServerPrefix + childDir + targetFile.getName());
        logger.info("文件上传成功！ fileMap: {}", JsonUtil.toJson(fileMap));
        return ServerResponse.createBySuccess(fileMap);
    }


}
