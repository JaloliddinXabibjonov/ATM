package uz.jaloliddin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.jaloliddin.web.rest.TestUtil;

class BankomatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bankomat.class);
        Bankomat bankomat1 = new Bankomat();
        bankomat1.setId(1L);
        Bankomat bankomat2 = new Bankomat();
        bankomat2.setId(bankomat1.getId());
        assertThat(bankomat1).isEqualTo(bankomat2);
        bankomat2.setId(2L);
        assertThat(bankomat1).isNotEqualTo(bankomat2);
        bankomat1.setId(null);
        assertThat(bankomat1).isNotEqualTo(bankomat2);
    }
}
