package com.anningtex.testgreendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @ClassName: StudentAndTeacherBean
 * @Description: java类作用描述
 * @Author: alvis
 * @CreateDate: 2020/7/8 15:53
 */
@Entity
public class StudentAndTeacherBean {
    @Id(autoincrement = true)
    Long id;
    Long studentId;//学生ID
    Long teacherId;//老师ID

    public StudentAndTeacherBean(Long studentId, Long teacherId) {
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    @Generated(hash = 2146410221)
    public StudentAndTeacherBean(Long id, Long studentId, Long teacherId) {
        this.id = id;
        this.studentId = studentId;
        this.teacherId = teacherId;
    }

    @Generated(hash = 207804266)
    public StudentAndTeacherBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return this.studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTeacherId() {
        return this.teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }
}
