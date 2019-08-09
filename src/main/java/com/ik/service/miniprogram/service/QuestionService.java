package com.ik.service.miniprogram.service;

import java.util.List;

import org.mybatis.extend.generic.service.GenericService;
import org.mybatis.extend.page.param.Page;

import com.ik.service.miniprogram.model.Question;

/**
 * Service: QuestionService
 * Mapper : QuestionMapper
 * Model  : Question
 *
 * This Service generated by MyBatis Generator Extend at 2018-12-04 10:26:51
 */
public interface QuestionService extends GenericService<Question, Integer> {

    Question save(Integer courseType, Integer questionType, String stem, String questionChoice, String answer,
            Integer teacherId, Float point, String questionImage, String questionAudio, String questionVideo,
            String quesitonExplain);

    Question update(Question question, String stem, String questionChoice, String answer, Float point,
            String questionImage, String questionAudio, String questionVideo);

    List<Question> getByIds(List<Integer> questionIds);

    List<Question> getByTeacherId(Integer teacherId, Page page);
}