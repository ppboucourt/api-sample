'use strict';

describe('Controller Tests', function() {

    describe('LabCompendium Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLabCompendium, MockLabRequisition, MockLabRequisitionsComponents, MockOrders, MockLabProfile;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            MockLabRequisition = jasmine.createSpy('MockLabRequisition');
            MockLabRequisitionsComponents = jasmine.createSpy('MockLabRequisitionsComponents');
            MockOrders = jasmine.createSpy('MockOrders');
            MockLabProfile = jasmine.createSpy('MockLabProfile');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LabCompendium': MockLabCompendium,
                'LabRequisition': MockLabRequisition,
                'LabRequisitionsComponents': MockLabRequisitionsComponents,
                'Orders': MockOrders,
                'LabProfile': MockLabProfile
            };
            createController = function() {
                $injector.get('$controller')("LabCompendiumDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:labCompendiumUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
