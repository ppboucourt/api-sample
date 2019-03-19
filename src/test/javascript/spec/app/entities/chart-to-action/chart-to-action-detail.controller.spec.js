'use strict';

describe('Controller Tests', function() {

    describe('ChartToAction Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChartToAction, MockChart, MockActions;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChartToAction = jasmine.createSpy('MockChartToAction');
            MockChart = jasmine.createSpy('MockChart');
            MockActions = jasmine.createSpy('MockActions');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ChartToAction': MockChartToAction,
                'Chart': MockChart,
                'Actions': MockActions
            };
            createController = function() {
                $injector.get('$controller')("ChartToActionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:chartToActionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
