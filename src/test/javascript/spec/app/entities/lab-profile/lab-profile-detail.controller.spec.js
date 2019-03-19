'use strict';

describe('Controller Tests', function() {

    describe('LabProfile Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLabProfile, MockLabCompendium, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLabProfile = jasmine.createSpy('MockLabProfile');
            MockLabCompendium = jasmine.createSpy('MockLabCompendium');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'LabProfile': MockLabProfile,
                'LabCompendium': MockLabCompendium,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("LabProfileDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:labProfileUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
