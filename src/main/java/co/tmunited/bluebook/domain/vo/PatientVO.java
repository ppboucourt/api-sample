package co.tmunited.bluebook.domain.vo;

import co.tmunited.bluebook.domain.Chart;
import co.tmunited.bluebook.domain.TypeRace;
import co.tmunited.bluebook.domain.enumeration.Gender;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Pp on 7/11/2017.
 */
public class PatientVO {

    private Long id;

    private String social;

    private String status;

    private String identification;

    private Gender sex;

    private ZonedDateTime dateBirth;

    private String preferredName;

    private String firstName;

    private String middleName;

    private String lastName;

    private Set<Chart> charts = new HashSet<>();

    private TypeRace typeRace;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public ZonedDateTime getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(ZonedDateTime dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getPreferredName() {
        return preferredName;
    }

    public void setPreferredName(String preferredName) {
        this.preferredName = preferredName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Chart> getCharts() {
        return charts;
    }

    public void setCharts(Set<Chart> charts) {
        this.charts = charts;
    }

    public TypeRace getTypeRace() {
        return typeRace;
    }

    public void setTypeRace(TypeRace typeRace) {
        this.typeRace = typeRace;
    }

    public PatientVO(Long id, String social, String status, String identification, Gender sex, ZonedDateTime dateBirth, String preferredName, String firstName, String middleName, String lastName, Set<Chart> charts, TypeRace typeRace) {
        this.id = id;
        this.social = social;
        this.status = status;
        this.identification = identification;
        this.sex = sex;
        this.dateBirth = dateBirth;
        this.preferredName = preferredName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.charts = charts;
        this.typeRace = typeRace;
    }

    public PatientVO() {
    }
}
