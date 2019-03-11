package com.ccz.votesystem.entity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/*
用户类
 */
@Data
public class User {

    private int userId;

    @NotBlank
    @Length(min = 5, max = 25,message = "用户名长度必须大于5小于25")
    private String userName;

    @NotBlank
    @Length(min = 11,max = 11,message = "手机号长度必须为11位")
    private String phone;

    @NotBlank
    @Length(min = 5,message = "密码长度必须大于5")
    private String password;

    @Max(value = 100, message = "年龄不能大于100岁")
    @Min(value = 0, message = "必须年满0岁！")
    private int age;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
