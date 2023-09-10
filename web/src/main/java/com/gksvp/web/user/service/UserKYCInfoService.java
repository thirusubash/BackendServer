package com.gksvp.web.user.service;

import com.gksvp.web.user.entity.UserKYCInfo;
import com.gksvp.web.user.repository.UserKYCInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserKYCInfoService {
    private final UserKYCInfoRepository userKYCInfoRepository;


    public UserKYCInfoService(UserKYCInfoRepository userKYCInfoRepository) {
        this.userKYCInfoRepository = userKYCInfoRepository;
    }

    public List<UserKYCInfo> getAllUserKYCInfos() {
        return userKYCInfoRepository.findAll();
    }

    public UserKYCInfo getUserKYCInfoById(Long userKYCInfoId) {
        return userKYCInfoRepository.findById(userKYCInfoId).orElse(null);
    }

    public void saveUserKYCInfo(UserKYCInfo userKYCInfo) {
        userKYCInfoRepository.save(userKYCInfo);
    }

    public void deleteUserKYCInfo(Long userKYCInfoId) {
        userKYCInfoRepository.deleteById(userKYCInfoId);
    }
}
