package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class LabelTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Label.class);
        Label label1 = new Label();
        label1.setId(1L);
        Label label2 = new Label();
        label2.setId(label1.getId());
        assertThat(label1).isEqualTo(label2);
        label2.setId(2L);
        assertThat(label1).isNotEqualTo(label2);
        label1.setId(null);
        assertThat(label1).isNotEqualTo(label2);
    }
}
