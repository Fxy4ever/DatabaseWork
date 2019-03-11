package com.ccz.votesystem.interceptor;

import com.ccz.votesystem.VotesystemApplication;
import com.ccz.votesystem.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

/*
    解密
 */
@Slf4j
@RestControllerAdvice
public class ParamEncryptRequestBodyAdvice implements RequestBodyAdvice {

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return new HttpInputMessage() {
            @Override
            public InputStream getBody() throws IOException {
                //此处进行解密数据
                String str = IOUtils.toString(httpInputMessage.getBody());
                PrivateKey privateKey = RSAUtil.string2PrivateKey(VotesystemApplication.privateKeyStr);
                //加密后的内容Base64解码
                byte[] base642Byte = RSAUtil.base642Byte(str);
                //用私钥解密
                byte[] privateDecrypt = RSAUtil.privateDecrypt(base642Byte, privateKey);
                if (privateDecrypt != null) {//解密成功，返回解密数据
                    return new ByteArrayInputStream(new String(privateDecrypt).getBytes(StandardCharsets.UTF_8));
                }else{
                    return new ByteArrayInputStream(IOUtils.toString(httpInputMessage.getBody()).getBytes(StandardCharsets.UTF_8));
                }
            }

            @Override
            public HttpHeaders getHeaders() {
                return httpInputMessage.getHeaders();
            }
        };
    }

    @Override
    public Object afterBodyRead(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }
}