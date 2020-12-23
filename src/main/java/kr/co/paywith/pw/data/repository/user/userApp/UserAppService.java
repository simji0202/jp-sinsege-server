package kr.co.paywith.pw.data.repository.user.userApp;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserAppService {

    @Autowired
    private UserAppRepository userAppRepository;


    @Autowired
    private ModelMapper modelMapper;


    /**
     * 정보 등록
     */
    @Transactional
    public UserApp create(UserApp userApp) {

        // 데이터베이스 값 갱신
        UserApp newUserApp = this.userAppRepository.save(userApp);

        return newUserApp;
    }


    /**
     * 정보 갱신
     *
     * @param userAppUpdateDto
     * @param existUserApp
     * @return
     */
    @Transactional
    public UserApp update(UserAppUpdateDto userAppUpdateDto, UserApp existUserApp) {

        // 입력값 대입
        this.modelMapper.map(userAppUpdateDto, existUserApp);

        // 데이터베이스 값 갱신
        this.userAppRepository.save(existUserApp);

        return existUserApp;
    }

}
