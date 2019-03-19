'use strict';

describe('Controller Tests', function() {

    describe('LabRequisitionsComponents Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLabRequisitionsComponents, MockLabRequisition, MockLabCompendium;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLabRequisitionsComponents = jasmine.createSpy('MockLabRequisitionsComponents');
            MockLabRequisition = jasmine.createSpy('MockLabRequisition');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LabRequisitionsComponents': MockLabRequisitionsComponents,
                'LabRequisition': MockLabRequisition,
                'LabCompendium': MockLabCompendium
            };
            createController = function() {
                $injector.get('$controller')("LabRequisitionsComponentsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:labRequisitionsComponentsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
