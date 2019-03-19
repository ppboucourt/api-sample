'use strict';

describe('Controller Tests', function() {

    describe('GroupSessionDetailsChart Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGroupSessionDetailsChart, MockGroupSessionDetails, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGroupSessionDetailsChart = jasmine.createSpy('MockGroupSessionDetailsChart');
            MockGroupSessionDetails = jasmine.createSpy('MockGroupSessionDetails');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GroupSessionDetailsChart': MockGroupSessionDetailsChart,
                'GroupSessionDetails': MockGroupSessionDetails,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("GroupSessionDetailsChartDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:groupSessionDetailsChartUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
