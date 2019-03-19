'use strict';

describe('Controller Tests', function() {

    describe('GroupSessionDetails Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGroupSessionDetails, MockEmployee, MockGroupSessionDetailsChart, MockGroupSession;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGroupSessionDetails = jasmine.createSpy('MockGroupSessionDetails');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockGroupSessionDetailsChart = jasmine.createSpy('MockGroupSessionDetailsChart');
            MockGroupSession = jasmine.createSpy('MockGroupSession');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GroupSessionDetails': MockGroupSessionDetails,
                'Employee': MockEmployee,
                'GroupSessionDetailsChart': MockGroupSessionDetailsChart,
                'GroupSession': MockGroupSession
            };
            createController = function() {
                $injector.get('$controller')("GroupSessionDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:groupSessionDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
