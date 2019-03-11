package com.ccz.votesystem.controller;

import com.ccz.votesystem.annotation.NeedUserToken;
import com.ccz.votesystem.entity.JsonWrapper;
import com.ccz.votesystem.entity.Token;
import com.ccz.votesystem.entity.User;
import com.ccz.votesystem.service.TokenService;
import com.ccz.votesystem.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequestMapping("/userSystem")
@RestController
public class UserController {

    @Autowired
    private UserServices services;

    @Autowired
    private TokenService tokenService;

    @PostMapping(value = "/register")
    public Object register(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder msg = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                msg.append(fieldError.getDefaultMessage()).append(",");
            }
            return new JsonWrapper(msg.toString().substring(0, msg.length() - 1), 400);
        } else {
            return services.addUser(user);
        }
    }

    @NeedUserToken
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public Object deleteUser(@NotBlank String userName, @NotBlank String password) {
        return services.deleteUser(userName, password);
    }

    @NeedUserToken
    @RequestMapping(value = "/getUserByName", method = RequestMethod.GET)
    public Object getUserByName(@NotBlank String userName) {
        System.out.println(userName);
        return services.getUser(userName);
    }

    @NeedUserToken
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public Object changePassword(@NotBlank String userName, @NotBlank String password, @NotBlank String newPassword) {
        return services.updatePassword(userName, password, newPassword);
    }


    @PostMapping("/login")
    public Object login(@NotBlank String userName,@NotBlank String password){
        User temp = services.getUserOnly(userName);
        if(temp == null){
            return new JsonWrapper("登陆失败,用户名不存在",400);
        }else{

            if(!password.equals(temp.getPassword())){
                return new JsonWrapper("登陆失败,密码错误",400);
            }else{
                String tokenInfo = tokenService.getToken(temp);
                Token token = new Token();
                token.setToken(tokenInfo);
                return new JsonWrapper("登陆成功",200,token);
            }
        }
    }

}
