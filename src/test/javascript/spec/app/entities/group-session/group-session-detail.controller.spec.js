'use strict';

describe('Controller Tests', function() {

    describe('GroupSession Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGroupSession, MockChartToGroupSession, MockGroupType, MockFacility, MockGroupSessionDetails;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGroupSession = jasmine.createSpy('MockGroupSession');
            MockChartToGroupSession = jasmine.createSpy('MockChartToGroupSession');
            MockGroupType = jasmine.createSpy('MockGroupType');
            MockFacility = jasmine.createSpy('MockFacility');
            MockGroupSessionDetails = jasmine.createSpy('MockGroupSessionDetails');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GroupSession': MockGroupSession,
                'ChartToGroupSession': MockChartToGroupSession,
                'GroupType': MockGroupType,
                'Facility': MockFacility,
                'GroupSessionDetails': MockGroupSessionDetails
            };
            createController = function() {
                $injector.get('$controller')("GroupSessionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:groupSessionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
