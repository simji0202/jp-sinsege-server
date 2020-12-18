package kr.co.paywith.pw.data.repository.user.userInfo;

import kr.co.paywith.pw.data.repository.admin.Admin;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

     @Autowired
    private ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * 일반 유저 정보 신규 등록
     * @return
     */
    @Transactional
    public UserInfo save(UserInfo userInfo) {

        userInfo.setUserPw(this.passwordEncoder.encode(userInfo.getUserPw()));
        return this.userInfoRepository.save(userInfo);
    }


    /**
     * 일반 유저 정보 갱신
     * @return
     */
    @Transactional
    public UserInfo update(UserInfoUpdateDto userInfoUpdateDto, UserInfo userInfo) {

        // 기존 List 항목 초기 설정
        userInfo.getUserAppList().clear();

         // 입력값 대입
        this.modelMapper.map(userInfoUpdateDto, userInfo);
        return this.userInfoRepository.save(userInfo);
    }


    /**
     * @return
     */
    @Transactional
    public UserInfo updatePw(UserInfo userInfo) {

        userInfo.setUserPw(this.passwordEncoder.encode(userInfo.getUserPw()));
        return this.userInfoRepository.save(userInfo);
    }




}
