package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PaymentsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payments.class);
        Payments payments1 = new Payments();
        payments1.setId(1L);
        Payments payments2 = new Payments();
        payments2.setId(payments1.getId());
        assertThat(payments1).isEqualTo(payments2);
        payments2.setId(2L);
        assertThat(payments1).isNotEqualTo(payments2);
        payments1.setId(null);
        assertThat(payments1).isNotEqualTo(payments2);
    }
}
