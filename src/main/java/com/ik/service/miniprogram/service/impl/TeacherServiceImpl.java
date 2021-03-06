package com.ik.service.miniprogram.service.impl;

import java.util.Date;
import java.util.List;

import org.mybatis.extend.generic.service.impl.GenericServiceImpl;
import org.mybatis.extend.page.param.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ik.service.miniprogram.mapper.TeacherMapper;
import com.ik.service.miniprogram.model.Account;
import com.ik.service.miniprogram.model.Teacher;
import com.ik.service.miniprogram.service.TeacherService;

/**
 * ServiceImpl: TeacherServiceImpl
 * Mapper : TeacherMapper
 * Model  : Teacher
 *
 * This ServiceImpl generated by MyBatis Generator Extend at 2018-12-04 10:26:51
 */
@Service
@Transactional
public class TeacherServiceImpl extends GenericServiceImpl<Teacher, Integer, TeacherMapper> implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public TeacherMapper getGenericMapper() {
        return teacherMapper;
    }

    @Override
    public List<Teacher> getTeachers(String phone) {
        Teacher teacher = new Teacher();
        teacher.setPhone(phone);

        return teacherMapper.select(teacher);
    }

    @Override
    public List<Teacher> getTeachersByAccountId(Integer accountId) {
        Teacher teacher = new Teacher();
        teacher.setAccountId(accountId);

        return teacherMapper.select(teacher);
    }

    @Override
    public Teacher findTeacherByAccessToken(String userToken) {
        // return teacherMapper.findTeacherByAccessToken(userToken);
        return null;
    }

    @Override
    public Teacher save(Account account, Integer type) {
        Teacher teacher = new Teacher();
        teacher.setAccountId(account.getId());
        teacher.setName(account.getName());
        teacher.setPhone(account.getPhone());
        teacher.setEmail(account.getEmail());
        teacher.setType(type);
        teacher.setCreatedAt(new Date());
        teacher.setUpdatedAt(new Date());
        teacherMapper.insertSelective(teacher);
        return teacher;
    }

    @Override
    public Teacher update(Account account, Integer type) {
        Teacher teacher = new Teacher();
        teacher.setAccountId(account.getId());
        teacher = teacherMapper.selectOne(teacher);
        teacher.setName(account.getName());
        teacher.setEmail(account.getEmail());
        teacher.setType(type);
        teacherMapper.updateByPrimaryKeySelective(teacher);

        return teacher;
    }

    @Override
    public List<Teacher> getAllTeacherList(Integer pageSize, Integer PageNum) {
        Page page = new Page(PageNum, pageSize);

        return teacherMapper.getAllTeacherList(page);

    }
}