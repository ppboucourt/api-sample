'use strict';

describe('Controller Tests', function() {

    describe('MedicationFrequency Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockMedicationFrequency, MockPatientMedicationPrescription;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockMedicationFrequency = jasmine.createSpy('MockMedicationFrequency');
            MockPatientMedicationPrescription = jasmine.createSpy('MockPatientMedicationPrescription');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'MedicationFrequency': MockMedicationFrequency,
                'PatientMedicationPrescription': MockPatientMedicationPrescription
            };
            createController = function() {
                $injector.get('$controller')("MedicationFrequencyDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:medicationFrequencyUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
