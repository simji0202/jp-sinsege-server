package kr.co.paywith.pw.data.repository.admin;


import kr.co.paywith.pw.common.BaseControllerTest;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


public class AdminServiceTest  extends BaseControllerTest {


    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    AdminService adminService;


    @Autowired
    AdminRepository adminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private WebApplicationContext webApplicationContext;


    // 403 Error 대응
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

     //    this.adminRepository.deleteAll();
        // this.accountRepository.deleteAll();

    }

    @Test
    public void findbyUserName () {

        String userId = "simji";
        String passwd = "1234";

        Admin admin  = Admin.builder()
                .adminNm("채원")
                .adminId(userId)
                .adminPw(passwd)
                .roles(Set.of(AdminRole.ADMIN_MASTER))

                .build();


        this.adminService.create(admin);

        // Given
        UserDetailsService userDetailsService = (UserDetailsService) adminService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

        //assertThat(userDetails.getPassword()).isEqualTo(passwd);
        assertThat(this.passwordEncoder.matches(passwd, userDetails.getPassword())).isTrue();

    }


    // 예외처리 확인
    //@Test (expected = UsernameNotFoundException.class)

    @Test
    public void findByUserIdFail() {
        String userId = "simji0202";
        String userPw = "12345678";
        Optional<Admin> admin = adminRepository.findByAdminId(userId);

        Admin exixt = admin.get();

        System.out.println("Login Test OK ");

    }




//    @Test
//    public void findByUserIdFail2() {
//        // Expected ( 먼저 예측 Exception을 기술 )
//        String userId = "IamfailUser";
//        expectedException.expect(UsernameNotFoundException.class);
//        expectedException.expectMessage(Matchers.containsString(userId));
//
//        // When
//        adminService.loadUserByUsername(userId);
//    }


}