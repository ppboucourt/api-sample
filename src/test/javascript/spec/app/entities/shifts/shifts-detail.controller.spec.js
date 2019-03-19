'use strict';

describe('Controller Tests', function() {

    describe('Shifts Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockShifts, MockEmployee, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockShifts = jasmine.createSpy('MockShifts');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Shifts': MockShifts,
                'Employee': MockEmployee,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("ShiftsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:shiftsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
