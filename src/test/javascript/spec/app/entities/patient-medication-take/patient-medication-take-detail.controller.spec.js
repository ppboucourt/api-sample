'use strict';

describe('Controller Tests', function() {

    describe('PatientMedicationTake Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientMedicationTake, MockPatientMedicationPres;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientMedicationTake = jasmine.createSpy('MockPatientMedicationTake');
            MockPatientMedicationPres = jasmine.createSpy('MockPatientMedicationPres');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientMedicationTake': MockPatientMedicationTake,
                'PatientMedicationPres': MockPatientMedicationPres
            };
            createController = function() {
                $injector.get('$controller')("PatientMedicationTakeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientMedicationTakeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
