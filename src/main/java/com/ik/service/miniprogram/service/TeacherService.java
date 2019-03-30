package com.ik.service.miniprogram.service;

import java.util.List;

import org.mybatis.extend.generic.service.GenericService;

import com.ik.service.miniprogram.model.Account;
import com.ik.service.miniprogram.model.Teacher;

/**
 * Service: TeacherService
 * Mapper : TeacherMapper
 * Model  : Teacher
 *
 * This Service generated by MyBatis Generator Extend at 2018-12-04 10:26:51
 */
public interface TeacherService extends GenericService<Teacher, Integer> {
    List<Teacher> getTeachers(String phone);

    List<Teacher> getTeachersByAccountId(Integer accountId);

    Teacher findTeacherByAccessToken(String userToken);

    Teacher save(Account account, Integer type);

    Teacher update(Account account, Integer type);

    List<Teacher> getAllTeacherList(Integer pageSize, Integer PageNum);
}