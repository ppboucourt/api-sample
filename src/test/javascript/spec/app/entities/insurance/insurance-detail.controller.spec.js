'use strict';

describe('Controller Tests', function() {

    describe('Insurance Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsurance, MockInsuranceCarrier, MockInsuranceType, MockInsuranceLevel, MockInsuranceRelation, MockCountryState, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockInsuranceCarrier = jasmine.createSpy('MockInsuranceCarrier');
            MockInsuranceType = jasmine.createSpy('MockInsuranceType');
            MockInsuranceLevel = jasmine.createSpy('MockInsuranceLevel');
            MockInsuranceRelation = jasmine.createSpy('MockInsuranceRelation');
            MockCountryState = jasmine.createSpy('MockCountryState');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Insurance': MockInsurance,
                'InsuranceCarrier': MockInsuranceCarrier,
                'InsuranceType': MockInsuranceType,
                'InsuranceLevel': MockInsuranceLevel,
                'InsuranceRelation': MockInsuranceRelation,
                'CountryState': MockCountryState,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("InsuranceDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:insuranceUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
