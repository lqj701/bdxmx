package com.ik.service.miniprogram.service.impl;

import org.mybatis.extend.generic.service.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ik.service.miniprogram.mapper.StudentMapper;
import com.ik.service.miniprogram.model.Student;
import com.ik.service.miniprogram.service.StudentService;

/**
 * ServiceImpl: StudentServiceImpl
 * Mapper : StudentMapper
 * Model  : Student
 *
 * This ServiceImpl generated by MyBatis Generator Extend at 2018-12-04 10:26:51
 */
@Service
@Transactional
public class StudentServiceImpl extends GenericServiceImpl<Student, Integer, StudentMapper> implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    public StudentMapper getGenericMapper() {
        return studentMapper;
    }
}