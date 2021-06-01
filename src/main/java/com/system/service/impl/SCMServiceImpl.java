package com.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.system.dao.SCMDao;
import com.system.entity.ResponseMessage;
import com.system.entity.SCMData;
import com.system.entity.SCMDataDTO;
import com.system.entity.SCMDataVO;
import com.system.service.SCMService;
import com.system.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Luffy
 * @description scm服务层实现
 * @createTime 2021年05月08日 11:46:00
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SCMServiceImpl implements SCMService {

    @Autowired
    private SCMDao scmDao;

    /**
    * <h1>解析上传的文件<h1>
    */
    @Override
    public void fileParsing(MultipartFile file) throws Exception {
        

    }


    /**
    * <h1>保存scm数据<h1>
    */
    @Override
    public int saveSCMData(SCMData scmData) {
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        populateSCMData(scmData,currentTimestamp);
        return scmDao.insert(scmData);
    }

    /**
    * <h1>批量保存scm数据<h1>
    */
    @Override
    public int batchSaveSCMData(List<SCMData> scmDataList) {
        Long currentTimestamp = DateUtil.getCurrentTimestamp();
        scmDataList.forEach(scmData -> populateSCMData(scmData,currentTimestamp));
        int result = 0;
        for (SCMData scmData : scmDataList) {
            result += scmDao.insert(scmData);
        }
        return result;
    }

    @Override
    public List<SCMData> getSCMDataListByIds(List<Long> ids) {
        return scmDao.selectBatchIds(ids);
    }

    /**
    * <h1>获取scm列表<h1>
    */
    @Override
    public ResponseMessage getSCMDataList(Integer page, Integer size, SCMDataDTO scmDataDTO) throws ParseException {
        Page<SCMData> pageInfo = new Page<>(page,size);
        QueryWrapper<SCMData> wrapper = null;
        if (Objects.nonNull(scmDataDTO) && Objects.nonNull(scmDataDTO.getBegin()) && Objects.nonNull(scmDataDTO.getEnd())) {
            Long begin = scmDataDTO.getBegin();
            Long end = scmDataDTO.getEnd();
            wrapper = new QueryWrapper<>();
            wrapper.ge("save_time",begin);
            wrapper.le("save_time",end);
        }
        IPage<SCMData> accountIPage = scmDao.selectPage(pageInfo, wrapper);
        long scmCount = accountIPage.getTotal();
        if (scmCount <= 0) {
            return ResponseMessage.success().data("totalCount",0).data("scmList",new ArrayList<>());
        }
        List<SCMData> scmDataList = accountIPage.getRecords();
        List<SCMDataVO> result = new ArrayList<>();
        for (SCMData scmData : scmDataList) {
            result.add(SCMDataVO.from(scmData));
        }
        return ResponseMessage.success().data("totalCount",scmCount).data("scmList",result);
    }

    @Override
    public ResponseMessage scheduleGetSCMDataList(Integer size)  {
        QueryWrapper<SCMData> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("update_time");
        wrapper.last("limit " + size);
        List<SCMData> scmDaoList = scmDao.selectList(wrapper);
        List<SCMDataVO> result = new ArrayList<>();
        for (SCMData scmData : scmDaoList) {
            result.add(SCMDataVO.from(scmData));
        }
        return ResponseMessage.success().data("list",result);
    }

    /**
    * <h1>删除scm<h1>
    */
    @Override
    public int deleteSCMDataById(Long id) {
        return scmDao.deleteById(id);
    }

    /**
     * <h1>批量删除scm<h1>
     */
    @Override
    public int batchDeleteSCMDataById(List<Long> ids) {
        return scmDao.deleteBatchIds(ids);
    }

    /**
     * <h1>修改scm<h1>
     */
    @Override
    public int updateSCMData(SCMData scmData) {
        return scmDao.updateById(scmData);
    }

    /**
     * <h1>批量修改scm<h1>
     */
    @Override
    public int batchUpdateSCMData(List<SCMData> scmDataList) {
        int result = 0;
        for (SCMData scmData : scmDataList) {
            result += scmDao.updateById(scmData);
        }
        return result;
    }

    private void populateSCMData(SCMData scmData,Long timeStamp) {
        scmData.setSaveTime(timeStamp);
        scmData.setUpdateTime(timeStamp);
    }
}
