'use strict';

describe('Controller Tests', function() {

    describe('Facility Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFacility, MockBuilding, MockContactsFacility, MockCorporation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFacility = jasmine.createSpy('MockFacility');
            MockBuilding = jasmine.createSpy('MockBuilding');
            MockContactsFacility = jasmine.createSpy('MockContactsFacility');
            MockCorporation = jasmine.createSpy('MockCorporation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Facility': MockFacility,
                'Building': MockBuilding,
                'ContactsFacility': MockContactsFacility,
                'Corporation': MockCorporation
            };
            createController = function() {
                $injector.get('$controller')("FacilityDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:facilityUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
