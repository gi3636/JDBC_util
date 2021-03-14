package org.example.entity;

import org.example.annotation.Column;
import org.example.annotation.Table;

import java.util.Date;

@Table("emp")//填写表名
public class Emp {
    private Integer empno;
    @Column("ename")//填写字段
    private String name;
    private String job;
    private Integer mgr;
    @Column("sal")//填写字段
    private Double salary;

    @Column("hiredate")//填写字段
    private Date hiredDate;;

    public Emp() {

    }

    public Integer getEmpno() {
        return empno;
    }

    public void setEmpno(Integer empno) {
        this.empno = empno;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getMgr() {
        return mgr;
    }

    public void setMgr(Integer mgr) {
        this.mgr = mgr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }


    public Date getHiredDate() {
        return hiredDate;
    }

    public void setHiredDate(Date hiredDate) {
        this.hiredDate = hiredDate;
    }

    public Emp(Integer empno, String name, String job, Integer mgr, Double salary, Date hiredDate) {
        this.empno = empno;
        this.name = name;
        this.job = job;
        this.mgr = mgr;
        this.salary = salary;
        this.hiredDate = hiredDate;
    }


    @Override
    public String toString() {
        return "Emp{" +
                "empno=" + empno +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", mgr=" + mgr +
                ", salary=" + salary +
                ", hiredDate=" + hiredDate +
                '}';
    }
}
