package kr.co.paywith.pw.data.repository.user.userInfo;

import com.querydsl.core.BooleanBuilder;
import java.time.ZonedDateTime;
import javax.transaction.Transactional;
import kr.co.paywith.pw.common.StringUtil;
import kr.co.paywith.pw.data.repository.admin.AdminRepository;
import kr.co.paywith.pw.data.repository.enumeration.CertTypeCd;
import kr.co.paywith.pw.data.repository.mbs.mrhst.mrhstTrmnl.MrhstTrmnlRepository;
import kr.co.paywith.pw.data.repository.user.grade.GradeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserInfoService {

    @Autowired
    UserInfoRepository userInfoRepository;

    @Autowired
    GradeService gradeService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    MrhstTrmnlRepository mrhstTrmnlRepository;

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

        userInfo = this.userInfoRepository.save(userInfo);

        // 스탬프 번호 생성. 중복 방지를 위해 db 저장 후 ID를 활용한다
        boolean isEndMakeNo = false;
        do {
            String noRule = "2";
            noRule += StringUtils.leftPad("" + userInfo.getId(), 8, "0"); //20000000
            noRule = StringUtils.rightPad(noRule, 15, RandomStringUtils.randomNumeric(12));
            String stampNo = StringUtil.makeNo(noRule);
            if (userInfoRepository.findUserInfoByStampNo(stampNo).isEmpty()) {
                isEndMakeNo = true;
                userInfo.setStampNo(stampNo);
            }
        } while(!isEndMakeNo);

        // 최초 등급 설정
        userInfo.setGrade(gradeService.findFirstGrade());

        // TODO 신규 회원 쿠폰 발급

        return userInfoRepository.save(userInfo);
    }


    /**
     * 일반 유저 정보 갱신
     * @return
     */
    @Transactional
    public UserInfo update(UserInfoUpdateDto userInfoUpdateDto, UserInfo userInfo) {

        // kms: 신규 클라이언트에서는 UserApp 직접 CRUD 예정이므로 코드 삭제
//        // 기존 List 항목 초기 설정
//        userInfo.getUserAppList().clear();

        if (!userInfoUpdateDto.getCertTypeCd().equals(userInfo.getCertTypeCd())) {
            // GUEST에서 일반 회원이 될 경우 최초 가입 쿠폰 발급. 반대의 경우는 오류 처리
            if (userInfo.getCertTypeCd().equals(CertTypeCd.GUEST)) {
                // TODO 신규 회원 쿠폰 발급
            } else if (userInfoUpdateDto.getCertTypeCd().equals(CertTypeCd.GUEST)) {
                // TODO kms: Exception 확인
                throw new RuntimeException();
            }
        }

        // 활설 - 비활성 상태 바꿀 때 outDttm 변경
        if (!userInfoUpdateDto.getActiveFl().equals(userInfo.getActiveFl())) {
            if (!userInfoUpdateDto.getActiveFl()) { // 활성 -> 비활성
                userInfo.setOutDttm(ZonedDateTime.now());
            } else {
                userInfo.setOutDttm(null);
            }
        }

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


    /**
     * 회원의 탈퇴 처리
     *
     * @param userInfo
     */
    @Transactional
    public void delete(UserInfo userInfo) {
        // 활설 - 비활성 상태 바꿀 때 outDttm 변경
        userInfo.setOutDttm(ZonedDateTime.now());
        userInfo.setActiveFl(false);
    }

}
