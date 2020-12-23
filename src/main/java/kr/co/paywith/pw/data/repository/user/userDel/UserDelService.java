package kr.co.paywith.pw.data.repository.user.userDel;


import kr.co.paywith.pw.data.repository.user.userDel.UserDel;
import kr.co.paywith.pw.data.repository.user.userDel.UserDelRepository;
import kr.co.paywith.pw.data.repository.user.userDel.UserDelUpdateDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDelService {

    @Autowired
    private UserDelRepository userDelRepository;


    @Autowired
    private ModelMapper modelMapper;


    /**
     * 정보 등록
     */
    @Transactional
    public UserDel create(UserDel userDel) {

        // 데이터베이스 값 갱신
        UserDel newUserDel = this.userDelRepository.save(userDel);

        return newUserDel;
    }


    /**
     * 정보 갱신
     *
     * @param userDelUpdateDto
     * @param existUserDel
     * @return
     */
    @Transactional
    public UserDel update(UserDelUpdateDto userDelUpdateDto, UserDel existUserDel) {

        // 입력값 대입
        this.modelMapper.map(userDelUpdateDto, existUserDel);

        // 데이터베이스 값 갱신
        this.userDelRepository.save(existUserDel);

        return existUserDel;
    }

}
