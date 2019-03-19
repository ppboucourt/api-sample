'use strict';

describe('Controller Tests', function() {

    describe('Employee Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEmployee, MockShifts, MockChart, MockUser, MockCorporation, MockGroupSessionDetails, MockTypeEmployee, MockUrgentIssues;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockShifts = jasmine.createSpy('MockShifts');
            MockChart = jasmine.createSpy('MockChart');
            MockUser = jasmine.createSpy('MockUser');
            MockCorporation = jasmine.createSpy('MockCorporation');
            MockGroupSessionDetails = jasmine.createSpy('MockGroupSessionDetails');
            MockTypeEmployee = jasmine.createSpy('MockTypeEmployee');
            MockUrgentIssues = jasmine.createSpy('MockUrgentIssues');


            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Employee': MockEmployee,
                'Shifts': MockShifts,
                'Chart': MockChart,
                'User': MockUser,
                'Corporation': MockCorporation,
                'GroupSessionDetails': MockGroupSessionDetails,
                'TypeEmployee': MockTypeEmployee,
                'UrgentIssues': MockUrgentIssues,
            };
            createController = function() {
                $injector.get('$controller')("EmployeeDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:employeeUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
