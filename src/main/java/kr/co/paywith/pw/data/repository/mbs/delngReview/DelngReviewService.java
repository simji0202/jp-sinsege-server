package kr.co.paywith.pw.data.repository.mbs.delngReview;

import javax.transaction.Transactional;
import kr.co.paywith.pw.data.repository.mbs.delng.Delng;
import kr.co.paywith.pw.data.repository.mbs.delng.DelngRepository;
import kr.co.paywith.pw.data.repository.mbs.mrhst.Mrhst;
import kr.co.paywith.pw.data.repository.mbs.mrhst.MrhstRepository;
import kr.co.paywith.pw.data.repository.mbs.mrhstOrdr.MrhstOrdr;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DelngReviewService {

  @Autowired
  DelngReviewRepository delngReviewRepository;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  private MrhstRepository mrhstRepository;

  @Autowired
  private DelngRepository delngRepository;

  /**
   * 정보 등록
   */
  @Transactional
  public DelngReview create(DelngReview delngReview) {

    // 데이터베이스 값 갱신
    DelngReview newDelngReview = this.delngReviewRepository.save(delngReview);

    Delng delng = delngRepository.findById(newDelngReview.getDelngId()).get();
    Mrhst mrhst = mrhstRepository.findById(delng.getMrhstId()).get();

    // 매장 리뷰 점수 갱신
    if (mrhst.getMrhstOrdr() == null) {
      mrhst.setMrhstOrdr(new MrhstOrdr());
    }
    MrhstOrdr mrhstOrdr = mrhst.getMrhstOrdr();

    // 점수 재 계산
    calcMrhstOrdrReviewScore(newDelngReview, mrhstOrdr);
    mrhstRepository.save(mrhst);

    return newDelngReview;
  }

  /**
   * 매장 리뷰 점수 재계산
   *
   * @param delngReview
   * @param mrhstOrdr
   */
  private void calcMrhstOrdrReviewScore(DelngReview delngReview, MrhstOrdr mrhstOrdr) {
    switch (delngReview.getScore()) {
      case 0:
        mrhstOrdr.setReview0Cnt(mrhstOrdr.getReview0Cnt() + 1);
        break;
      case 1:
        mrhstOrdr.setReview1Cnt(mrhstOrdr.getReview1Cnt() + 1);
        break;
      case 2:
        mrhstOrdr.setReview2Cnt(mrhstOrdr.getReview2Cnt() + 1);
        break;
      case 3:
        mrhstOrdr.setReview3Cnt(mrhstOrdr.getReview3Cnt() + 1);
        break;
      case 4:
        mrhstOrdr.setReview4Cnt(mrhstOrdr.getReview4Cnt() + 1);
        break;
      case 5:
        mrhstOrdr.setReview5Cnt(mrhstOrdr.getReview5Cnt() + 1);
        break;
    }
    mrhstOrdr.setReviewScore(
        (mrhstOrdr.getReview1Cnt() +
            mrhstOrdr.getReview2Cnt() * 2 +
            mrhstOrdr.getReview3Cnt() * 3 +
            mrhstOrdr.getReview4Cnt() * 4 +
            mrhstOrdr.getReview5Cnt() * 5) /
            (mrhstOrdr.getReview1Cnt() +
                mrhstOrdr.getReview2Cnt() +
                mrhstOrdr.getReview3Cnt() +
                mrhstOrdr.getReview4Cnt() +
                mrhstOrdr.getReview5Cnt())
    );
  }


  /**
   * 정보 갱신
   */
  @Transactional
  public DelngReview update(DelngReviewUpdateDto delngReviewUpdateDto,
      DelngReview existDelngReview) {

    // 입력값 대입
    this.modelMapper.map(delngReviewUpdateDto, existDelngReview);

    // 데이터베이스 값 갱신
    this.delngReviewRepository.save(existDelngReview);

    Delng delng = delngRepository.findById(existDelngReview.getDelngId()).get();
    Mrhst mrhst = mrhstRepository.findById(delng.getMrhstId()).get();

    // 매장 리뷰 점수 갱신
    if (mrhst.getMrhstOrdr() == null) {
      mrhst.setMrhstOrdr(new MrhstOrdr());
    }
    MrhstOrdr mrhstOrdr = mrhst.getMrhstOrdr();

    // 점수 재 계산
    calcMrhstOrdrReviewScore(existDelngReview, mrhstOrdr);

    return existDelngReview;
  }

}
