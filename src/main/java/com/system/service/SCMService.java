package com.system.service;


import com.system.entity.ResponseMessage;
import com.system.entity.SCMData;
import com.system.entity.SCMDataDTO;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

/**
 * @author Luffy
 * @description SCM服务层
 * @createTime 2021年05月08日 11:35:00
 */
public interface SCMService {

    /**
    * <p>文件解析<p>
    */
    void fileParsing(MultipartFile file) throws Exception;

    /**
     * <p>保存scm上传信息<p>
     */
    int saveSCMData(SCMData scmData);

    /**
     * <p>批量保存scm上传信息<p>
     */
    int batchSaveSCMData(List<SCMData> scmDataList);

    /**
    * <p>通过id获取scm数据集合<p>
    */
    List<SCMData> getSCMDataListByIds(List<Long> ids);

    /**
     * <p>获取scm分页信息<p>
     */
    ResponseMessage getSCMDataList(Integer page, Integer size, SCMDataDTO scmDataDTO) throws ParseException;

    /**
     * <p>周期获取scm信息<p>
     */
    ResponseMessage scheduleGetSCMDataList(Integer size) throws ParseException;

    /**
     * <p>根据id删除scm信息<p>
     */
    int deleteSCMDataById(Long id);

    /**
     * <p>根据id批量删除scm信息<p>
     */
    int batchDeleteSCMDataById(List<Long> ids);

    /**
     * <p>修改scm信息<p>
     */
    int updateSCMData(SCMData scmData);

    /**
     * <p>批量修改scm信息<p>
     */
    int batchUpdateSCMData(List<SCMData> scmDataList);


}
