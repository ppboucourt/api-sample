'use strict';

describe('Controller Tests', function() {

    describe('Forms Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockForms, MockTypeFormRules, MockTypePatientProcess, MockLaboratories, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockForms = jasmine.createSpy('MockForms');
            MockTypeFormRules = jasmine.createSpy('MockTypeFormRules');
            MockTypePatientProcess = jasmine.createSpy('MockTypePatientProcess');
            MockLaboratories = jasmine.createSpy('MockLaboratories');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Forms': MockForms,
                'TypeFormRules': MockTypeFormRules,
                'TypePatientProcess': MockTypePatientProcess,
                'Laboratories': MockLaboratories,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("FormsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:formsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
