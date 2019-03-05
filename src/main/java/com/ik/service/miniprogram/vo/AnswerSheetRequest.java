package com.ik.service.miniprogram.vo;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnswerSheetRequest {
    @NotNull(message = "paperId不可为空")
    private Integer paperId;

    @NotNull(message = "studentId不可为空")
    private Integer studentId;

    @NotNull(message = "teacherId不可为空")
    private Integer teacherId;

    @NotNull(message = "score不可为空")
    private Float score;

    @NotNull(message = "studentAnswer不可为空")
    private String studentAnswer;

    @NotNull(message = "teacherCorrection不可为空")
    private String teacherCorrection;

    @NotNull(message = "questions不可为空")
    private String questions;

    @NotNull(message = "remark不可为空")
    private String remark;

    @NotNull(message = "startedAt不可为空")
    private String startedAt;

    @NotNull(message = "completedAt不可为空")
    private String completedAt;

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getTeacherCorrection() {
        return teacherCorrection;
    }

    public void setTeacherCorrection(String teacherCorrection) {
        this.teacherCorrection = teacherCorrection;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
