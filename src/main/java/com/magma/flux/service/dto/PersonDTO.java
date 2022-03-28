package com.magma.flux.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Lob;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.magma.flux.domain.Person} entity.
 */
public class PersonDTO implements Serializable {

    @NotNull
    @Min(value = 1000L)
    private Long id;

    @Lob
    private String meta;

    @Lob
    private byte[] profilePicture;

    private String profilePictureContentType;
    private String firstname;

    private String lastname;

    private String middlename;

    @Lob
    private String bio;

    private Long school;

    private Long major;

    @Lob
    private String socialLinks;

    private Long nationalityId;

    private Long genderId;

    private Long pronounId;

    private Long raceId;

    private Long addressId;

    private LocalDate birthdate;

    private SchoolDTO school;

    private CollegeDegreeDTO major;

    private CountryDTO nationalityId;

    private GenderDTO genderId;

    private PronounDTO pronounId;

    private RaceDTO raceId;

    private AddressDTO addressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public byte[] getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureContentType() {
        return profilePictureContentType;
    }

    public void setProfilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getSchool() {
        return school;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Long getMajor() {
        return major;
    }

    public void setMajor(Long major) {
        this.major = major;
    }

    public String getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(String socialLinks) {
        this.socialLinks = socialLinks;
    }

    public Long getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Long getGenderId() {
        return genderId;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getPronounId() {
        return pronounId;
    }

    public void setPronounId(Long pronounId) {
        this.pronounId = pronounId;
    }

    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public SchoolDTO getSchool() {
        return school;
    }

    public void setSchool(SchoolDTO school) {
        this.school = school;
    }

    public CollegeDegreeDTO getMajor() {
        return major;
    }

    public void setMajor(CollegeDegreeDTO major) {
        this.major = major;
    }

    public CountryDTO getNationalityId() {
        return nationalityId;
    }

    public void setNationalityId(CountryDTO nationalityId) {
        this.nationalityId = nationalityId;
    }

    public GenderDTO getGenderId() {
        return genderId;
    }

    public void setGenderId(GenderDTO genderId) {
        this.genderId = genderId;
    }

    public PronounDTO getPronounId() {
        return pronounId;
    }

    public void setPronounId(PronounDTO pronounId) {
        this.pronounId = pronounId;
    }

    public RaceDTO getRaceId() {
        return raceId;
    }

    public void setRaceId(RaceDTO raceId) {
        this.raceId = raceId;
    }

    public AddressDTO getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressDTO addressId) {
        this.addressId = addressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonDTO)) {
            return false;
        }

        PersonDTO personDTO = (PersonDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, personDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonDTO{" +
            "id=" + getId() +
            ", meta='" + getMeta() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", firstname='" + getFirstname() + "'" +
            ", lastname='" + getLastname() + "'" +
            ", middlename='" + getMiddlename() + "'" +
            ", bio='" + getBio() + "'" +
            ", school=" + getSchool() +
            ", major=" + getMajor() +
            ", socialLinks='" + getSocialLinks() + "'" +
            ", nationalityId=" + getNationalityId() +
            ", genderId=" + getGenderId() +
            ", pronounId=" + getPronounId() +
            ", raceId=" + getRaceId() +
            ", addressId=" + getAddressId() +
            ", birthdate='" + getBirthdate() + "'" +
            ", school=" + getSchool() +
            ", major=" + getMajor() +
            ", nationalityId=" + getNationalityId() +
            ", genderId=" + getGenderId() +
            ", pronounId=" + getPronounId() +
            ", raceId=" + getRaceId() +
            ", addressId=" + getAddressId() +
            "}";
    }
}
