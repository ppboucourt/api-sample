'use strict';

describe('Controller Tests', function() {

    describe('InsuranceRelation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockInsuranceRelation, MockInsurance, MockCorporation;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockInsuranceRelation = jasmine.createSpy('MockInsuranceRelation');
            MockInsurance = jasmine.createSpy('MockInsurance');
            MockCorporation = jasmine.createSpy('MockCorporation');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'InsuranceRelation': MockInsuranceRelation,
                'Insurance': MockInsurance,
                'Corporation': MockCorporation
            };
            createController = function() {
                $injector.get('$controller')("InsuranceRelationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:insuranceRelationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
