package kr.co.paywith.pw.data.repository.admin;



import kr.co.paywith.pw.common.AppPropertiesTest;
import kr.co.paywith.pw.common.BaseControllerTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;


public class
AdminTest extends BaseControllerTest {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    AdminService adminService;


    @Autowired
    AppPropertiesTest appPropertiesTest;


    @Test
    public void builder () {

        Admin admin = Admin.builder()
                .adminId(appPropertiesTest.getAdminId())
                .adminPw(appPropertiesTest.getAdminPw())
                .build();


        
        assertThat(admin).isNotNull();
    }

    @Test
    public  void




    createAdmin() {

        // Given
        IntStream.range(0, 1).forEach(this::generateAdmin);
    }


    private Admin generateAdmin(int i) {


        Admin admin = Admin.builder()
                .adminId("simji")
                .adminPw("1234")
                .adminNm("paywith")
                .adminType(AdminType.ADMIN)
                .roles(Set.of(AdminRole.ADMIN_MASTER))
                .build();

        return this.adminService.saveAdmin(admin);
    }
}
