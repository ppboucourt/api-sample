'use strict';

describe('Controller Tests', function() {

    describe('InsuranceType Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsuranceType, MockInsurance, MockCorporation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsuranceType = jasmine.createSpy('MockInsuranceType');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockCorporation = jasmine.createSpy('MockCorporation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'InsuranceType': MockInsuranceType,
                'Insurance': MockInsurance,
                'Corporation': MockCorporation
            };
            createController = function() {
                $injector.get('$controller')("InsuranceTypeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:insuranceTypeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
