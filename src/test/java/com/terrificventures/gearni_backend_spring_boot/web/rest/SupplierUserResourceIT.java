package com.terrificventures.gearni_backend_spring_boot.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.terrificventures.gearni_backend_spring_boot.IntegrationTest;
import com.terrificventures.gearni_backend_spring_boot.domain.SupplierUser;
import com.terrificventures.gearni_backend_spring_boot.domain.enumeration.Role;
import com.terrificventures.gearni_backend_spring_boot.repository.SupplierUserRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SupplierUserResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SupplierUserResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PICTURE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PICTURE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_APPROVED = false;
    private static final Boolean UPDATED_IS_APPROVED = true;

    private static final Long DEFAULT_REFRESH_TOKEN = 1L;
    private static final Long UPDATED_REFRESH_TOKEN = 2L;

    private static final Long DEFAULT_REFRESH_TOKEN_EXPIRES = 1L;
    private static final Long UPDATED_REFRESH_TOKEN_EXPIRES = 2L;

    private static final Role DEFAULT_ROLE = Role.SUPERADMIN;
    private static final Role UPDATED_ROLE = Role.COMPANYADMIN;

    private static final String ENTITY_API_URL = "/api/supplier-users";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SupplierUserRepository supplierUserRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierUserMockMvc;

    private SupplierUser supplierUser;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierUser createEntity(EntityManager em) {
        SupplierUser supplierUser = new SupplierUser()
            .name(DEFAULT_NAME)
            .profilePicture(DEFAULT_PROFILE_PICTURE)
            .email(DEFAULT_EMAIL)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .isApproved(DEFAULT_IS_APPROVED)
            .refreshToken(DEFAULT_REFRESH_TOKEN)
            .refreshTokenExpires(DEFAULT_REFRESH_TOKEN_EXPIRES)
            .role(DEFAULT_ROLE);
        return supplierUser;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierUser createUpdatedEntity(EntityManager em) {
        SupplierUser supplierUser = new SupplierUser()
            .name(UPDATED_NAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .isApproved(UPDATED_IS_APPROVED)
            .refreshToken(UPDATED_REFRESH_TOKEN)
            .refreshTokenExpires(UPDATED_REFRESH_TOKEN_EXPIRES)
            .role(UPDATED_ROLE);
        return supplierUser;
    }

    @BeforeEach
    public void initTest() {
        supplierUser = createEntity(em);
    }

    @Test
    @Transactional
    void createSupplierUser() throws Exception {
        int databaseSizeBeforeCreate = supplierUserRepository.findAll().size();
        // Create the SupplierUser
        restSupplierUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierUser)))
            .andExpect(status().isCreated());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierUser testSupplierUser = supplierUserList.get(supplierUserList.size() - 1);
        assertThat(testSupplierUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplierUser.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testSupplierUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupplierUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSupplierUser.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testSupplierUser.getRefreshToken()).isEqualTo(DEFAULT_REFRESH_TOKEN);
        assertThat(testSupplierUser.getRefreshTokenExpires()).isEqualTo(DEFAULT_REFRESH_TOKEN_EXPIRES);
        assertThat(testSupplierUser.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void createSupplierUserWithExistingId() throws Exception {
        // Create the SupplierUser with an existing ID
        supplierUser.setId(1L);

        int databaseSizeBeforeCreate = supplierUserRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierUserMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierUser)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSupplierUsers() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        // Get all the supplierUserList
        restSupplierUserMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].profilePicture").value(hasItem(DEFAULT_PROFILE_PICTURE)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].isApproved").value(hasItem(DEFAULT_IS_APPROVED.booleanValue())))
            .andExpect(jsonPath("$.[*].refreshToken").value(hasItem(DEFAULT_REFRESH_TOKEN.intValue())))
            .andExpect(jsonPath("$.[*].refreshTokenExpires").value(hasItem(DEFAULT_REFRESH_TOKEN_EXPIRES.intValue())))
            .andExpect(jsonPath("$.[*].role").value(hasItem(DEFAULT_ROLE.toString())));
    }

    @Test
    @Transactional
    void getSupplierUser() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        // Get the supplierUser
        restSupplierUserMockMvc
            .perform(get(ENTITY_API_URL_ID, supplierUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierUser.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.profilePicture").value(DEFAULT_PROFILE_PICTURE))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.isApproved").value(DEFAULT_IS_APPROVED.booleanValue()))
            .andExpect(jsonPath("$.refreshToken").value(DEFAULT_REFRESH_TOKEN.intValue()))
            .andExpect(jsonPath("$.refreshTokenExpires").value(DEFAULT_REFRESH_TOKEN_EXPIRES.intValue()))
            .andExpect(jsonPath("$.role").value(DEFAULT_ROLE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingSupplierUser() throws Exception {
        // Get the supplierUser
        restSupplierUserMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewSupplierUser() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();

        // Update the supplierUser
        SupplierUser updatedSupplierUser = supplierUserRepository.findById(supplierUser.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierUser are not directly saved in db
        em.detach(updatedSupplierUser);
        updatedSupplierUser
            .name(UPDATED_NAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .isApproved(UPDATED_IS_APPROVED)
            .refreshToken(UPDATED_REFRESH_TOKEN)
            .refreshTokenExpires(UPDATED_REFRESH_TOKEN_EXPIRES)
            .role(UPDATED_ROLE);

        restSupplierUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSupplierUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSupplierUser))
            )
            .andExpect(status().isOk());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
        SupplierUser testSupplierUser = supplierUserList.get(supplierUserList.size() - 1);
        assertThat(testSupplierUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplierUser.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testSupplierUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupplierUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSupplierUser.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testSupplierUser.getRefreshToken()).isEqualTo(UPDATED_REFRESH_TOKEN);
        assertThat(testSupplierUser.getRefreshTokenExpires()).isEqualTo(UPDATED_REFRESH_TOKEN_EXPIRES);
        assertThat(testSupplierUser.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void putNonExistingSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, supplierUser.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(supplierUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(supplierUser)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSupplierUserWithPatch() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();

        // Update the supplierUser using partial update
        SupplierUser partialUpdatedSupplierUser = new SupplierUser();
        partialUpdatedSupplierUser.setId(supplierUser.getId());

        partialUpdatedSupplierUser.refreshTokenExpires(UPDATED_REFRESH_TOKEN_EXPIRES);

        restSupplierUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierUser))
            )
            .andExpect(status().isOk());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
        SupplierUser testSupplierUser = supplierUserList.get(supplierUserList.size() - 1);
        assertThat(testSupplierUser.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSupplierUser.getProfilePicture()).isEqualTo(DEFAULT_PROFILE_PICTURE);
        assertThat(testSupplierUser.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupplierUser.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testSupplierUser.getIsApproved()).isEqualTo(DEFAULT_IS_APPROVED);
        assertThat(testSupplierUser.getRefreshToken()).isEqualTo(DEFAULT_REFRESH_TOKEN);
        assertThat(testSupplierUser.getRefreshTokenExpires()).isEqualTo(UPDATED_REFRESH_TOKEN_EXPIRES);
        assertThat(testSupplierUser.getRole()).isEqualTo(DEFAULT_ROLE);
    }

    @Test
    @Transactional
    void fullUpdateSupplierUserWithPatch() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();

        // Update the supplierUser using partial update
        SupplierUser partialUpdatedSupplierUser = new SupplierUser();
        partialUpdatedSupplierUser.setId(supplierUser.getId());

        partialUpdatedSupplierUser
            .name(UPDATED_NAME)
            .profilePicture(UPDATED_PROFILE_PICTURE)
            .email(UPDATED_EMAIL)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .isApproved(UPDATED_IS_APPROVED)
            .refreshToken(UPDATED_REFRESH_TOKEN)
            .refreshTokenExpires(UPDATED_REFRESH_TOKEN_EXPIRES)
            .role(UPDATED_ROLE);

        restSupplierUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSupplierUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSupplierUser))
            )
            .andExpect(status().isOk());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
        SupplierUser testSupplierUser = supplierUserList.get(supplierUserList.size() - 1);
        assertThat(testSupplierUser.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSupplierUser.getProfilePicture()).isEqualTo(UPDATED_PROFILE_PICTURE);
        assertThat(testSupplierUser.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupplierUser.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testSupplierUser.getIsApproved()).isEqualTo(UPDATED_IS_APPROVED);
        assertThat(testSupplierUser.getRefreshToken()).isEqualTo(UPDATED_REFRESH_TOKEN);
        assertThat(testSupplierUser.getRefreshTokenExpires()).isEqualTo(UPDATED_REFRESH_TOKEN_EXPIRES);
        assertThat(testSupplierUser.getRole()).isEqualTo(UPDATED_ROLE);
    }

    @Test
    @Transactional
    void patchNonExistingSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, supplierUser.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(supplierUser))
            )
            .andExpect(status().isBadRequest());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSupplierUser() throws Exception {
        int databaseSizeBeforeUpdate = supplierUserRepository.findAll().size();
        supplierUser.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSupplierUserMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(supplierUser))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SupplierUser in the database
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSupplierUser() throws Exception {
        // Initialize the database
        supplierUserRepository.saveAndFlush(supplierUser);

        int databaseSizeBeforeDelete = supplierUserRepository.findAll().size();

        // Delete the supplierUser
        restSupplierUserMockMvc
            .perform(delete(ENTITY_API_URL_ID, supplierUser.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierUser> supplierUserList = supplierUserRepository.findAll();
        assertThat(supplierUserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
