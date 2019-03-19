'use strict';

describe('Controller Tests', function() {

    describe('LabRequisition Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLabRequisition, MockLabCompendium, MockLabRequisitionsComponents, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLabRequisition = jasmine.createSpy('MockLabRequisition');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            MockLabRequisitionsComponents = jasmine.createSpy('MockLabRequisitionsComponents');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LabRequisition': MockLabRequisition,
                'LabCompendium': MockLabCompendium,
                'LabRequisitionsComponents': MockLabRequisitionsComponents,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("LabRequisitionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:labRequisitionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
