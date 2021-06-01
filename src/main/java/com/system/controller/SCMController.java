package com.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.system.entity.ResponseMessage;
import com.system.entity.SCMData;
import com.system.entity.SCMDataDTO;
import com.system.enumeration.ErrorEnum;
import com.system.service.SCMService;
import com.system.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Luffy
 * @description SCM控制层
 * @createTime 2021年05月08日 09:29:00
 */
@RestController
@RequestMapping("/scm")
@CrossOrigin
public class SCMController {

    @Autowired
    private SCMService scmService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SCMController.class);

    /**
    * <h1>导出scm数据<h1>
    */
    @GetMapping("/export")
    public ResponseMessage exportExcel(@RequestParam("ids") List<Long> ids, HttpServletResponse response) {
        /**
        * <h1>设置返回格式和编码<h1>
        */
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String fileName;
        try {
            fileName = URLEncoder.encode("scmData", "UTF-8");
        } catch (Exception e) {
            fileName = "scmData";
        }

        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

        List<SCMData> result = scmService.getSCMDataListByIds(ids);
        if (CollectionUtils.isEmpty(result)) {
            LOGGER.error("[SCMController > exportExcel] The target scmData list is null.");
            return ResponseMessage.error().data("errorMessage", ErrorEnum.TARGET_SCM_LIST_IS_NULL.getErrorMessage());
        }

        /**
        * <h1>写入返回的IO流中<h1>
        */
        try {
            EasyExcel.write(response.getOutputStream(), SCMData.class).sheet("result").doWrite(result);
        } catch (Exception e) {
            LOGGER.error("[SCMController > exportExcel] Write IO Exception, Track is :",e);
            return ResponseMessage.error().data("errorMessage",ErrorEnum.IO_ERROR.getErrorMessage());
        }
        return ResponseMessage.success();
    }

    @PostMapping("/upload/file")
    public ResponseMessage uploadFile(@NotNull(message = "File can not be null.") @RequestParam("file") MultipartFile file) {
        //TODO 解析上传文件
        try {
            scmService.fileParsing(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseMessage.success();
    }

    @PutMapping("/save")
    public ResponseMessage saveSCM(@RequestBody SCMData scmData) {
        int affectRow = 0;
        try {
            affectRow = scmService.saveSCMData(scmData);
        } catch (Exception e) {
            LOGGER.error("[SCMController > saveSCM] Save scmData fail. Target scmData is {} . Track",scmData,e);
        }
        return checkResult(affectRow,ErrorEnum.SAVE_ERROR);
    }

    @PutMapping("/batchSave")
    public ResponseMessage batchSaveSCM(@Valid @RequestBody List<SCMData> scmDataList) {
        int affectRow = 0;
        try {
            affectRow = scmService.batchSaveSCMData(scmDataList);
        } catch (Exception e) {
            LOGGER.error("[SCMController > batchSaveSCM] Batch save scmData fail. Target scmDataList is {} . Track",JSON.toJSONString(scmDataList),e);
        }
        return checkResult(affectRow,ErrorEnum.SAVE_ERROR);
    }

    public ResponseMessage checkResult(int affectRow,ErrorEnum type) {
        return affectRow <= 0 ? ResponseMessage.error().data("errorMessage",type.getErrorMessage()) : ResponseMessage.success();
    }

    @PostMapping("/list")
    public ResponseMessage getSCMList(@RequestBody SCMDataDTO scmDataDTO) {
        Integer page = scmDataDTO.getPage();
        Integer size = scmDataDTO.getSize();
        if (Objects.isNull(page) || page <= 0) {
            page = 1;
        }

        if (Objects.isNull(size) || size <= 0) {
            size = 20;
        }

        try {
            return  scmService.getSCMDataList(page,size,scmDataDTO);
        } catch (Exception e) {
            LOGGER.error("[SCMController > getSCMList] Select scmDataList fail. Track",e);
            return ResponseMessage.error().data("errorMessage", ErrorEnum.SELECT_ERROR.getErrorMessage());
        }
    }

    @GetMapping("/schedule/{size}")
    public ResponseMessage getSCMListSchedule(@PathVariable Integer size) {

        if (Objects.isNull(size) || size <= 0) {
            size = 10;
        }

        try {
            return scmService.scheduleGetSCMDataList(size);
        } catch (Exception e) {
            LOGGER.error("[SCMController > getSCMList] Select scmDataList fail. Track",e);
            return ResponseMessage.error().data("errorMessage", ErrorEnum.SELECT_ERROR.getErrorMessage());
        }
    }


    @DeleteMapping("/byId/{id}")
    public ResponseMessage deleteSCM(@NotNull(message = "id can not be null.") @PathVariable Long id) {
        int affectRow = 0;
        try {
            affectRow = scmService.deleteSCMDataById(id);
        } catch (Exception e) {
            LOGGER.error("[SCMController > deleteSCM] Delete scmData by id fail. id is {} Track",id,e);
        }

        return checkResult(affectRow,ErrorEnum.DELETE_ERROR);
    }

    @DeleteMapping("/batchById")
    public ResponseMessage batchDeleteSCM(@NotNull(message = "ids can not be null.") @RequestParam("ids") List<Long> ids) {
        int affectRow = 0;
        try {
            affectRow = scmService.batchDeleteSCMDataById(ids);
        } catch (Exception e) {
            LOGGER.error("[SCMController > batchDeleteSCM] Batch delete scmData by id fail. ids is {} Track",ids,e);
        }
        return checkResult(affectRow,ErrorEnum.DELETE_ERROR);
    }


    @PutMapping("/update/byId")
    public ResponseMessage updateSCM(@RequestBody SCMData scmData) {
        if (Objects.isNull(scmData.getId())) {
            LOGGER.error("[SCMController > updateSCM] SCMData's id is null.");
            return ResponseMessage.error().data("errorMessage",ErrorEnum.SCM_ID_IS_NULL.getErrorMessage());
        }
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        scmData.setUpdateTime(currentTimestamp);
        int affectRow = 0;
        try {
            affectRow = scmService.updateSCMData(scmData);
        } catch (Exception e) {
            LOGGER.error("[SCMController > updateSCM] Update scmData fail. Target scmData is {} Track",scmData,e);
        }
        return checkResult(affectRow,ErrorEnum.UPDATE_ERROR);
    }

    @PutMapping("/batchUpdate/byId")
    public ResponseMessage batchUpdateSCM(@NotNull(message = "scmDataList can not be null") @RequestBody List<SCMData> scmDataList) {
        checkSCMDataList(scmDataList);
        if (scmDataList.size() == 0) {
            LOGGER.error("[SCMController > batchUpdateSCM] All scmData's id is null.");
            return ResponseMessage.error().data("errorMessage",ErrorEnum.All_SCM_ID_IS_NULL.getErrorMessage());
        }
        int affectRow = 0;
        try {
            affectRow = scmService.batchUpdateSCMData(scmDataList);
        } catch (Exception e) {
            LOGGER.error("[SCMController > batchUpdateSCM] Batch Update scm fail. Target scmDataList is {} Track", JSON.toJSONString(scmDataList),e);
        }
        return checkResult(affectRow,ErrorEnum.UPDATE_ERROR);
    }

    /**
    * <p>校验scmDataList数据<p>
    */
    public void checkSCMDataList(List<SCMData> scmDataList) {
        Iterator<SCMData> scmDataIterator = scmDataList.iterator();
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        while (scmDataIterator.hasNext()) {
            SCMData scmData = scmDataIterator.next();
            if (Objects.isNull(scmData.getId())) {
                scmDataIterator.remove();
                continue;
            }
            scmData.setUpdateTime(currentTimestamp);
        }
    }


}
