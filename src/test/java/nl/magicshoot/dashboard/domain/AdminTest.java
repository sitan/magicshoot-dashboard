package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AdminTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Admin.class);
        Admin admin1 = new Admin();
        admin1.setId(1L);
        Admin admin2 = new Admin();
        admin2.setId(admin1.getId());
        assertThat(admin1).isEqualTo(admin2);
        admin2.setId(2L);
        assertThat(admin1).isNotEqualTo(admin2);
        admin1.setId(null);
        assertThat(admin1).isNotEqualTo(admin2);
    }
}
