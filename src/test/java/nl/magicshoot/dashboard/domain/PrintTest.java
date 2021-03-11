package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class PrintTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Print.class);
        Print print1 = new Print();
        print1.setId(1L);
        Print print2 = new Print();
        print2.setId(print1.getId());
        assertThat(print1).isEqualTo(print2);
        print2.setId(2L);
        assertThat(print1).isNotEqualTo(print2);
        print1.setId(null);
        assertThat(print1).isNotEqualTo(print2);
    }
}
