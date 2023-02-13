package com.atguigu.crowd.entity;

/**
 * @author YUChangcan
 * @date 2022/12/20 - 11:37
 */
public class Subject {
    private String subjectName;
    private Integer subjectScore;

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Integer getSubjectScore() {
        return subjectScore;
    }

    public void setSubjectScore(Integer subjectScore) {
        this.subjectScore = subjectScore;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectName='" + subjectName + '\'' +
                ", subjectScore=" + subjectScore +
                '}';
    }

    public Subject(String subjectName, Integer subjectScore) {
        this.subjectName = subjectName;
        this.subjectScore = subjectScore;
    }

    public Subject() {
    }
}
