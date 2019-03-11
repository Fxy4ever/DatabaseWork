package com.ccz.votesystem.dao;

import com.ccz.votesystem.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 操作数据库
 * 字段：userId userName password phone age
 */
@Mapper
public interface UserDao {

    //插入用户
    @Insert("insert into user(userName,password,phone,age)" +
            "values (" +
            "#{userName,jdbcType=VARCHAR}," +
            "#{password,jdbcType=VARCHAR}," +
            "#{phone,jdbcType=VARCHAR}," +
            "#{age,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true,keyProperty = "userId",keyColumn = "userId")
    void insertUser(User user);

    //更新用户密码
    @Update("update user set password = #{password,jdbcType=VARCHAR} WHERE userName = #{userName,jdbcType=VARCHAR}")
    void updatePassword(@Param("userName") String userName,@Param("password") String password);

    //删除用户
    @Delete("delete from user WHERE userName = #{userName,jdbcType=VARCHAR}")
    void deleteUserByName(@Param("userName") String userName);

    //根据名称获取用户
    @Select("select userId,userName,phone,age from user WHERE userName = #{userName,jdbcType=VARCHAR}")
    User getUserByName(@Param("userName") String userName);

    //根据名称获取用户
    @Select("select userId,password,userName,phone,age from user WHERE userName = #{userName,jdbcType=VARCHAR}")
    User getUserByNameIncludePsw(@Param("userName") String userName);

    //根据名称获取用户
    @Select("select userId,userName,password,phone,age from user WHERE userName = #{userName,jdbcType=VARCHAR}")
    User getUserPassword(@Param("userName") String userName);

    @Select("select userId,userName,password,phone,age from user where userId = #{userId,jdbcType=INTEGER}")
    User getUserById(@Param("userId") int userId);

    //根据名称获取用户
    @Select("select userId,userName,phone,age from user")
    List<User> getAllUser();
}
