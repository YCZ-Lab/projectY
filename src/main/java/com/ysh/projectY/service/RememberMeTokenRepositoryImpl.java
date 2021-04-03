package com.ysh.projectY.service;

import com.ysh.projectY.dao.RememberMeTokenDao;
import com.ysh.projectY.entity.RememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class RememberMeTokenRepositoryImpl implements PersistentTokenRepository {

    final RememberMeTokenDao rememberMeTokenDao;

    public RememberMeTokenRepositoryImpl(RememberMeTokenDao rememberMeTokenDao) {
        this.rememberMeTokenDao = rememberMeTokenDao;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void createNewToken(PersistentRememberMeToken token) {
//        System.out.println("CustomRememberMeTokenRepositoryImpl.createNewToken(PersistentRememberMeToken token=" + token + ")");
        rememberMeTokenDao.createNewToken(token.getUsername(), token.getSeries(), token.getTokenValue(), token.getDate());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void updateToken(String series, String token, Date lastUsedDateTime) {
//        System.out.println("CustomRememberMeTokenRepositoryImpl.updateToken(series=" + series + ",token=" + token + ",lastUsedDateTime=" + lastUsedDateTime + ")");
        rememberMeTokenDao.updateToken(series, token, lastUsedDateTime);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String series) {
//        System.out.println("CustomRememberMeTokenRepositoryImpl.getTokenForSeries(seriesId=" + series + ")");
        RememberMeToken rememberMeToken = rememberMeTokenDao.getTokenForSeries(series);
        if (rememberMeToken != null) {
            PersistentRememberMeToken persistentRememberMeToken = new PersistentRememberMeToken(rememberMeToken.getUsername(), rememberMeToken.getSeries(), rememberMeToken.getToken(), rememberMeToken.getLastUsedDateTime());
            return persistentRememberMeToken;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void removeUserTokens(String username) {
//        System.out.println("CustomRememberMeTokenRepositoryImpl.removeUserTokens(username=" + username + ")");
        rememberMeTokenDao.removeUserTokens(username);
    }
}
