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
}
