package com.ryan.novachat.service;

import com.ryan.novachat.common.enums.APIErrorCommonEnum;
import com.ryan.novachat.common.exception.BusinessException;
import com.ryan.novachat.dto.FileBasicInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Ryan
 * @Date 2025/12/6 23:38
 */
@Slf4j
@Service
public class FileInfoService {
    public Boolean fileInfo2DB(FileBasicInfoDTO fileBasicInfoDTO) {
        if (fileBasicInfoDTO == null) {
            throw new BusinessException(APIErrorCommonEnum.PARAM_MISSING);
        }
        // TODO 写库操作

        return true;
    }
}
