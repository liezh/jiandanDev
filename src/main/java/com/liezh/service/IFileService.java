package com.liezh.service;

import com.liezh.domain.dto.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2017/10/23.
 *  文件服务接口
 */
public interface IFileService {

    String upload(MultipartFile file, String path);

    ServerResponse uploadInLocal(MultipartFile file, String path);

}
