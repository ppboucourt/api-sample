'use strict';

describe('Controller Tests', function() {

    describe('ChartToIcd10 Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChartToIcd10, MockChart, MockIcd10;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChartToIcd10 = jasmine.createSpy('MockChartToIcd10');
            MockChart = jasmine.createSpy('MockChart');
            MockIcd10 = jasmine.createSpy('MockIcd10');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ChartToIcd10': MockChartToIcd10,
                'Chart': MockChart,
                'Icd10': MockIcd10
            };
            createController = function() {
                $injector.get('$controller')("ChartToIcd10DetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:chartToIcd10Update';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
