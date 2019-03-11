package com.ccz.votesystem.service;

import com.ccz.votesystem.dao.UserDao;
import com.ccz.votesystem.entity.JsonWrapper;
import com.ccz.votesystem.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
    private UserDao userDao;


    //添加用户
    public JsonWrapper addUser(User user) {
        User mUser = userDao.getUserByName(user.getUserName());
        if (mUser != null) {
            return new JsonWrapper("failed!用户已存在", 400);
        }
        mUser = new User();
        mUser.setUserName(user.getUserName());
        mUser.setAge(user.getAge());
        mUser.setPassword(user.getPassword());
        mUser.setPhone(user.getPhone());
        userDao.insertUser(mUser);
        return new JsonWrapper("success!", 200, mUser);
    }

    //根据用户名获得用户信息
    public JsonWrapper getUser(String name) {
        System.out.println(name);
        User user = userDao.getUserByName(name);
        System.out.println(user);
        if (user != null) {
            return new JsonWrapper("success!", 200, user);
        } else {
            return new JsonWrapper("failed!用户不存在", 400);
        }
    }

    public User getUserOnly(String name) {
        return userDao.getUserByNameIncludePsw(name);
    }

    public User getUserById(int userId){
        return userDao.getUserById(userId);
    }

    //用户名和密码相同时才能更改密码
    public JsonWrapper updatePassword(String userName, String password, String newPassword) {
        User user = userDao.getUserPassword(userName);
        if (user == null) {
            return new JsonWrapper("failed!用户不存在", 400);
        } else {
            if (user.getPassword().equals(password)) {
                userDao.updatePassword(userName, newPassword);
                return new JsonWrapper("success!", 200);
            } else {
                return new JsonWrapper("failed!用户密码错误", 400);
            }
        }
    }

    //用户名和密码相同时才能删除账号
    public JsonWrapper deleteUser(String userName, String password) {
        User user = userDao.getUserPassword(userName);
        if (user == null) {
            return new JsonWrapper("failed!用户不存在", 400);
        } else {
            if (user.getPassword().equals(password)) {
                userDao.deleteUserByName(userName);
                return new JsonWrapper("success!", 200);
            } else {
                return new JsonWrapper("failed!用户密码错误", 400);
            }
        }
    }


}
