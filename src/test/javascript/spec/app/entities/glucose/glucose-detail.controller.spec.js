'use strict';

describe('Controller Tests', function() {

    describe('Glucose Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGlucose, MockChart, MockGlucoseIntervention;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGlucose = jasmine.createSpy('MockGlucose');
            MockChart = jasmine.createSpy('MockChart');
            MockGlucoseIntervention = jasmine.createSpy('MockGlucoseIntervention');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Glucose': MockGlucose,
                'Chart': MockChart,
                'GlucoseIntervention': MockGlucoseIntervention
            };
            createController = function() {
                $injector.get('$controller')("GlucoseDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:glucoseUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
