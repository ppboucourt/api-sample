'use strict';

describe('Controller Tests', function() {

    describe('PatientMedicationPres Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientMedicationPres, MockIcd10, MockPatientMedicationTake, MockLabCompendium, MockPatientMedication, MockMedicationFrequency;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientMedicationPres = jasmine.createSpy('MockPatientMedicationPres');
            MockIcd10 = jasmine.createSpy('MockIcd10');
            MockPatientMedicationTake = jasmine.createSpy('MockPatientMedicationTake');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            MockPatientMedication = jasmine.createSpy('MockPatientMedication');
            MockMedicationFrequency = jasmine.createSpy('MockMedicationFrequency');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientMedicationPres': MockPatientMedicationPres,
                'Icd10': MockIcd10,
                'PatientMedicationTake': MockPatientMedicationTake,
                'LabCompendium': MockLabCompendium,
                'PatientMedication': MockPatientMedication,
                'MedicationFrequency': MockMedicationFrequency
            };
            createController = function() {
                $injector.get('$controller')("PatientMedicationPresDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientMedicationPresUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
