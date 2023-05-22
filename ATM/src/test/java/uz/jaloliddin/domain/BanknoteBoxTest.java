package uz.jaloliddin.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import uz.jaloliddin.web.rest.TestUtil;

class BanknoteBoxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BanknoteBox.class);
        BanknoteBox banknoteBox1 = new BanknoteBox();
        banknoteBox1.setId(1L);
        BanknoteBox banknoteBox2 = new BanknoteBox();
        banknoteBox2.setId(banknoteBox1.getId());
        assertThat(banknoteBox1).isEqualTo(banknoteBox2);
        banknoteBox2.setId(2L);
        assertThat(banknoteBox1).isNotEqualTo(banknoteBox2);
        banknoteBox1.setId(null);
        assertThat(banknoteBox1).isNotEqualTo(banknoteBox2);
    }
}
