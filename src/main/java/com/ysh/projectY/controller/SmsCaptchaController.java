package com.ysh.projectY.controller;

import com.ysh.projectY.form.SmsCaptcha;
import com.ysh.projectY.service.SmsCaptchaService;
import com.ysh.projectY.utils.JsonResponse;
import com.ysh.projectY.utils.MethodResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "${projectY.api-base-path}")
public class SmsCaptchaController {
    final SmsCaptchaService smsCaptchaService;

    public SmsCaptchaController(SmsCaptchaService smsCaptchaService) {
        this.smsCaptchaService = smsCaptchaService;
    }

    /**
     * 发送短信验证码
     * POST    /smsCaptcha        发送短信验证码
     *
     * @param smsCaptchaForm
     * @return http 响应
     */
    @PostMapping("/smsCaptcha")
    public HttpEntity<?> sendSms(@Valid @RequestBody SmsCaptcha smsCaptchaForm, HttpServletRequest req) {
//        System.out.println("SmsCaptchaForm验证完成!");
        MethodResponse methodResponse = smsCaptchaService.sendSmsCaptchaVerify(smsCaptchaForm.getMobilePhone(), smsCaptchaForm.getSource(), req.getRemoteAddr());
        if (!methodResponse.isSuccess()) {
            return new ResponseEntity<>(JsonResponse.failure(HttpStatus.UNPROCESSABLE_ENTITY.value(), methodResponse.getI18nMessageKey(), methodResponse.getData(), methodResponse.getDetail()), HttpStatus.OK);
        }
        com.ysh.projectY.entity.SmsCaptcha smsCaptcha = new com.ysh.projectY.entity.SmsCaptcha();
        smsCaptcha.setMobilePhone(smsCaptchaForm.getMobilePhone());
        smsCaptcha.setSource(smsCaptchaForm.getSource());
        smsCaptcha.setClientIp(req.getRemoteAddr());
        methodResponse = smsCaptchaService.sendSmsCaptcha(smsCaptcha);
        if (methodResponse.isSuccess()) {
            return new ResponseEntity<>(JsonResponse.success(HttpStatus.CREATED.value(), methodResponse.getI18nMessageKey()), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(JsonResponse.failure(HttpStatus.UNPROCESSABLE_ENTITY.value(), methodResponse.getI18nMessageKey(), methodResponse.getData(), methodResponse.getDetail()), HttpStatus.OK);
        }
    }
}
