package kr.co.paywith.pw.data.repository.user.grade;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class GradeUpService {

    @Autowired
    private GradeUpRepository gradeUpRepository;


    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     */
    @Transactional
    public GradeUp create(GradeUp gradeUp) {

        // 데이터베이스 값 갱신
        GradeUp newGradeUp = this.gradeUpRepository.save(gradeUp);

        return newGradeUp;
    }


    /**
     * 정보 갱신
     */
    @Transactional
    public GradeUp update(GradeUpUpdateDto gradeUpUpdateDto, GradeUp existGradeUp) {

        // 입력값 대입
        this.modelMapper.map(gradeUpUpdateDto, existGradeUp);

        // 데이터베이스 값 갱신
        this.gradeUpRepository.save(existGradeUp);

        return existGradeUp;
    }

}
