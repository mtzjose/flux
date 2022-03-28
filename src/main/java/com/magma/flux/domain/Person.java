package com.magma.flux.domain;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Person.
 */
@Entity
@Table(name = "person")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Min(value = 1000L)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "meta")
    private String meta;

    @Lob
    @Column(name = "profile_picture")
    private byte[] profilePicture;

    @Column(name = "profile_picture_content_type")
    private String profilePictureContentType;

    @Column(name = "firstname")
    private String firstname;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "middlename")
    private String middlename;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "bio")
    private String bio;

    @Column(name = "school")
    private Long school;

    @Column(name = "major")
    private Long major;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "social_links")
    private String socialLinks;

    @Column(name = "nationality_id")
    private Long nationalityId;

    @Column(name = "gender_id")
    private Long genderId;

    @Column(name = "pronoun_id")
    private Long pronounId;

    @Column(name = "race_id")
    private Long raceId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @OneToOne
    @JoinColumn(unique = true)
    private School school;

    @OneToOne
    @JoinColumn(unique = true)
    private CollegeDegree major;

    @OneToOne
    @JoinColumn(unique = true)
    private Country nationalityId;

    @OneToOne
    @JoinColumn(unique = true)
    private Gender genderId;

    @OneToOne
    @JoinColumn(unique = true)
    private Pronoun pronounId;

    @OneToOne
    @JoinColumn(unique = true)
    private Race raceId;

    @OneToOne
    @JoinColumn(unique = true)
    private Address addressId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Person id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMeta() {
        return this.meta;
    }

    public Person meta(String meta) {
        this.setMeta(meta);
        return this;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public byte[] getProfilePicture() {
        return this.profilePicture;
    }

    public Person profilePicture(byte[] profilePicture) {
        this.setProfilePicture(profilePicture);
        return this;
    }

    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getProfilePictureContentType() {
        return this.profilePictureContentType;
    }

    public Person profilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
        return this;
    }

    public void setProfilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public Person firstname(String firstname) {
        this.setFirstname(firstname);
        return this;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public Person lastname(String lastname) {
        this.setLastname(lastname);
        return this;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public Person middlename(String middlename) {
        this.setMiddlename(middlename);
        return this;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getBio() {
        return this.bio;
    }

    public Person bio(String bio) {
        this.setBio(bio);
        return this;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Long getSchool() {
        return this.school;
    }

    public Person school(Long school) {
        this.setSchool(school);
        return this;
    }

    public void setSchool(Long school) {
        this.school = school;
    }

    public Long getMajor() {
        return this.major;
    }

    public Person major(Long major) {
        this.setMajor(major);
        return this;
    }

    public void setMajor(Long major) {
        this.major = major;
    }

    public String getSocialLinks() {
        return this.socialLinks;
    }

    public Person socialLinks(String socialLinks) {
        this.setSocialLinks(socialLinks);
        return this;
    }

    public void setSocialLinks(String socialLinks) {
        this.socialLinks = socialLinks;
    }

    public Long getNationalityId() {
        return this.nationalityId;
    }

    public Person nationalityId(Long nationalityId) {
        this.setNationalityId(nationalityId);
        return this;
    }

    public void setNationalityId(Long nationalityId) {
        this.nationalityId = nationalityId;
    }

    public Long getGenderId() {
        return this.genderId;
    }

    public Person genderId(Long genderId) {
        this.setGenderId(genderId);
        return this;
    }

    public void setGenderId(Long genderId) {
        this.genderId = genderId;
    }

    public Long getPronounId() {
        return this.pronounId;
    }

    public Person pronounId(Long pronounId) {
        this.setPronounId(pronounId);
        return this;
    }

    public void setPronounId(Long pronounId) {
        this.pronounId = pronounId;
    }

    public Long getRaceId() {
        return this.raceId;
    }

    public Person raceId(Long raceId) {
        this.setRaceId(raceId);
        return this;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getAddressId() {
        return this.addressId;
    }

    public Person addressId(Long addressId) {
        this.setAddressId(addressId);
        return this;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public LocalDate getBirthdate() {
        return this.birthdate;
    }

    public Person birthdate(LocalDate birthdate) {
        this.setBirthdate(birthdate);
        return this;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public Person school(School school) {
        this.setSchool(school);
        return this;
    }

    public CollegeDegree getMajor() {
        return this.major;
    }

    public void setMajor(CollegeDegree collegeDegree) {
        this.major = collegeDegree;
    }

    public Person major(CollegeDegree collegeDegree) {
        this.setMajor(collegeDegree);
        return this;
    }

    public Country getNationalityId() {
        return this.nationalityId;
    }

    public void setNationalityId(Country country) {
        this.nationalityId = country;
    }

    public Person nationalityId(Country country) {
        this.setNationalityId(country);
        return this;
    }

    public Gender getGenderId() {
        return this.genderId;
    }

    public void setGenderId(Gender gender) {
        this.genderId = gender;
    }

    public Person genderId(Gender gender) {
        this.setGenderId(gender);
        return this;
    }

    public Pronoun getPronounId() {
        return this.pronounId;
    }

    public void setPronounId(Pronoun pronoun) {
        this.pronounId = pronoun;
    }

    public Person pronounId(Pronoun pronoun) {
        this.setPronounId(pronoun);
        return this;
    }

    public Race getRaceId() {
        return this.raceId;
    }

    public void setRaceId(Race race) {
        this.raceId = race;
    }

    public Person raceId(Race race) {
        this.setRaceId(race);
        return this;
    }

    public Address getAddressId() {
        return this.addressId;
    }

    public void setAddressId(Address address) {
        this.addressId = address;
    }

    public Person addressId(Address address) {
        this.setAddressId(address);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }
        return id != null && id.equals(((Person) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Person{" +
            "id=" + getId() +
            ", meta='" + getMeta() + "'" +
            ", profilePicture='" + getProfilePicture() + "'" +
            ", profilePictureContentType='" + getProfilePictureContentType() + "'" +
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
            "}";
    }
}
