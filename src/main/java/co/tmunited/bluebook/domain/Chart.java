package co.tmunited.bluebook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Chart.
 */
@Entity
@Table(name = "chart")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "chart")
public class Chart extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "program")
    private String program;

    @Column(name = "referrer")
    private String referrer;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private String zip;

    @Column(name = "occupancy")
    private String occupancy;

    @Column(name = "employer")
    private String employer;

    @Column(name = "status")
    private String status;

    @Column(name = "email")
    private String email;

//    @Lob
//    @Column(name = "picture")
//    private byte[] picture;
//
//    @Column(name = "picture_content_type")
//    private String pictureContentType;

    @Column(name = "waiting_room")
    private Boolean waitingRoom;

    @Column(name = "discharge_date")
    private ZonedDateTime dischargeDate;

    @Size(max = 2000)
    @Column(name = "reason", length = 2000)
    private String reason;

    @Column(name = "admission_date")
    private ZonedDateTime admissionDate;

    @Column(name = "discharge_time")
    private ZonedDateTime dischargeTime;

    @Column(name = "discharge_to")
    private String dischargeTo;

    @Column(name = "rep_name")
    private String repName;

    @Column(name = "address_two")
    private String addressTwo;

    @Column(name = "date_first_contact")
    private ZonedDateTime dateFirstContact;

    @Column(name = "first_contact_name")
    private String firstContactName;

    @Column(name = "first_contact_relationship")
    private String firstContactRelationship;

    @Column(name = "employer_phone")
    private String employerPhone;

    @Column(name = "mr_no")
//    @Field(
//        type = FieldType.String,
//        index = FieldIndex.not_analyzed
//    )
    private String mrNo;

    @Column(name = "first_contact_phone")
    private String firstContactPhone;

    @Column(name = "alt_phone")
    private String altPhone;

    @Column(name = "sobriety_date")
    private ZonedDateTime sobrietyDate;

    @Column(name = "one_time_only")
    private Boolean oneTimeOnly;

//    @Lob
//    @Column(name = "patient_license")
//    private byte[] patientLicense;
//
//    @Column(name = "patient_license_content_type")
//    private String patientLicenseContentType;

    @Column(name = "referrer_require_contact")
    private Boolean referrerRequireContact;

    @Column(name = "living_arrangement")
    private String livingArrangement;

    @Size(max = 800)
    @Column(name = "way_living", length = 800)
    private String wayLiving;

    @Formula("date_part('day', (now()-admission_date))")
    private String daysInFacility;

    @ManyToOne(cascade = CascadeType.ALL)
    private Patient patient;

    @OneToMany(mappedBy = "chart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CareManager> careManagers = new HashSet<>();

    @PrimaryKeyJoinColumn(name = "id", referencedColumnName = "chart_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Bed bed;

    @OneToMany(mappedBy = "chart")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChartToGroupSession> chartToGroupSessions = new HashSet<>();

    @OneToMany(mappedBy = "chartConcurrentReviews")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConcurrentReviews> concurrentReviews = new HashSet<>();

//    @OneToMany(mappedBy = "chart", orphanRemoval = true, cascade = CascadeType.ALL)
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<ChartToMedications> chartToMedications = new HashSet<>();

//    @OneToMany(mappedBy = "chart", orphanRemoval = true, cascade = CascadeType.ALL)
//    @OrderBy("id")
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Contacts> contacts = new HashSet<>();

    @OneToMany(mappedBy = "chart", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Insurance> insurances = new HashSet<>();


    @OneToMany(mappedBy = "chart", fetch = FetchType.LAZY)
//    @JsonIgnore
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PatientMedication> patientMedications = new HashSet<>();

//    @OneToMany(mappedBy = "chart", orphanRemoval = true, cascade = CascadeType.ALL)
//    @JsonIgnore
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<Orders> orders = new HashSet<>();

    @OneToMany(mappedBy = "chart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Vitals> vitals = new HashSet<>();

    @OneToMany(mappedBy = "chart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Weight> weights = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employees;

    @ManyToOne
    private TypePatientPrograms typePatientPrograms;

    @ManyToOne
    private TypeLevelCare typeLevelCare;

    @ManyToOne
    private Facility facility;

    @OneToMany(mappedBy = "chart", cascade = CascadeType.ALL)
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Glucose> glucoses = new HashSet<>();

    @ManyToOne
    private TypeAdmissionStatus typeAdmissionStatus;

    @OneToMany(mappedBy = "chart")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GroupSessionDetailsChart> groupSessionDetailsCharts = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chart_shifts",
        joinColumns = @JoinColumn(name = "charts_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "shifts_id", referencedColumnName = "ID"))
    private Set<Shifts> shifts = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chart_icd10",
        joinColumns = @JoinColumn(name = "charts_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "icd10s_id", referencedColumnName = "ID"))
    private Set<Icd10> icd10S = new HashSet<>();

    @ManyToOne
    private TypeDischargeType typeDischargeType;

    @ManyToOne
    private TypePaymentMethods typePaymentMethods;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "chart_drugs",
        joinColumns = @JoinColumn(name = "charts_id", referencedColumnName = "ID"),
        inverseJoinColumns = @JoinColumn(name = "drugs_id", referencedColumnName = "ID"))
    private Set<Drugs> drugs = new HashSet<>();

    @OneToMany(mappedBy = "chart", cascade = CascadeType.ALL)
    @OrderBy("id")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Allergies> allergies = new HashSet<>();

    @OneToMany(mappedBy = "chart")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Diet> diets = new HashSet<>();

    @ManyToOne
    private TypeMaritalStatus typeMaritalStatus;

    @ManyToOne
    private TypeEthnicity typeEthnicity;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "chart", cascade = CascadeType.ALL)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChartToLevelCare> chartToLevelCares = new HashSet<>();

    @OneToMany(mappedBy = "chart")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ChartToForm> chartToForms = new HashSet<>();

//    @OneToMany(mappedBy = "chart")
//    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
//    private Set<ChartToIcd10> chartToIcd10S = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "picture_ref_id", referencedColumnName = "id")
    private Picture pictureRef;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_license_id", referencedColumnName = "id")
    private Picture patientLicenseId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProgram() {
        return program;
    }

    public Chart program(String program) {
        this.program = program;
        return this;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public String getReferrer() {
        return referrer;
    }

    public Chart referrer(String referrer) {
        this.referrer = referrer;
        return this;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer;
    }

    public String getPhone() {
        return phone;
    }

    public Chart phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public Chart address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Chart city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public Chart state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public Chart zip(String zip) {
        this.zip = zip;
        return this;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public Chart occupancy(String occupancy) {
        this.occupancy = occupancy;
        return this;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public String getEmployer() {
        return employer;
    }

    public Chart employer(String employer) {
        this.employer = employer;
        return this;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getStatus() {
        return status;
    }

    public Chart status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public Chart email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean isWaitingRoom() {
        return waitingRoom;
    }

    public Chart waitingRoom(Boolean waitingRoom) {
        this.waitingRoom = waitingRoom;
        return this;
    }

    public String getDaysInFacility() {
        return daysInFacility;
    }

    public void setDaysInFacility(String daysInFacility) {
        this.daysInFacility = daysInFacility;
    }

    public void setWaitingRoom(Boolean waitingRoom) {
        this.waitingRoom = waitingRoom;
    }

    public ZonedDateTime getDischargeDate() {
        return dischargeDate;
    }

    public Chart dischargeDate(ZonedDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
        return this;
    }

    public void setDischargeDate(ZonedDateTime dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getReason() {
        return reason;
    }

    public Chart reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ZonedDateTime getAdmissionDate() {
        return admissionDate;
    }

    public Chart admissionDate(ZonedDateTime admissionDate) {
        this.admissionDate = admissionDate;
        return this;
    }

    public void setAdmissionDate(ZonedDateTime admissionDate) {
        this.admissionDate = admissionDate;
    }

    public ZonedDateTime getDischargeTime() {
        return dischargeTime;
    }

    public Chart dischargeTime(ZonedDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
        return this;
    }

    public void setDischargeTime(ZonedDateTime dischargeTime) {
        this.dischargeTime = dischargeTime;
    }

    public String getDischargeTo() {
        return dischargeTo;
    }

    public Chart dischargeTo(String dischargeTo) {
        this.dischargeTo = dischargeTo;
        return this;
    }

    public void setDischargeTo(String dischargeTo) {
        this.dischargeTo = dischargeTo;
    }

    public String getRepName() {
        return repName;
    }

    public Chart repName(String repName) {
        this.repName = repName;
        return this;
    }

    public void setRepName(String repName) {
        this.repName = repName;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public Chart addressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
        return this;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public ZonedDateTime getDateFirstContact() {
        return dateFirstContact;
    }

    public Chart dateFirstContact(ZonedDateTime dateFirstContact) {
        this.dateFirstContact = dateFirstContact;
        return this;
    }

    public void setDateFirstContact(ZonedDateTime dateFirstContact) {
        this.dateFirstContact = dateFirstContact;
    }

    public String getFirstContactName() {
        return firstContactName;
    }

    public Chart firstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
        return this;
    }

    public void setFirstContactName(String firstContactName) {
        this.firstContactName = firstContactName;
    }

    public String getFirstContactRelationship() {
        return firstContactRelationship;
    }

    public Chart firstContactRelationship(String firstContactRelationship) {
        this.firstContactRelationship = firstContactRelationship;
        return this;
    }

    public void setFirstContactRelationship(String firstContactRelationship) {
        this.firstContactRelationship = firstContactRelationship;
    }

    public String getEmployerPhone() {
        return employerPhone;
    }

    public Chart employerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
        return this;
    }

    public void setEmployerPhone(String employerPhone) {
        this.employerPhone = employerPhone;
    }

    public String getMrNo() {
        return mrNo;
    }

    public Chart mrNo(String mrNo) {
        this.mrNo = mrNo;
        return this;
    }

    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    public String getFirstContactPhone() {
        return firstContactPhone;
    }

    public Chart firstContactPhone(String firstContactPhone) {
        this.firstContactPhone = firstContactPhone;
        return this;
    }

    public void setFirstContactPhone(String firstContactPhone) {
        this.firstContactPhone = firstContactPhone;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public Chart altPhone(String altPhone) {
        this.altPhone = altPhone;
        return this;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public ZonedDateTime getSobrietyDate() {
        return sobrietyDate;
    }

    public Chart sobrietyDate(ZonedDateTime sobrietyDate) {
        this.sobrietyDate = sobrietyDate;
        return this;
    }

    public void setSobrietyDate(ZonedDateTime sobrietyDate) {
        this.sobrietyDate = sobrietyDate;
    }

    public Boolean isOneTimeOnly() {
        return oneTimeOnly;
    }

    public Chart oneTimeOnly(Boolean oneTimeOnly) {
        this.oneTimeOnly = oneTimeOnly;
        return this;
    }

    public void setOneTimeOnly(Boolean oneTimeOnly) {
        this.oneTimeOnly = oneTimeOnly;
    }

    public Boolean isReferrerRequireContact() {
        return referrerRequireContact;
    }

    public Chart referrerRequireContact(Boolean referrerRequireContact) {
        this.referrerRequireContact = referrerRequireContact;
        return this;
    }

    public void setReferrerRequireContact(Boolean referrerRequireContact) {
        this.referrerRequireContact = referrerRequireContact;
    }

    public String getLivingArrangement() {
        return livingArrangement;
    }

    public Chart livingArrangement(String livingArrangement) {
        this.livingArrangement = livingArrangement;
        return this;
    }

    public void setLivingArrangement(String livingArrangement) {
        this.livingArrangement = livingArrangement;
    }

    public String getWayLiving() {
        return wayLiving;
    }

    public Chart wayLiving(String wayLiving) {
        this.wayLiving = wayLiving;
        return this;
    }

    public void setWayLiving(String wayLiving) {
        this.wayLiving = wayLiving;
    }

    public Patient getPatient() {
        return patient;
    }

    public Chart patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<CareManager> getCareManagers() {
        return careManagers;
    }

    public Chart careManagers(Set<CareManager> careManagers) {
        this.careManagers = careManagers;
        return this;
    }

    public Chart addCareManagers(CareManager careManager) {
        careManagers.add(careManager);
        careManager.setChart(this);
        return this;
    }

    public Chart removeCareManagers(CareManager careManager) {
        careManagers.remove(careManager);
        careManager.setChart(null);
        return this;
    }

    public void setCareManagers(Set<CareManager> careManagers) {
        this.careManagers = careManagers;
    }

    public Bed getBed() {
        return bed;
    }

    public Chart bed(Bed bed) {
        this.bed = bed;
        return this;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Set<ChartToGroupSession> getChartToGroupSessions() {
        return chartToGroupSessions;
    }

    public Chart chartToGroupSessions(Set<ChartToGroupSession> chartToGroupSessions) {
        this.chartToGroupSessions = chartToGroupSessions;
        return this;
    }

    public Chart addChartToGroupSessions(ChartToGroupSession chartToGroupSession) {
        chartToGroupSessions.add(chartToGroupSession);
        chartToGroupSession.setChart(this);
        return this;
    }

    public Chart removeChartToGroupSessions(ChartToGroupSession chartToGroupSession) {
        chartToGroupSessions.remove(chartToGroupSession);
        chartToGroupSession.setChart(null);
        return this;
    }

    public void setChartToGroupSessions(Set<ChartToGroupSession> chartToGroupSessions) {
        this.chartToGroupSessions = chartToGroupSessions;
    }

    public Set<ConcurrentReviews> getConcurrentReviews() {
        return concurrentReviews;
    }

    public Chart concurrentReviews(Set<ConcurrentReviews> concurrentReviews) {
        this.concurrentReviews = concurrentReviews;
        return this;
    }

//    public Chart addConcurrentReviews(ConcurrentReviews concurrentReviews) {
//        concurrentReviews.add(concurrentReviews);
//        concurrentReviews.setChartConcurrentReviews(this);
//        return this;
//    }
//
//    public Chart removeConcurrentReviews(ConcurrentReviews concurrentReviews) {
//        concurrentReviews.remove(concurrentReviews);
//        concurrentReviews.setChartConcurrentReviews(null);
//        return this;
//    }

    public void setConcurrentReviews(Set<ConcurrentReviews> concurrentReviews) {
        this.concurrentReviews = concurrentReviews;
    }

//    public Set<ChartToMedications> getChartToMedications() {
//        return chartToMedications;
//    }
//
//    public Chart chartToMedications(Set<ChartToMedications> chartToMedications) {
//        this.chartToMedications = chartToMedications;
//        return this;
//    }

//    public Chart addChartToMedications(ChartToMedications chartToMedications) {
//        chartToMedications.add(chartToMedications);
//        chartToMedications.setChart(this);
//        return this;
//    }
//
//    public Chart removeChartToMedications(ChartToMedications chartToMedications) {
//        chartToMedications.remove(chartToMedications);
//        chartToMedications.setChart(null);
//        return this;
//    }

//    public void setChartToMedications(Set<ChartToMedications> chartToMedications) {
//        this.chartToMedications = chartToMedications;
//    }

//    public Set<Contacts> getContacts() {
//        return contacts;
//    }
//
//    public Chart contacts(Set<Contacts> contacts) {
//        this.contacts = contacts;
//        return this;
//    }

//    public Chart addContacts(Contacts contacts) {
//        contacts.add(contacts);
//        contacts.setChart(this);
//        return this;
//    }
//
//    public Chart removeContacts(Contacts contacts) {
//        contacts.remove(contacts);
//        contacts.setChart(null);
//        return this;
//    }
//
//    public void setContacts(Set<Contacts> contacts) {
//        this.contacts = contacts;
//    }

    public Set<Insurance> getInsurances() {
        return insurances;
    }

    public Chart insurances(Set<Insurance> insurances) {
        this.insurances = insurances;
        return this;
    }

//    public Chart addInsurances(Insurance insurance) {
//        insurances.add(insurance);
//        insurance.setChart(this);
//        return this;
//    }
//
//    public Chart removeInsurances(Insurance insurance) {
//        insurances.remove(insurance);
//        insurance.setChart(null);
//        return this;
//    }

    public void setInsurances(Set<Insurance> insurances) {
        this.insurances = insurances;
    }

    public Set<Vitals> getVitals() {
        return vitals;
    }

    public Chart vitals(Set<Vitals> vitals) {
        this.vitals = vitals;
        return this;
    }

//    public Chart addVitals(Vitals vitals) {
//        vitals.add(vitals);
//        vitals.setChart(this);
//        return this;
//    }
//
//    public Chart removeVitals(Vitals vitals) {
//        vitals.remove(vitals);
//        vitals.setChart(null);
//        return this;
//    }

    public void setVitals(Set<Vitals> vitals) {
        this.vitals = vitals;
    }

    public Set<Weight> getWeights() {
        return weights;
    }

    public Chart weights(Set<Weight> weights) {
        this.weights = weights;
        return this;
    }

    public Chart addWeights(Weight weight) {
        weights.add(weight);
        weight.setChart(this);
        return this;
    }

    public Chart removeWeights(Weight weight) {
        weights.remove(weight);
        weight.setChart(null);
        return this;
    }

    public void setWeights(Set<Weight> weights) {
        this.weights = weights;
    }

    public Employee getEmployees() {
        return employees;
    }

    public Chart employees(Employee employee) {
        this.employees = employee;
        return this;
    }

    public void setEmployees(Employee employee) {
        this.employees = employee;
    }

    public TypePatientPrograms getTypePatientPrograms() {
        return typePatientPrograms;
    }

    public Chart typePatientPrograms(TypePatientPrograms typePatientPrograms) {
        this.typePatientPrograms = typePatientPrograms;
        return this;
    }

    public void setTypePatientPrograms(TypePatientPrograms typePatientPrograms) {
        this.typePatientPrograms = typePatientPrograms;
    }

    public TypeLevelCare getTypeLevelCare() {
        return typeLevelCare;
    }

    public Chart typeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
        return this;
    }

    public void setTypeLevelCare(TypeLevelCare typeLevelCare) {
        this.typeLevelCare = typeLevelCare;
    }

    public Facility getFacility() {
        return facility;
    }

    public Chart facility(Facility facility) {
        this.facility = facility;
        return this;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Set<Glucose> getGlucoses() {
        return glucoses;
    }

    public Chart glucoses(Set<Glucose> glucoses) {
        this.glucoses = glucoses;
        return this;
    }

    public Chart addGlucoses(Glucose glucose) {
        glucoses.add(glucose);
        glucose.setChart(this);
        return this;
    }

    public Chart removeGlucoses(Glucose glucose) {
        glucoses.remove(glucose);
        glucose.setChart(null);
        return this;
    }

    public void setGlucoses(Set<Glucose> glucoses) {
        this.glucoses = glucoses;
    }

    public TypeAdmissionStatus getTypeAdmissionStatus() {
        return typeAdmissionStatus;
    }

    public Chart typeAdmissionStatus(TypeAdmissionStatus typeAdmissionStatus) {
        this.typeAdmissionStatus = typeAdmissionStatus;
        return this;
    }

    public void setTypeAdmissionStatus(TypeAdmissionStatus typeAdmissionStatus) {
        this.typeAdmissionStatus = typeAdmissionStatus;
    }

    public Set<GroupSessionDetailsChart> getGroupSessionDetailsCharts() {
        return groupSessionDetailsCharts;
    }

    public Chart groupSessionDetailsCharts(Set<GroupSessionDetailsChart> groupSessionDetailsCharts) {
        this.groupSessionDetailsCharts = groupSessionDetailsCharts;
        return this;
    }

    public Chart addGroupSessionDetailsChart(GroupSessionDetailsChart groupSessionDetailsChart) {
        groupSessionDetailsCharts.add(groupSessionDetailsChart);
        groupSessionDetailsChart.setChart(this);
        return this;
    }

    public Chart removeGroupSessionDetailsChart(GroupSessionDetailsChart groupSessionDetailsChart) {
        groupSessionDetailsCharts.remove(groupSessionDetailsChart);
        groupSessionDetailsChart.setChart(null);
        return this;
    }

    public void setGroupSessionDetailsCharts(Set<GroupSessionDetailsChart> groupSessionDetailsCharts) {
        this.groupSessionDetailsCharts = groupSessionDetailsCharts;
    }

    public Set<Shifts> getShifts() {
        return shifts;
    }

    public Chart shifts(Set<Shifts> shifts) {
        this.shifts = shifts;
        return this;
    }

//    public Chart addShifts(Shifts shifts) {
//        shifts.add(shifts);
//        shifts.getCharts().add(this);
//        return this;
//    }
//
//    public Chart removeShifts(Shifts shifts) {
//        shifts.remove(shifts);
//        shifts.getCharts().remove(this);
//        return this;
//    }

    public void setShifts(Set<Shifts> shifts) {
        this.shifts = shifts;
    }

    public Set<Icd10> getIcd10S() {
        return icd10S;
    }

    public Chart icd10S(Set<Icd10> icd10S) {
        this.icd10S = icd10S;
        return this;
    }

//    public Chart addIcd10(Icd10 icd10) {
//        icd10S.add(icd10);
//        icd10.getCharts().add(this);
//        return this;
//    }
//
//    public Chart removeIcd10(Icd10 icd10) {
//        icd10S.remove(icd10);
//        icd10.getCharts().remove(this);
//        return this;
//    }

    public void setIcd10S(Set<Icd10> icd10S) {
        this.icd10S = icd10S;
    }

    public TypeDischargeType getTypeDischargeType() {
        return typeDischargeType;
    }

    public Chart typeDischargeType(TypeDischargeType typeDischargeType) {
        this.typeDischargeType = typeDischargeType;
        return this;
    }

    public void setTypeDischargeType(TypeDischargeType typeDischargeType) {
        this.typeDischargeType = typeDischargeType;
    }

    public TypePaymentMethods getTypePaymentMethods() {
        return typePaymentMethods;
    }

    public Chart typePaymentMethods(TypePaymentMethods typePaymentMethods) {
        this.typePaymentMethods = typePaymentMethods;
        return this;
    }

    public void setTypePaymentMethods(TypePaymentMethods typePaymentMethods) {
        this.typePaymentMethods = typePaymentMethods;
    }

    public Set<Drugs> getDrugs() {
        return drugs;
    }

    public Chart drugs(Set<Drugs> drugs) {
        this.drugs = drugs;
        return this;
    }

//    public Chart addDrugs(Drugs drugs) {
//        drugs.add(drugs);
//        drugs.getCharts().add(this);
//        return this;
//    }
//
//    public Chart removeDrugs(Drugs drugs) {
//        drugs.remove(drugs);
//        drugs.getCharts().remove(this);
//        return this;
//    }

    public void setDrugs(Set<Drugs> drugs) {
        this.drugs = drugs;
    }

//    public Set<Allergies> getAllergies() {
//        return allergies;
//    }
//
//    public Chart allergies(Set<Allergies> allergies) {
//        this.allergies = allergies;
//        return this;
//    }

//    public Chart addAllergies(Allergies allergies) {
//        allergies.add(allergies);
//        allergies.setChart(this);
//        return this;
//    }
//
//    public Chart removeAllergies(Allergies allergies) {
//        allergies.remove(allergies);
//        allergies.setChart(null);
//        return this;
//    }

//    public void setAllergies(Set<Allergies> allergies) {
//        this.allergies = allergies;
//    }

    public Set<Diet> getDiets() {
        return diets;
    }

    public Chart diets(Set<Diet> diets) {
        this.diets = diets;
        return this;
    }

    public Chart addDiet(Diet diet) {
        diets.add(diet);
        diet.setChart(this);
        return this;
    }

    public Chart removeDiet(Diet diet) {
        diets.remove(diet);
        diet.setChart(null);
        return this;
    }

    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
    }

    public TypeMaritalStatus getTypeMaritalStatus() {
        return typeMaritalStatus;
    }

    public Chart typeMaritalStatus(TypeMaritalStatus typeMaritalStatus) {
        this.typeMaritalStatus = typeMaritalStatus;
        return this;
    }

    public void setTypeMaritalStatus(TypeMaritalStatus typeMaritalStatus) {
        this.typeMaritalStatus = typeMaritalStatus;
    }

    public TypeEthnicity getTypeEthnicity() {
        return typeEthnicity;
    }

    public Chart typeEthnicity(TypeEthnicity typeEthnicity) {
        this.typeEthnicity = typeEthnicity;
        return this;
    }

    public void setTypeEthnicity(TypeEthnicity typeEthnicity) {
        this.typeEthnicity = typeEthnicity;
    }

    public Set<ChartToLevelCare> getChartToLevelCares() {
        return chartToLevelCares;
    }

    public Chart chartToLevelCares(Set<ChartToLevelCare> chartToLevelCares) {
        this.chartToLevelCares = chartToLevelCares;
        return this;
    }

    public Chart addChartToLevelCare(ChartToLevelCare chartToLevelCare) {
        chartToLevelCares.add(chartToLevelCare);
        chartToLevelCare.setChart(this);
        return this;
    }

    public Chart removeChartToLevelCare(ChartToLevelCare chartToLevelCare) {
        chartToLevelCares.remove(chartToLevelCare);
        chartToLevelCare.setChart(null);
        return this;
    }

    public void setChartToLevelCares(Set<ChartToLevelCare> chartToLevelCares) {
        this.chartToLevelCares = chartToLevelCares;
    }
//
//    public Set<ChartToIcd10> getChartToIcd10S() {
//        return chartToIcd10S;
//    }
//
//    public Chart chartToIcd10S(Set<ChartToIcd10> chartToIcd10S) {
//        this.chartToIcd10S = chartToIcd10S;
//        return this;
//    }
//
//    public Chart addChartToIcd10(ChartToIcd10 chartToIcd10) {
//        chartToIcd10S.add(chartToIcd10);
//        chartToIcd10.setChart(this);
//        return this;
//    }
//
//    public Chart removeChartToIcd10(ChartToIcd10 chartToIcd10) {
//        chartToIcd10S.remove(chartToIcd10);
//        chartToIcd10.setChart(null);
//        return this;
//    }
//
//    public void setChartToIcd10S(Set<ChartToIcd10> chartToIcd10S) {
//        this.chartToIcd10S = chartToIcd10S;
//    }


    public Picture getPictureRef() {
        return pictureRef;
    }

    public void setPictureRef(Picture pictureRef) {
        this.pictureRef = pictureRef;
    }

    public Chart pictureRef(Picture pictureRef) {
        this.pictureRef = pictureRef;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chart chart = (Chart) o;
        if (chart.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, chart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Chart{" +
            "id=" + id +
            ", program='" + program + "'" +
            ", referrer='" + referrer + "'" +
            ", phone='" + phone + "'" +
            ", address='" + address + "'" +
            ", city='" + city + "'" +
            ", state='" + state + "'" +
            ", zip='" + zip + "'" +
            ", occupancy='" + occupancy + "'" +
            ", employer='" + employer + "'" +
            ", status='" + status + "'" +
            ", email='" + email + "'" +
            ", waitingRoom='" + waitingRoom + "'" +
            ", dischargeDate='" + dischargeDate + "'" +
            ", reason='" + reason + "'" +
            ", admissionDate='" + admissionDate + "'" +
            ", dischargeTime='" + dischargeTime + "'" +
            ", dischargeTo='" + dischargeTo + "'" +
            ", repName='" + repName + "'" +
            ", addressTwo='" + addressTwo + "'" +
            ", dateFirstContact='" + dateFirstContact + "'" +
            ", firstContactName='" + firstContactName + "'" +
            ", firstContactRelationship='" + firstContactRelationship + "'" +
            ", employerPhone='" + employerPhone + "'" +
            ", mrNo='" + mrNo + "'" +
            ", firstContactPhone='" + firstContactPhone + "'" +
            ", altPhone='" + altPhone + "'" +
            ", sobrietyDate='" + sobrietyDate + "'" +
            ", oneTimeOnly='" + oneTimeOnly + "'" +
            ", referrerRequireContact='" + referrerRequireContact + "'" +
            ", livingArrangement='" + livingArrangement + "'" +
            ", wayLiving='" + wayLiving + "'" +
            '}';
    }

    public Set<ChartToForm> getChartToForms() {
        return chartToForms;
    }

    public void setChartToForms(Set<ChartToForm> chartToForms) {
        this.chartToForms = chartToForms;
    }

    public Set<Allergies> getAllergies() {
        return allergies;
    }

    public void setAllergies(Set<Allergies> allergies) {
        this.allergies = allergies;
    }

    public Set<PatientMedication> getPatientMedications() {
        return patientMedications;
    }

    public void setPatientMedications(Set<PatientMedication> patientMedications) {
        this.patientMedications = patientMedications;
    }

    public Picture getPatientLicenseId() {
        return patientLicenseId;
    }

    public void setPatientLicenseId(Picture patientLicenseId) {
        this.patientLicenseId = patientLicenseId;
    }
}
