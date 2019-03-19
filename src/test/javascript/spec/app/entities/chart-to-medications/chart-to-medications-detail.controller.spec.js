'use strict';

describe('Controller Tests', function() {

    describe('ChartToMedications Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChartToMedications, MockChart, MockMedications;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChartToMedications = jasmine.createSpy('MockChartToMedications');
            MockChart = jasmine.createSpy('MockChart');
            MockMedications = jasmine.createSpy('MockMedications');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ChartToMedications': MockChartToMedications,
                'Chart': MockChart,
                'Medications': MockMedications
            };
            createController = function() {
                $injector.get('$controller')("ChartToMedicationsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:chartToMedicationsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
