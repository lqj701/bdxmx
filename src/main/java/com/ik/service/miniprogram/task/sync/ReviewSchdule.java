package com.ik.service.miniprogram.task.sync;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ik.service.miniprogram.model.TeacherStudentMap;
import com.ik.service.miniprogram.service.TeacherStudentMapService;

@Component
public class ReviewSchdule {
    private static final Logger logger = LoggerFactory.getLogger(ReviewSchdule.class);
    @Autowired
    private TeacherStudentMapService teacherStudentMapService;

    @Scheduled(cron = "0 */5 * * * ?") // 每五分钟刷新一次
    public void updateStudentBindedTeachers() {
        logger.info("begin review task");
        TeacherStudentMap teacherStudentMap = new TeacherStudentMap();
        teacherStudentMap.setAuditStatus(true);
        List<TeacherStudentMap> teacherStudentMapList = teacherStudentMapService.select(teacherStudentMap);

        teacherStudentMapList.stream().forEach(teacherStudentMap1 -> {
            teacherStudentMapService.updateStudentBindedTeachers(teacherStudentMap1.getStudentId());
        });
        logger.info("end review task");
    }
}
