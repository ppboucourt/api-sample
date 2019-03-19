'use strict';

describe('Controller Tests', function() {

    describe('Chart Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChart, MockPatient, MockCareManager, MockBed, MockChartToGroupSession, MockConcurrentReviews, MockChartToMedications, MockContacts, MockInsurance, MockOrders, MockVitals, MockWeight, MockLabRequisition, MockEmployee, MockTypePatientPrograms, MockTypeLevelCare, MockFacility, MockGlucose, MockTypeAdmissionStatus, MockChartToAction, MockGroupSessionDetailsChart, MockShifts, MockIcd10, MockTreatmentHistory, MockHospitalization, MockTypeDischargeType, MockTypePaymentMethods, MockDrugs, MockAllergies, MockDiet, MockTypeMaritalStatus, MockTypeEthnicity, MockChartToLevelCare, MockChartToIcd10;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChart = jasmine.createSpy('MockChart');
            MockPatient = jasmine.createSpy('MockPatient');
            MockCareManager = jasmine.createSpy('MockCareManager');
            MockBed = jasmine.createSpy('MockBed');
            MockChartToGroupSession = jasmine.createSpy('MockChartToGroupSession');
            MockConcurrentReviews = jasmine.createSpy('MockConcurrentReviews');
            MockChartToMedications = jasmine.createSpy('MockChartToMedications');
            MockContacts = jasmine.createSpy('MockContacts');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockOrders = jasmine.createSpy('MockOrders');
            MockVitals = jasmine.createSpy('MockVitals');
            MockWeight = jasmine.createSpy('MockWeight');
            MockLabRequisition = jasmine.createSpy('MockLabRequisition');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockTypePatientPrograms = jasmine.createSpy('MockTypePatientPrograms');
            MockTypeLevelCare = jasmine.createSpy('MockTypeLevelCare');
            MockFacility = jasmine.createSpy('MockFacility');
            MockGlucose = jasmine.createSpy('MockGlucose');
            MockTypeAdmissionStatus = jasmine.createSpy('MockTypeAdmissionStatus');
            MockChartToAction = jasmine.createSpy('MockChartToAction');
            MockGroupSessionDetailsChart = jasmine.createSpy('MockGroupSessionDetailsChart');
            MockShifts = jasmine.createSpy('MockShifts');
            MockIcd10 = jasmine.createSpy('MockIcd10');
            MockTreatmentHistory = jasmine.createSpy('MockTreatmentHistory');
            MockHospitalization = jasmine.createSpy('MockHospitalization');
            MockTypeDischargeType = jasmine.createSpy('MockTypeDischargeType');
            MockTypePaymentMethods = jasmine.createSpy('MockTypePaymentMethods');
            MockDrugs = jasmine.createSpy('MockDrugs');
            MockAllergies = jasmine.createSpy('MockAllergies');
            MockDiet = jasmine.createSpy('MockDiet');
            MockTypeMaritalStatus = jasmine.createSpy('MockTypeMaritalStatus');
            MockTypeEthnicity = jasmine.createSpy('MockTypeEthnicity');
            MockChartToLevelCare = jasmine.createSpy('MockChartToLevelCare');
            MockChartToIcd10 = jasmine.createSpy('MockChartToIcd10');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Chart': MockChart,
                'Patient': MockPatient,
                'CareManager': MockCareManager,
                'Bed': MockBed,
                'ChartToGroupSession': MockChartToGroupSession,
                'ConcurrentReviews': MockConcurrentReviews,
                'ChartToMedications': MockChartToMedications,
                'Contacts': MockContacts,
                'Insurance': MockInsurance,
                'Orders': MockOrders,
                'Vitals': MockVitals,
                'Weight': MockWeight,
                'LabRequisition': MockLabRequisition,
                'Employee': MockEmployee,
                'TypePatientPrograms': MockTypePatientPrograms,
                'TypeLevelCare': MockTypeLevelCare,
                'Facility': MockFacility,
                'Glucose': MockGlucose,
                'TypeAdmissionStatus': MockTypeAdmissionStatus,
                'ChartToAction': MockChartToAction,
                'GroupSessionDetailsChart': MockGroupSessionDetailsChart,
                'Shifts': MockShifts,
                'Icd10': MockIcd10,
                'TreatmentHistory': MockTreatmentHistory,
                'Hospitalization': MockHospitalization,
                'TypeDischargeType': MockTypeDischargeType,
                'TypePaymentMethods': MockTypePaymentMethods,
                'Drugs': MockDrugs,
                'Allergies': MockAllergies,
                'Diet': MockDiet,
                'TypeMaritalStatus': MockTypeMaritalStatus,
                'TypeEthnicity': MockTypeEthnicity,
                'ChartToLevelCare': MockChartToLevelCare,
                'ChartToIcd10': MockChartToIcd10
            };
            createController = function() {
                $injector.get('$controller')("ChartDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:chartUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
