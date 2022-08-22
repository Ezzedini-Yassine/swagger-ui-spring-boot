package com.terrificventures.gearni_backend_spring_boot.domain;

import com.terrificventures.gearni_backend_spring_boot.domain.enumeration.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A SupplierUser.
 */
@Entity
@Table(name = "supplier_user")
public class SupplierUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    /**
     * The firstname attribute.
     */
    @Schema(description = "The firstname attribute.")
    @Column(name = "name")
    private String name;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_approved")
    private Boolean isApproved;

    @Column(name = "refresh_token")
    private Long refreshToken;

    @Column(name = "refresh_token_expires")
    private Long refreshTokenExpires;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SupplierUser id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public SupplierUser name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public SupplierUser profilePicture(String profilePicture) {
        this.setProfilePicture(profilePicture);
        return this;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getEmail() {
        return this.email;
    }

    public SupplierUser email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public SupplierUser phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public SupplierUser isApproved(Boolean isApproved) {
        this.setIsApproved(isApproved);
        return this;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public Long getRefreshToken() {
        return this.refreshToken;
    }

    public SupplierUser refreshToken(Long refreshToken) {
        this.setRefreshToken(refreshToken);
        return this;
    }

    public void setRefreshToken(Long refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshTokenExpires() {
        return this.refreshTokenExpires;
    }

    public SupplierUser refreshTokenExpires(Long refreshTokenExpires) {
        this.setRefreshTokenExpires(refreshTokenExpires);
        return this;
    }

    public void setRefreshTokenExpires(Long refreshTokenExpires) {
        this.refreshTokenExpires = refreshTokenExpires;
    }

    public Role getRole() {
        return this.role;
    }

    public SupplierUser role(Role role) {
        this.setRole(role);
        return this;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SupplierUser)) {
            return false;
        }
        return id != null && id.equals(((SupplierUser) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SupplierUser{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", isApproved='" + getIsApproved() + "'" +
            ", refreshToken=" + getRefreshToken() +
            ", refreshTokenExpires=" + getRefreshTokenExpires() +
            ", role='" + getRole() + "'" +
            "}";
    }
}
