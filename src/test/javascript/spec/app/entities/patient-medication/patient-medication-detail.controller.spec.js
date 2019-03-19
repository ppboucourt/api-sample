'use strict';

describe('Controller Tests', function() {

    describe('PatientMedication Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientMedication, MockPatientMedicationPres, MockVia, MockChart, MockEmployee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientMedication = jasmine.createSpy('MockPatientMedication');
            MockPatientMedicationPres = jasmine.createSpy('MockPatientMedicationPres');
            MockVia = jasmine.createSpy('MockVia');
            MockChart = jasmine.createSpy('MockChart');
            MockEmployee = jasmine.createSpy('MockEmployee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientMedication': MockPatientMedication,
                'PatientMedicationPres': MockPatientMedicationPres,
                'Via': MockVia,
                'Chart': MockChart,
                'Employee': MockEmployee
            };
            createController = function() {
                $injector.get('$controller')("PatientMedicationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientMedicationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
