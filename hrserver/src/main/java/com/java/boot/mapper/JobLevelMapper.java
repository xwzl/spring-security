package com.java.boot.mapper;

import org.apache.ibatis.annotations.Param;
import com.java.boot.bean.JobLevel;

import java.util.List;

/**
 * Created by sang on 2018/1/11.
 */
public interface JobLevelMapper {
    JobLevel getJobLevelByName(String name);

    int addJobLevel(@Param("jobLevel") JobLevel jobLevel);

    List<JobLevel> getAllJobLevels();

    int deleteJobLevelById(@Param("ids") String[] ids);

    int updateJobLevel(@Param("jobLevel") JobLevel jobLevel);
}
