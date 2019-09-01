package com.study.order.bean;

import java.util.Date;

public class LessonSchedule {
    private String id;

    private String name;

    private String courseId;

    private Integer lessonPeriodType;

    private String lessonResource;

    private Date lessonTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId == null ? null : courseId.trim();
    }

    public Integer getLessonPeriodType() {
        return lessonPeriodType;
    }

    public void setLessonPeriodType(Integer lessonPeriodType) {
        this.lessonPeriodType = lessonPeriodType;
    }

    public String getLessonResource() {
        return lessonResource;
    }

    public void setLessonResource(String lessonResource) {
        this.lessonResource = lessonResource == null ? null : lessonResource.trim();
    }

    public Date getLessonTime() {
        return lessonTime;
    }

    public void setLessonTime(Date lessonTime) {
        this.lessonTime = lessonTime;
    }
}