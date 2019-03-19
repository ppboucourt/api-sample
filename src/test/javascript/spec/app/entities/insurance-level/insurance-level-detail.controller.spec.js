'use strict';

describe('Controller Tests', function() {

    describe('InsuranceLevel Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsuranceLevel, MockInsurance, MockCorporation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsuranceLevel = jasmine.createSpy('MockInsuranceLevel');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockCorporation = jasmine.createSpy('MockCorporation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'InsuranceLevel': MockInsuranceLevel,
                'Insurance': MockInsurance,
                'Corporation': MockCorporation
            };
            createController = function() {
                $injector.get('$controller')("InsuranceLevelDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:insuranceLevelUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
