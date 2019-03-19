'use strict';

describe('Controller Tests', function() {

    describe('TypePatientProcess Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTypePatientProcess, MockTypePage, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTypePatientProcess = jasmine.createSpy('MockTypePatientProcess');
            MockTypePage = jasmine.createSpy('MockTypePage');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TypePatientProcess': MockTypePatientProcess,
                'TypePage': MockTypePage,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("TypePatientProcessDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:typePatientProcessUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
