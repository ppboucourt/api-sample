'use strict';

describe('Controller Tests', function() {

    describe('UrgentIssues Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockUrgentIssues, MockChart, MockEmployee, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockUrgentIssues = jasmine.createSpy('MockUrgentIssues');
            MockChart = jasmine.createSpy('MockChart');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'UrgentIssues': MockUrgentIssues,
                'Chart': MockChart,
                'Employee': MockEmployee,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("UrgentIssuesDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:urgentIssuesUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
