'use strict';

describe('Controller Tests', function() {

    describe('ContactsFacility Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContactsFacility, MockFacility, MockTypeContactFacilityType, MockCountryState;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContactsFacility = jasmine.createSpy('MockContactsFacility');
            MockFacility = jasmine.createSpy('MockFacility');
            MockTypeContactFacilityType = jasmine.createSpy('MockTypeContactFacilityType');
            MockCountryState = jasmine.createSpy('MockCountryState');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ContactsFacility': MockContactsFacility,
                'Facility': MockFacility,
                'TypeContactFacilityType': MockTypeContactFacilityType,
                'CountryState': MockCountryState
            };
            createController = function() {
                $injector.get('$controller')("ContactsFacilityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:contactsFacilityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
