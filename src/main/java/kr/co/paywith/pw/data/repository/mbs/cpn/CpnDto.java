package kr.co.paywith.pw.data.repository.mbs.cpn;

import kr.co.paywith.pw.data.repository.user.userInfo.UserInfoDto;
import lombok.Data;

/**
 * 가맹점
 */
@Data
public class CpnDto {


//    /**
//     * 쿠폰 일련번호
//     */
//    private Integer id;

    // kms: 삭제가능. 서버에서 생성하므로 주석
//    /**
//     * 쿠폰 번호
//     */
//    private String cpnNo;

    // kms: 삭제가능. 서버에서 생성하므로 주석
//    /**
//     * 쿠폰 상태 코드
//     */
//    @Enumerated(EnumType.STRING)
//    private CpnSttsCd cpnSttsCd;

    // kms: 삭제가능. 서버에서 cpnIssu 정보를 바탕으로 생성하므로 주석
//    /**
//     * 쿠폰 종류
//     */
//    private CpnMaster cpnMaster;

    /**
     * 쿠폰 소지 회원
     */
    private UserInfoDto userInfoDto;


//    /**
//     * 쿠폰 발급(대장)
//     */
//    @ManyToOne
//    private CpnIssu cpnIssu;

    // kms: 삭제가능. 회원이 read 했을 때 서버에서 바꾸는 상태값
//    /**
//     * 확인 여부
//     */
//    private Boolean readFl = false;

}
