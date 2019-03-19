'use strict';

describe('Controller Tests', function() {

    describe('PatientActionPre Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPatientActionPre, MockIcd10, MockPatientActionTake, MockLabCompendium, MockPatientAction, MockActionFrequency;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPatientActionPre = jasmine.createSpy('MockPatientActionPre');
            MockIcd10 = jasmine.createSpy('MockIcd10');
            MockPatientActionTake = jasmine.createSpy('MockPatientActionTake');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            MockPatientAction = jasmine.createSpy('MockPatientAction');
            MockActionFrequency = jasmine.createSpy('MockActionFrequency');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PatientActionPre': MockPatientActionPre,
                'Icd10': MockIcd10,
                'PatientActionTake': MockPatientActionTake,
                'LabCompendium': MockLabCompendium,
                'PatientAction': MockPatientAction,
                'ActionFrequency': MockActionFrequency
            };
            createController = function() {
                $injector.get('$controller')("PatientActionPreDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:patientActionPreUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
