'use strict';

describe('Controller Tests', function() {

    describe('PatientActionTake Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientActionTake, MockPatientActionPres;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientActionTake = jasmine.createSpy('MockPatientActionTake');
            MockPatientActionPres = jasmine.createSpy('MockPatientActionPres');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientActionTake': MockPatientActionTake,
                'PatientActionPres': MockPatientActionPres
            };
            createController = function() {
                $injector.get('$controller')("PatientActionTakeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientActionTakeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
