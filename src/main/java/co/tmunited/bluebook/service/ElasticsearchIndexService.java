package co.tmunited.bluebook.service;

import com.codahale.metrics.annotation.Timed;
import co.tmunited.bluebook.domain.*;
import co.tmunited.bluebook.repository.*;
import co.tmunited.bluebook.repository.search.*;
import org.elasticsearch.indices.IndexAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class ElasticsearchIndexService {

    private final Logger log = LoggerFactory.getLogger(ElasticsearchIndexService.class);

    @Inject
    private ActionsRepository actionsRepository;

    @Inject
    private ActionsSearchRepository actionsSearchRepository;

    @Inject
    private AllergiesRepository allergiesRepository;

    @Inject
    private AllergiesSearchRepository allergiesSearchRepository;

    @Inject
    private AllOrdersRepository allOrdersRepository;

    @Inject
    private AllOrdersSearchRepository allOrdersSearchRepository;

    @Inject
    private BedRepository bedRepository;

    @Inject
    private BedSearchRepository bedSearchRepository;

    @Inject
    private DrugsRepository drugsRepository;

    @Inject
    private DrugsSearchRepository drugsSearchRepository;

    @Inject
    private BuildingRepository buildingRepository;

    @Inject
    private BuildingSearchRepository buildingSearchRepository;

    @Inject
    private CareManagerRepository careManagerRepository;

    @Inject
    private CareManagerSearchRepository careManagerSearchRepository;

    @Inject
    private ChartRepository chartRepository;

    @Inject
    private ChartSearchRepository chartSearchRepository;

    @Inject
    private ChartToActionRepository chartToActionRepository;

    @Inject
    private ChartToActionSearchRepository chartToActionSearchRepository;

    @Inject
    private ChartToFormRepository chartToFormRepository;

    @Inject
    private ChartToFormSearchRepository chartToFormSearchRepository;

    @Inject
    private ChartToGroupSessionRepository chartToGroupSessionRepository;

    @Inject
    private ChartToGroupSessionSearchRepository chartToGroupSessionSearchRepository;

    @Inject
    private ChartToIcd10Repository chartToIcd10Repository;

    @Inject
    private ChartToIcd10SearchRepository chartToIcd10SearchRepository;

    @Inject
    private ChartToMedicationsRepository chartToMedicationsRepository;

    @Inject
    private ChartToMedicationsSearchRepository chartToMedicationsSearchRepository;

    @Inject
    private ConcurrentReviewsRepository concurrentReviewsRepository;

    @Inject
    private ConcurrentReviewsSearchRepository concurrentReviewsSearchRepository;

    @Inject
    private ContactsRepository contactsRepository;

    @Inject
    private ContactsSearchRepository contactsSearchRepository;

    @Inject
    private ContactsFacilityRepository contactsFacilityRepository;

    @Inject
    private ContactsFacilitySearchRepository contactsFacilitySearchRepository;

    @Inject
    private CorporationRepository corporationRepository;

    @Inject
    private CorporationSearchRepository corporationSearchRepository;

    @Inject
    private CountryStateRepository countryStateRepository;

    @Inject
    private CountryStateSearchRepository countryStateSearchRepository;

    @Inject
    private DietRepository dietRepository;

    @Inject
    private DietSearchRepository dietSearchRepository;

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private EmployeeSearchRepository employeeSearchRepository;

    @Inject
    private EvaluationRepository evaluationRepository;

    @Inject
    private EvaluationSearchRepository evaluationSearchRepository;

    @Inject
    private EvaluationContentRepository evaluationContentRepository;

    @Inject
    private EvaluationContentSearchRepository evaluationContentSearchRepository;

    @Inject
    private EvaluationItemsRepository evaluationItemsRepository;

    @Inject
    private EvaluationItemsSearchRepository evaluationItemsSearchRepository;

    @Inject
    private FacilityRepository facilityRepository;

    @Inject
    private FacilitySearchRepository facilitySearchRepository;

    @Inject
    private FieldsRepository fieldsRepository;

    @Inject
    private FieldsSearchRepository fieldsSearchRepository;

    @Inject
    private FormsRepository formsRepository;

    @Inject
    private FormsSearchRepository formsSearchRepository;

    @Inject
    private GlucoseRepository glucoseRepository;

    @Inject
    private GlucoseSearchRepository glucoseSearchRepository;

    @Inject
    private GroupSessionRepository groupSessionRepository;

    @Inject
    private GroupSessionSearchRepository groupSessionSearchRepository;

    @Inject
    private GroupSessionDetailsRepository groupSessionDetailsRepository;

    @Inject
    private GroupSessionDetailsSearchRepository groupSessionDetailsSearchRepository;

    @Inject
    private GroupSessionDetailsChartRepository groupSessionDetailsChartRepository;

    @Inject
    private GroupSessionDetailsChartSearchRepository groupSessionDetailsChartSearchRepository;

    @Inject
    private GroupTypeRepository groupTypeRepository;

    @Inject
    private GroupTypeSearchRepository groupTypeSearchRepository;

    @Inject
    private Icd10Repository icd10Repository;

    @Inject
    private Icd10SearchRepository icd10SearchRepository;

    @Inject
    private InsuranceRepository insuranceRepository;

    @Inject
    private InsuranceSearchRepository insuranceSearchRepository;

    @Inject
    private InsuranceCarrierRepository insuranceCarrierRepository;

    @Inject
    private InsuranceCarrierSearchRepository insuranceCarrierSearchRepository;

    @Inject
    private LabCompendiumRepository labCompendiumRepository;

    @Inject
    private LabCompendiumSearchRepository labCompendiumSearchRepository;

    @Inject
    private LaboratoriesRepository laboratoriesRepository;

    @Inject
    private LaboratoriesSearchRepository laboratoriesSearchRepository;

    @Inject
    private LabProfileRepository labProfileRepository;

    @Inject
    private LabProfileSearchRepository labProfileSearchRepository;

    @Inject
    private LabRequisitionRepository labRequisitionRepository;

    @Inject
    private LabRequisitionSearchRepository labRequisitionSearchRepository;

    @Inject
    private LabRequisitionsComponentsRepository labRequisitionsComponentsRepository;

    @Inject
    private LabRequisitionsComponentsSearchRepository labRequisitionsComponentsSearchRepository;

    @Inject
    private MedicationsRepository medicationsRepository;

    @Inject
    private MedicationsSearchRepository medicationsSearchRepository;

    @Inject
    private OrderComponentRepository orderComponentRepository;

    @Inject
    private OrderComponentSearchRepository orderComponentSearchRepository;

    @Inject
    private OrdersRepository ordersRepository;

    @Inject
    private OrdersSearchRepository ordersSearchRepository;

    @Inject
    private Order_typeRepository order_typeRepository;

    @Inject
    private Order_typeSearchRepository order_typeSearchRepository;

    @Inject
    private PatientRepository patientRepository;

    @Inject
    private PatientSearchRepository patientSearchRepository;

    @Inject
    private PatientToShiftRepository patientToShiftRepository;

    @Inject
    private PatientToShiftSearchRepository patientToShiftSearchRepository;

    @Inject
    private Patient_propertiesRepository patient_propertiesRepository;

    @Inject
    private Patient_propertiesSearchRepository patient_propertiesSearchRepository;

    @Inject
    private PayerRepository payerRepository;

    @Inject
    private PayerSearchRepository payerSearchRepository;

    @Inject
    private ReportDetailsRepository reportDetailsRepository;

    @Inject
    private ReportDetailsSearchRepository reportDetailsSearchRepository;

    @Inject
    private ReportsRepository reportsRepository;

    @Inject
    private ReportsSearchRepository reportsSearchRepository;

    @Inject
    private RoomRepository roomRepository;

    @Inject
    private RoomSearchRepository roomSearchRepository;

    @Inject
    private RoutesRepository routesRepository;

    @Inject
    private RoutesSearchRepository routesSearchRepository;

    @Inject
    private ShiftsRepository shiftsRepository;

    @Inject
    private ShiftsSearchRepository shiftsSearchRepository;

    @Inject
    private TablesRepository tablesRepository;

    @Inject
    private TablesSearchRepository tablesSearchRepository;

    @Inject
    private TypeAdmissionStatusRepository typeAdmissionStatusRepository;

    @Inject
    private TypeAdmissionStatusSearchRepository typeAdmissionStatusSearchRepository;

    @Inject
    private TypeContactFacilityTypeRepository typeContactFacilityTypeRepository;

    @Inject
    private TypeContactFacilityTypeSearchRepository typeContactFacilityTypeSearchRepository;

    @Inject
    private TypeDischargeTypeRepository typeDischargeTypeRepository;

    @Inject
    private TypeDischargeTypeSearchRepository typeDischargeTypeSearchRepository;

    @Inject
    private TypeDosageRepository typeDosageRepository;

    @Inject
    private TypeDosageSearchRepository typeDosageSearchRepository;

    @Inject
    private TypeEthnicityRepository typeEthnicityRepository;

    @Inject
    private TypeEthnicitySearchRepository typeEthnicitySearchRepository;

    @Inject
    private TypeEvaluationRepository typeEvaluationRepository;

    @Inject
    private TypeEvaluationSearchRepository typeEvaluationSearchRepository;

    @Inject
    private TypeFoodDietsRepository typeFoodDietsRepository;

    @Inject
    private TypeFoodDietsSearchRepository typeFoodDietsSearchRepository;

    @Inject
    private TypeFormRulesRepository typeFormRulesRepository;

    @Inject
    private TypeFormRulesSearchRepository typeFormRulesSearchRepository;

    @Inject
    private TypeLevelCareRepository typeLevelCareRepository;

    @Inject
    private TypeLevelCareSearchRepository typeLevelCareSearchRepository;

    @Inject
    private TypeMaritalStatusRepository typeMaritalStatusRepository;

    @Inject
    private TypeMaritalStatusSearchRepository typeMaritalStatusSearchRepository;

    @Inject
    private TypeMedicationRepository typeMedicationRepository;

    @Inject
    private TypeMedicationSearchRepository typeMedicationSearchRepository;

    @Inject
    private TypeMedicationRoutesRepository typeMedicationRoutesRepository;

    @Inject
    private TypeMedicationRoutesSearchRepository typeMedicationRoutesSearchRepository;

    @Inject
    private TypePageRepository typePageRepository;

    @Inject
    private TypePageSearchRepository typePageSearchRepository;

    @Inject
    private TypePatientContactsRelationshipRepository typePatientContactsRelationshipRepository;

    @Inject
    private TypePatientContactsRelationshipSearchRepository typePatientContactsRelationshipSearchRepository;

    @Inject
    private TypePatientContactTypesRepository typePatientContactTypesRepository;

    @Inject
    private TypePatientContactTypesSearchRepository typePatientContactTypesSearchRepository;

    @Inject
    private TypePatientProcessRepository typePatientProcessRepository;

    @Inject
    private TypePatientProcessSearchRepository typePatientProcessSearchRepository;

    @Inject
    private TypePatientProgramsRepository typePatientProgramsRepository;

    @Inject
    private TypePatientProgramsSearchRepository typePatientProgramsSearchRepository;

    @Inject
    private TypePatientPropertyConditionRepository typePatientPropertyConditionRepository;

    @Inject
    private TypePatientPropertyConditionSearchRepository typePatientPropertyConditionSearchRepository;

    @Inject
    private TypePaymentMethodsRepository typePaymentMethodsRepository;

    @Inject
    private TypePaymentMethodsSearchRepository typePaymentMethodsSearchRepository;

    @Inject
    private TypePersonRepository typePersonRepository;

    @Inject
    private TypePersonSearchRepository typePersonSearchRepository;

    @Inject
    private TypePreAdmissionStatusRepository typePreAdmissionStatusRepository;

    @Inject
    private TypePreAdmissionStatusSearchRepository typePreAdmissionStatusSearchRepository;

    @Inject
    private TypeRaceRepository typeRaceRepository;

    @Inject
    private TypeRaceSearchRepository typeRaceSearchRepository;

    @Inject
    private TypeTreatmentPlanStatusesRepository typeTreatmentPlanStatusesRepository;

    @Inject
    private TypeTreatmentPlanStatusesSearchRepository typeTreatmentPlanStatusesSearchRepository;

    @Inject
    private UrgentIssuesRepository urgentIssuesRepository;

    @Inject
    private UrgentIssuesSearchRepository urgentIssuesSearchRepository;

    @Inject
    private VendorsRepository vendorsRepository;

    @Inject
    private VendorsSearchRepository vendorsSearchRepository;

    @Inject
    private VitalsRepository vitalsRepository;

    @Inject
    private VitalsSearchRepository vitalsSearchRepository;

    @Inject
    private WeightRepository weightRepository;

    @Inject
    private WeightSearchRepository weightSearchRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private UserSearchRepository userSearchRepository;

    @Inject
    private ElasticsearchTemplate elasticsearchTemplate;

    @Inject
    private PatientOrderRepository patientOrderRepository;

    @Inject
    private PatientOrderSearchRepository patientOrderSearchRepository;

    @Inject
    private PatientOrderItemRepository patientOrderItemRepository;

    @Inject
    private PatientOrderItemSearchRepository patientOrderItemSearchRepository;

    @Inject
    private PatientOrderTestRepository patientOrderTestRepository;

    @Inject
    private PatientOrderTestSearchRepository patientOrderTestSearchRepository;

    @Inject
    private PatientResultRepository patientResultRepository;

    @Inject
    private PatientResultSearchRepository patientResultSearchRepository;

    @Inject
    private PatientResultDetRepository patientResultDetRepository;

    @Inject
    private PatientResultDetSearchRepository patientResultDetSearchRepository;

    @Inject
    private PatientActionRepository patientActionRepository;

    @Inject
    private PatientActionSearchRepository patientActionSearchRepository;

    @Inject
    private PatientActionPreRepository patientActionPreRepository;

    @Inject
    private PatientActionPreSearchRepository patientActionPreSearchRepository;

    @Inject
    private PatientActionTakeRepository patientActionTakeRepository;

    @Inject
    private PatientActionTakeSearchRepository patientActionTakeSearchRepository;

    @Inject
    private PatientMedicationRepository patientMedicationRepository;

    @Inject
    private PatientMedicationSearchRepository patientMedicationSearchRepository;

    @Inject
    private PatientMedicationPresRepository patientMedicationPresRepository;

    @Inject
    private PatientMedicationPresSearchRepository patientMedicationPresSearchRepository;

    @Inject
    private PatientMedicationTakeRepository patientMedicationTakeRepository;

    @Inject
    private PatientMedicationTakeSearchRepository patientMedicationTakeSearchRepository;

    @Async
    @Timed
    public void reindexAll() {
        reindexForClass(Actions.class, actionsRepository, actionsSearchRepository);
        reindexForClass(Allergies.class, allergiesRepository, allergiesSearchRepository);
        reindexForClass(AllOrders.class, allOrdersRepository, allOrdersSearchRepository);
        reindexForClass(Bed.class, bedRepository, bedSearchRepository);
        reindexForClass(Building.class, buildingRepository, buildingSearchRepository);
        reindexForClass(CareManager.class, careManagerRepository, careManagerSearchRepository);
        reindexForClass(Chart.class, chartRepository, chartSearchRepository);
        reindexForClass(ChartToAction.class, chartToActionRepository, chartToActionSearchRepository);
        reindexForClass(ChartToForm.class, chartToFormRepository, chartToFormSearchRepository);
        reindexForClass(ChartToGroupSession.class, chartToGroupSessionRepository, chartToGroupSessionSearchRepository);
        reindexForClass(ChartToIcd10.class, chartToIcd10Repository, chartToIcd10SearchRepository);
        reindexForClass(ChartToMedications.class, chartToMedicationsRepository, chartToMedicationsSearchRepository);
        reindexForClass(ConcurrentReviews.class, concurrentReviewsRepository, concurrentReviewsSearchRepository);
        reindexForClass(Contacts.class, contactsRepository, contactsSearchRepository);
        reindexForClass(ContactsFacility.class, contactsFacilityRepository, contactsFacilitySearchRepository);
        reindexForClass(Corporation.class, corporationRepository, corporationSearchRepository);
        reindexForClass(CountryState.class, countryStateRepository, countryStateSearchRepository);
        reindexForClass(Diet.class, dietRepository, dietSearchRepository);
        reindexForClass(Employee.class, employeeRepository, employeeSearchRepository);
        reindexForClass(Evaluation.class, evaluationRepository, evaluationSearchRepository);
        reindexForClass(EvaluationContent.class, evaluationContentRepository, evaluationContentSearchRepository);
        reindexForClass(EvaluationItems.class, evaluationItemsRepository, evaluationItemsSearchRepository);
        reindexForClass(Facility.class, facilityRepository, facilitySearchRepository);
        reindexForClass(Fields.class, fieldsRepository, fieldsSearchRepository);
        reindexForClass(Forms.class, formsRepository, formsSearchRepository);
        reindexForClass(Glucose.class, glucoseRepository, glucoseSearchRepository);
        reindexForClass(GroupSession.class, groupSessionRepository, groupSessionSearchRepository);
        reindexForClass(GroupSessionDetails.class, groupSessionDetailsRepository, groupSessionDetailsSearchRepository);
        reindexForClass(GroupSessionDetailsChart.class, groupSessionDetailsChartRepository, groupSessionDetailsChartSearchRepository);
        reindexForClass(GroupType.class, groupTypeRepository, groupTypeSearchRepository);
        reindexForClass(Icd10.class, icd10Repository, icd10SearchRepository);
        reindexForClass(Drugs.class, drugsRepository, drugsSearchRepository);
        reindexForClass(Insurance.class, insuranceRepository, insuranceSearchRepository);
        reindexForClass(InsuranceCarrier.class, insuranceCarrierRepository, insuranceCarrierSearchRepository);
        reindexForClass(LabCompendium.class, labCompendiumRepository, labCompendiumSearchRepository);
        reindexForClass(Laboratories.class, laboratoriesRepository, laboratoriesSearchRepository);
        reindexForClass(LabProfile.class, labProfileRepository, labProfileSearchRepository);
        reindexForClass(LabRequisition.class, labRequisitionRepository, labRequisitionSearchRepository);
        reindexForClass(LabRequisitionsComponents.class, labRequisitionsComponentsRepository, labRequisitionsComponentsSearchRepository);
        reindexForClass(Medications.class, medicationsRepository, medicationsSearchRepository);
        reindexForClass(OrderComponent.class, orderComponentRepository, orderComponentSearchRepository);
        reindexForClass(Orders.class, ordersRepository, ordersSearchRepository);
        reindexForClass(Order_type.class, order_typeRepository, order_typeSearchRepository);
        reindexForClass(Patient.class, patientRepository, patientSearchRepository);
        reindexForClass(PatientToShift.class, patientToShiftRepository, patientToShiftSearchRepository);
        reindexForClass(Patient_properties.class, patient_propertiesRepository, patient_propertiesSearchRepository);
        reindexForClass(Payer.class, payerRepository, payerSearchRepository);
        reindexForClass(ReportDetails.class, reportDetailsRepository, reportDetailsSearchRepository);
        reindexForClass(Reports.class, reportsRepository, reportsSearchRepository);
        reindexForClass(Room.class, roomRepository, roomSearchRepository);
        reindexForClass(Routes.class, routesRepository, routesSearchRepository);
        reindexForClass(Shifts.class, shiftsRepository, shiftsSearchRepository);
        reindexForClass(Tables.class, tablesRepository, tablesSearchRepository);
        reindexForClass(TypeAdmissionStatus.class, typeAdmissionStatusRepository, typeAdmissionStatusSearchRepository);
        reindexForClass(TypeContactFacilityType.class, typeContactFacilityTypeRepository, typeContactFacilityTypeSearchRepository);
        reindexForClass(TypeDischargeType.class, typeDischargeTypeRepository, typeDischargeTypeSearchRepository);
        reindexForClass(TypeDosage.class, typeDosageRepository, typeDosageSearchRepository);
        reindexForClass(TypeEthnicity.class, typeEthnicityRepository, typeEthnicitySearchRepository);
        reindexForClass(TypeEvaluation.class, typeEvaluationRepository, typeEvaluationSearchRepository);
        reindexForClass(TypeFoodDiets.class, typeFoodDietsRepository, typeFoodDietsSearchRepository);
        reindexForClass(TypeFormRules.class, typeFormRulesRepository, typeFormRulesSearchRepository);
        reindexForClass(TypeLevelCare.class, typeLevelCareRepository, typeLevelCareSearchRepository);
        reindexForClass(TypeMaritalStatus.class, typeMaritalStatusRepository, typeMaritalStatusSearchRepository);
        reindexForClass(TypeMedication.class, typeMedicationRepository, typeMedicationSearchRepository);
        reindexForClass(TypeMedicationRoutes.class, typeMedicationRoutesRepository, typeMedicationRoutesSearchRepository);
        reindexForClass(TypePage.class, typePageRepository, typePageSearchRepository);
        reindexForClass(TypePatientContactsRelationship.class, typePatientContactsRelationshipRepository, typePatientContactsRelationshipSearchRepository);
        reindexForClass(TypePatientContactTypes.class, typePatientContactTypesRepository, typePatientContactTypesSearchRepository);
        reindexForClass(TypePatientProcess.class, typePatientProcessRepository, typePatientProcessSearchRepository);
        reindexForClass(TypePatientPrograms.class, typePatientProgramsRepository, typePatientProgramsSearchRepository);
        reindexForClass(TypePatientPropertyCondition.class, typePatientPropertyConditionRepository, typePatientPropertyConditionSearchRepository);
        reindexForClass(TypePaymentMethods.class, typePaymentMethodsRepository, typePaymentMethodsSearchRepository);
        reindexForClass(TypePerson.class, typePersonRepository, typePersonSearchRepository);
        reindexForClass(TypePreAdmissionStatus.class, typePreAdmissionStatusRepository, typePreAdmissionStatusSearchRepository);
        reindexForClass(TypeRace.class, typeRaceRepository, typeRaceSearchRepository);
        reindexForClass(TypeTreatmentPlanStatuses.class, typeTreatmentPlanStatusesRepository, typeTreatmentPlanStatusesSearchRepository);
        reindexForClass(UrgentIssues.class, urgentIssuesRepository, urgentIssuesSearchRepository);
        reindexForClass(Vendors.class, vendorsRepository, vendorsSearchRepository);
        reindexForClass(Vitals.class, vitalsRepository, vitalsSearchRepository);
        reindexForClass(Weight.class, weightRepository, weightSearchRepository);
        reindexForClass(User.class, userRepository, userSearchRepository);
        reindexForClass(PatientOrder.class, patientOrderRepository, patientOrderSearchRepository);
        reindexForClass(PatientOrderItem.class, patientOrderItemRepository, patientOrderItemSearchRepository);
        reindexForClass(PatientOrderTest.class, patientOrderTestRepository, patientOrderTestSearchRepository);
        reindexForClass(PatientResult.class, patientResultRepository, patientResultSearchRepository);
        reindexForClass(PatientResultDet.class, patientResultDetRepository, patientResultDetSearchRepository);
        reindexForClass(PatientOrder.class, patientOrderRepository, patientOrderSearchRepository);
        reindexForClass(PatientOrderTest.class, patientOrderTestRepository, patientOrderTestSearchRepository);
        reindexForClass(PatientOrderItem.class, patientOrderItemRepository, patientOrderItemSearchRepository);
        reindexForClass(PatientAction.class, patientActionRepository, patientActionSearchRepository);
        reindexForClass(PatientActionPre.class, patientActionPreRepository, patientActionPreSearchRepository);
        reindexForClass(PatientActionTake.class, patientActionTakeRepository, patientActionTakeSearchRepository);
        reindexForClass(PatientMedication.class, patientMedicationRepository, patientMedicationSearchRepository);
        reindexForClass(PatientMedicationPres.class, patientMedicationPresRepository, patientMedicationPresSearchRepository);
        reindexForClass(PatientMedicationTake.class, patientMedicationTakeRepository, patientMedicationTakeSearchRepository);

        log.info("Elasticsearch: Successfully performed reindexing");
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    private <T> void reindexForClass(Class<T> entityClass, JpaRepository<T, Long> jpaRepository,
                                                          ElasticsearchRepository<T, Long> elasticsearchRepository) {
        elasticsearchTemplate.deleteIndex(entityClass);
        try {
            elasticsearchTemplate.createIndex(entityClass);
        } catch (IndexAlreadyExistsException e) {
            // Do nothing. Index was already concurrently recreated by some other service.
        }
        elasticsearchTemplate.putMapping(entityClass);
        if (jpaRepository.count() > 0) {
            try {
                Method m = jpaRepository.getClass().getMethod("findAllWithEagerRelationships");
                elasticsearchRepository.save((List<T>) m.invoke(jpaRepository));
            } catch (Exception e) {
                elasticsearchRepository.save(jpaRepository.findAll());
            }
        }
        log.info("Elasticsearch: Indexed all rows for " + entityClass.getSimpleName());
    }
}
