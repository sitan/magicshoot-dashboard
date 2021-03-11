package nl.magicshoot.dashboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import nl.magicshoot.dashboard.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MediaInTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MediaIn.class);
        MediaIn mediaIn1 = new MediaIn();
        mediaIn1.setId(1L);
        MediaIn mediaIn2 = new MediaIn();
        mediaIn2.setId(mediaIn1.getId());
        assertThat(mediaIn1).isEqualTo(mediaIn2);
        mediaIn2.setId(2L);
        assertThat(mediaIn1).isNotEqualTo(mediaIn2);
        mediaIn1.setId(null);
        assertThat(mediaIn1).isNotEqualTo(mediaIn2);
    }
}
