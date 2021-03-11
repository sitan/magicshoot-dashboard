package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MediaOutTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaOut.class);
        MediaOut mediaOut1 = new MediaOut();
        mediaOut1.setId(1L);
        MediaOut mediaOut2 = new MediaOut();
        mediaOut2.setId(mediaOut1.getId());
        assertThat(mediaOut1).isEqualTo(mediaOut2);
        mediaOut2.setId(2L);
        assertThat(mediaOut1).isNotEqualTo(mediaOut2);
        mediaOut1.setId(null);
        assertThat(mediaOut1).isNotEqualTo(mediaOut2);
    }
}
