package com.ik.service.miniprogram.model;

import java.io.Serializable;
import java.util.Date;
import org.mybatis.extend.generic.model.BaseModel;

/**
 * Model: Question
 * Table: questions
 * Alias: qu
 *
 * This Model generated by MyBatis Generator Extend.
 */
public class Question extends BaseModel<Integer> implements Serializable {
    /**
     * 命题人ID
     * teacher_id
     */
    private Integer teacherId;

    /**
     * 题目分数
     * point
     */
    private Float point;

    /**
     * 课程类型1：语文；2：数学；3：英语；4：物理；5：化学；6：生物；7：政治；8：历史；9：地理
     * course_type
     */
    private Integer courseType;

    /**
     * 题目类型1：选择；2：判断；3：填空；4：解答
     * question_type
     */
    private Integer questionType;

    /**
     * 创建时间
     * created_at
     */
    private Date createdAt;

    /**
     * 修改时间
     * updated_at
     */
    private Date updatedAt;

    /**
     * 题干
     * question_stem
     */
    private String questionStem;

    /**
     * 选项 JSON字符串
     * question_choice
     */
    private String questionChoice;

    /**
     * 解答
     * question_explain
     */
    private String questionExplain;

    /**
     * JSON字符串
     * question_answer
     */
    private String questionAnswer;

    /**
     * 
     * question_audio
     */
    private String questionAudio;

    /**
     * 
     * question_image
     */
    private String questionImage;

    /**
     * 
     * question_video
     */
    private String questionVideo;

    private static final long serialVersionUID = 1L;

    /**
     * 命题人ID
     * teacher_id
     */
    public Integer getTeacherId() {
        return teacherId;
    }

    /**
     * 命题人ID
     * teacher_id
     *
     * @param teacherId 命题人ID
     */
    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * 题目分数
     * point
     */
    public Float getPoint() {
        return point;
    }

    /**
     * 题目分数
     * point
     *
     * @param point 题目分数
     */
    public void setPoint(Float point) {
        this.point = point;
    }

    /**
     * 课程类型1：语文；2：数学；3：英语；4：物理；5：化学；6：生物；7：政治；8：历史；9：地理
     * course_type
     */
    public Integer getCourseType() {
        return courseType;
    }

    /**
     * 课程类型1：语文；2：数学；3：英语；4：物理；5：化学；6：生物；7：政治；8：历史；9：地理
     * course_type
     *
     * @param courseType 课程类型1：语文；2：数学；3：英语；4：物理；5：化学；6：生物；7：政治；8：历史；9：地理
     */
    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    /**
     * 题目类型1：选择；2：判断；3：填空；4：解答
     * question_type
     */
    public Integer getQuestionType() {
        return questionType;
    }

    /**
     * 题目类型1：选择；2：判断；3：填空；4：解答
     * question_type
     *
     * @param questionType 题目类型1：选择；2：判断；3：填空；4：解答
     */
    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }

    /**
     * 创建时间
     * created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 创建时间
     * created_at
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 修改时间
     * updated_at
     */
    public Date getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 修改时间
     * updated_at
     *
     * @param updatedAt 修改时间
     */
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 题干
     * question_stem
     */
    public String getQuestionStem() {
        return questionStem;
    }

    /**
     * 题干
     * question_stem
     *
     * @param questionStem 题干
     */
    public void setQuestionStem(String questionStem) {
        this.questionStem = questionStem;
    }

    /**
     * 选项 JSON字符串
     * question_choice
     */
    public String getQuestionChoice() {
        return questionChoice;
    }

    /**
     * 选项 JSON字符串
     * question_choice
     *
     * @param questionChoice 选项 JSON字符串
     */
    public void setQuestionChoice(String questionChoice) {
        this.questionChoice = questionChoice;
    }

    /**
     * 解答
     * question_explain
     */
    public String getQuestionExplain() {
        return questionExplain;
    }

    /**
     * 解答
     * question_explain
     *
     * @param questionExplain 解答
     */
    public void setQuestionExplain(String questionExplain) {
        this.questionExplain = questionExplain;
    }

    /**
     * JSON字符串
     * question_answer
     */
    public String getQuestionAnswer() {
        return questionAnswer;
    }

    /**
     * JSON字符串
     * question_answer
     *
     * @param questionAnswer JSON字符串
     */
    public void setQuestionAnswer(String questionAnswer) {
        this.questionAnswer = questionAnswer;
    }

    /**
     * 
     * question_audio
     */
    public String getQuestionAudio() {
        return questionAudio;
    }

    /**
     * 
     * question_audio
     *
     * @param questionAudio 
     */
    public void setQuestionAudio(String questionAudio) {
        this.questionAudio = questionAudio;
    }

    /**
     * 
     * question_image
     */
    public String getQuestionImage() {
        return questionImage;
    }

    /**
     * 
     * question_image
     *
     * @param questionImage 
     */
    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    /**
     * 
     * question_video
     */
    public String getQuestionVideo() {
        return questionVideo;
    }

    /**
     * 
     * question_video
     *
     * @param questionVideo 
     */
    public void setQuestionVideo(String questionVideo) {
        this.questionVideo = questionVideo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", teacherId=").append(teacherId);
        sb.append(", point=").append(point);
        sb.append(", courseType=").append(courseType);
        sb.append(", questionType=").append(questionType);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", questionStem=").append(questionStem);
        sb.append(", questionChoice=").append(questionChoice);
        sb.append(", questionExplain=").append(questionExplain);
        sb.append(", questionAnswer=").append(questionAnswer);
        sb.append(", questionAudio=").append(questionAudio);
        sb.append(", questionImage=").append(questionImage);
        sb.append(", questionVideo=").append(questionVideo);
        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        return result;
    }
}