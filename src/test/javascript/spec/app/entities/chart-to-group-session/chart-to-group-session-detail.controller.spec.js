'use strict';

describe('Controller Tests', function() {

    describe('ChartToGroupSession Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockChartToGroupSession, MockChart, MockGroupSession;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockChartToGroupSession = jasmine.createSpy('MockChartToGroupSession');
            MockChart = jasmine.createSpy('MockChart');
            MockGroupSession = jasmine.createSpy('MockGroupSession');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ChartToGroupSession': MockChartToGroupSession,
                'Chart': MockChart,
                'GroupSession': MockGroupSession
            };
            createController = function() {
                $injector.get('$controller')("ChartToGroupSessionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:chartToGroupSessionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
