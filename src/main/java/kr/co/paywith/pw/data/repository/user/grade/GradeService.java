package kr.co.paywith.pw.data.repository.user.grade;


import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;


    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     */
    @Transactional
    public Grade create(Grade grade) {

        // 데이터베이스 값 갱신
        Grade newGrade = this.gradeRepository.save(grade);

        return newGrade;
    }


    /**
     * 정보 갱신
     *
     * @param gradeUpdateDto
     * @param existGrade
     * @return
     */
    @Transactional
    public Grade update(GradeUpdateDto gradeUpdateDto, Grade existGrade) {

        // 입력값 대입
        this.modelMapper.map(gradeUpdateDto, existGrade);

        // 데이터베이스 값 갱신
        this.gradeRepository.save(existGrade);

        return existGrade;
    }

    /**
     * 회원 가입 시 최초 등급 조회
     *
     * @return 최초 등급
     */
    public Grade findFirstGrade() {
        Optional<Grade> optionalGrade = gradeRepository.findBySort(0);
        if (optionalGrade.isEmpty()) {
            // TODO kms: Exception 확인
            throw new RuntimeException("등급 설정 오류");
        }
        return optionalGrade.get();
    }

    /**
     * 현재 등급 기준 다음 등급 조회
     *
     * @param currentGrade 현재 등급
     * @return 다음 등급, 없으면 null
     */
    public Grade getNextGrade(Grade currentGrade) {
        if (currentGrade.getSort() == null) {
            return null;
        }

        return gradeRepository.findBySort(currentGrade.getSort() + 1).orElse(null);
    }

    /**
     * 현재 등급 기준 이전 등급 조회
     *
     * @param currentGrade 현재 등급
     * @return 이전 등급, 없으면 null
     */
    public Grade getPreviousGrade(Grade currentGrade) {
        if (currentGrade.getSort() == null || currentGrade.getSort() == 0) {
            return null;
        }

        return gradeRepository.findBySort(currentGrade.getSort() - 1).orElse(null);

    }
}
