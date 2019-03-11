package com.ccz.votesystem.interceptor;

import com.ccz.votesystem.VotesystemApplication;
import com.ccz.votesystem.annotation.Encrypt;
import com.ccz.votesystem.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.security.PublicKey;

/*
加密
 */
@Slf4j
@RestControllerAdvice
public class ParamEncryptResponseBodyAdvice implements ResponseBodyAdvice {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParamEncryptResponseBodyAdvice.class);
    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return returnType.hasMethodAnnotation(Encrypt.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        //此处进行加密数据
        PublicKey publicKey = RSAUtil.string2PublicKey(VotesystemApplication.publicKeyStr);
        //用公钥加密
        byte[] publicEncrypt = RSAUtil.publicEncrypt(body.toString().getBytes(), publicKey);
        //加密后的内容Base64编码
        return RSAUtil.byte2Base64(publicEncrypt);
    }
}
