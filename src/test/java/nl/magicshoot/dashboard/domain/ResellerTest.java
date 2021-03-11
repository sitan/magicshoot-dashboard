package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class ResellerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Reseller.class);
        Reseller reseller1 = new Reseller();
        reseller1.setId(1L);
        Reseller reseller2 = new Reseller();
        reseller2.setId(reseller1.getId());
        assertThat(reseller1).isEqualTo(reseller2);
        reseller2.setId(2L);
        assertThat(reseller1).isNotEqualTo(reseller2);
        reseller1.setId(null);
        assertThat(reseller1).isNotEqualTo(reseller2);
    }
}
