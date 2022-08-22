package com.terrificventures.gearni_backend_spring_boot.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.terrificventures.gearni_backend_spring_boot.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SupplierUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SupplierUser.class);
        SupplierUser supplierUser1 = new SupplierUser();
        supplierUser1.setId(1L);
        SupplierUser supplierUser2 = new SupplierUser();
        supplierUser2.setId(supplierUser1.getId());
        assertThat(supplierUser1).isEqualTo(supplierUser2);
        supplierUser2.setId(2L);
        assertThat(supplierUser1).isNotEqualTo(supplierUser2);
        supplierUser1.setId(null);
        assertThat(supplierUser1).isNotEqualTo(supplierUser2);
    }
}
