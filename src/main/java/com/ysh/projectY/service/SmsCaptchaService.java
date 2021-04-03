package com.ysh.projectY.service;

import com.ysh.projectY.dao.SmsCaptchaDao;
import com.ysh.projectY.entity.SmsCaptcha;
import com.ysh.projectY.entity.User;
import com.ysh.projectY.utils.MethodResponse;
import com.ysh.projectY.utils.SmsCaptchaStatus;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.services.sns.model.SnsException;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SmsCaptchaService {

    final SmsCaptchaDao smsCaptchaDao;
    final UserDetailService userDetailService;

    public SmsCaptchaService(SmsCaptchaDao smsCaptchaDao, UserDetailService userDetailService) {
        this.smsCaptchaDao = smsCaptchaDao;
        this.userDetailService = userDetailService;
    }

    public MethodResponse sendSmsCaptchaVerify(String mobilePhone, String source, String clientIp) {
        if ("login".equals(source)) {
            Optional<User> user = userDetailService.findByMobilePhone(mobilePhone);
            if (user.isEmpty()) {
                return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptchaVerify.failure.mobilePhoneNotExist", "手机号码不存在!");
            }
            List<SmsCaptcha> smsCount = smsCaptchaDao.findBySourceAndClientIpAndCreateDateTimeGreaterThan(source, clientIp, new Timestamp(System.currentTimeMillis() - (60 * 60 * 24 * 1000)));
            if (!smsCount.isEmpty() && smsCount.size() >= 100) {
                return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptchaVerify.failure.login.MaximumNumberOfSending", "登录 - 发送短信验证码达到最大发送次数!");
            }
        } else if ("register".equals(source)) {
            Optional<User> user = userDetailService.findByMobilePhone(mobilePhone);
            if (user.isPresent()) {
                return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptchaVerify.failure.mobilePhoneAlreadyRegistered", "手机号码已经被注册!");
            }
            List<SmsCaptcha> smsCount = smsCaptchaDao.findBySourceAndClientIpAndCreateDateTimeGreaterThan(source, clientIp, new Timestamp(System.currentTimeMillis() - (60 * 60 * 24 * 1000)));
            if (!smsCount.isEmpty() && smsCount.size() >= 100) {
                return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptchaVerify.failure.register.MaximumNumberOfSending", "注册 - 发送短信验证码达到最大发送次数!");
            }
        }
        Optional<SmsCaptcha> optional = smsCaptchaDao.findLastValidSmsCaptchaByMobilePhone(mobilePhone);
        if (optional.isPresent()) {
            SmsCaptcha lastValidSmsCaptcha = optional.get();
            if (((System.currentTimeMillis() - lastValidSmsCaptcha.getCreateDateTime().getTime()) / 1000) < 60) {
                return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptchaVerify.failure.frequently", "发送短信验证码太频繁!");
            }
        }
        return MethodResponse.success("projectY.SmsCaptchaService.sendSmsCaptchaVerify.success", "发送短信验证码-验证通过!");
    }

    public MethodResponse sendSmsCaptcha(SmsCaptcha smsCaptcha) {
        // 生成验证码
        Random random = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        smsCaptcha.setCaptcha(code.toString());
        // 设置创建时间
        smsCaptcha.setCreateDateTime(new Timestamp(System.currentTimeMillis()));
        // 设置条目状态
        smsCaptcha.setStatus(SmsCaptchaStatus.CREATED.name());
        // 设置更新时间
        smsCaptcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
        try {
//            String mobilePhone = smsCaptcha.getMobilePhone();
//            mobilePhone = "+1" + mobilePhone;
//            SnsClient snsClient = SnsClient.builder().region(Region.US_EAST_2).build();
//            PublishRequest request = PublishRequest.builder().message("[projectY]: " + smsCaptcha.getCaptcha()).phoneNumber(mobilePhone).build();
//            PublishResponse response = snsClient.publish(request);
//            System.out.println("Status was " + response.sdkHttpResponse().statusCode());
            smsCaptchaDao.saveAndFlush(smsCaptcha);
//            System.out.println("验证码: " + smsCaptcha.getCaptcha());
        } catch (SnsException e) {
            return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptcha.failure.SnsException", e.toString());
        } catch (AwsServiceException e) {
            return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptcha.failure.AwsServiceException", e.toString());
        } catch (SdkServiceException e) {
            return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptcha.failure.SdkServiceException", e.toString());
        } catch (SdkException e) {
            return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptcha.failure.SdkException", e.toString());
        } catch (Exception e) {
            return MethodResponse.failure("projectY.SmsCaptchaService.sendSmsCaptcha.failure.Exception", e.toString());
        }
        return MethodResponse.success("projectY.SmsCaptchaService.sendSmsCaptcha.success");
    }

    public MethodResponse verifySmsCaptcha(String mobilePhone, String smsCaptcha) {
        Optional<SmsCaptcha> optional = smsCaptchaDao.findLastValidSmsCaptchaByMobilePhone(mobilePhone);
        if (optional.isEmpty()) {
            return MethodResponse.failure("projectY.valid.SmsCaptcha.failure.smsCaptchaNotFound", "找不到验证码, 请重新获取!");
        }
        SmsCaptcha lastValidSmsCaptcha = optional.get();
        final List<SmsCaptcha> smsCaptchas = smsCaptchaDao.findByMobilePhoneAndStatus(mobilePhone, SmsCaptchaStatus.CREATED.name());
        if (smsCaptcha.length() > 1) {
            for (SmsCaptcha captcha : smsCaptchas) {
                if (!captcha.equals(lastValidSmsCaptcha)) {
                    captcha.setStatus(SmsCaptchaStatus.COVERED.name());
                    captcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
                    smsCaptchaDao.save(captcha);
                }
            }
        }
        if (((System.currentTimeMillis() - lastValidSmsCaptcha.getCreateDateTime().getTime()) / 1000) > 60) {
            lastValidSmsCaptcha.setStatus(SmsCaptchaStatus.EXPIRED.name());
            lastValidSmsCaptcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
            smsCaptchaDao.save(lastValidSmsCaptcha);
            return MethodResponse.failure("projectY.valid.SmsCaptcha.failure.timeout", "验证码超时, 请重新获取!");
        } else {
            if (lastValidSmsCaptcha.getVerifiedTimes() > 3) {
                lastValidSmsCaptcha.setStatus(SmsCaptchaStatus.EXCEED.name());
                lastValidSmsCaptcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
                smsCaptchaDao.save(lastValidSmsCaptcha);
                return MethodResponse.failure("projectY.valid.SmsCaptcha.failure.maximumNumberOfVerificationsExceeded", "超过最大验证次数, 请重新获取!");
            } else {
                if (!lastValidSmsCaptcha.getCaptcha().equalsIgnoreCase(smsCaptcha)) {
                    lastValidSmsCaptcha.setVerifiedTimes(lastValidSmsCaptcha.getVerifiedTimes() + 1);
                    lastValidSmsCaptcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
                    smsCaptchaDao.save(lastValidSmsCaptcha);
                    return MethodResponse.failure("projectY.valid.SmsCaptcha.failure.mismatch", "验证失败, 请重新输入验证码!");
                } else {
                    lastValidSmsCaptcha.setStatus(SmsCaptchaStatus.USED.name());
                    lastValidSmsCaptcha.setUpdateDateTime(new Timestamp(System.currentTimeMillis()));
                    smsCaptchaDao.save(lastValidSmsCaptcha);
                    return MethodResponse.success("projectY.valid.SmsCaptcha.success", "验证成功!");
                }
            }
        }
    }
}
