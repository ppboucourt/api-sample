'use strict';

describe('Controller Tests', function() {

    describe('LabResults Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLabResults, MockLabRequisition, MockLabCompendium;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLabResults = jasmine.createSpy('MockLabResults');
            MockLabRequisition = jasmine.createSpy('MockLabRequisition');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LabResults': MockLabResults,
                'LabRequisition': MockLabRequisition,
                'LabCompendium': MockLabCompendium
            };
            createController = function() {
                $injector.get('$controller')("LabResultsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:labResultsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
