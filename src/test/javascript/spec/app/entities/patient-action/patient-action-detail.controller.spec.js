'use strict';

describe('Controller Tests', function() {

    describe('PatientAction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientAction, MockPatientActionPre, MockVia, MockChart, MockEmployee;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientAction = jasmine.createSpy('MockPatientAction');
            MockPatientActionPre = jasmine.createSpy('MockPatientActionPre');
            MockVia = jasmine.createSpy('MockVia');
            MockChart = jasmine.createSpy('MockChart');
            MockEmployee = jasmine.createSpy('MockEmployee');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientAction': MockPatientAction,
                'PatientActionPre': MockPatientActionPre,
                'Via': MockVia,
                'Chart': MockChart,
                'Employee': MockEmployee
            };
            createController = function() {
                $injector.get('$controller')("PatientActionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientActionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
