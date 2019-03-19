'use strict';

describe('Controller Tests', function() {

    describe('GlucoseIntervention Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockGlucoseIntervention, MockGlucose;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockGlucoseIntervention = jasmine.createSpy('MockGlucoseIntervention');
            MockGlucose = jasmine.createSpy('MockGlucose');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'GlucoseIntervention': MockGlucoseIntervention,
                'Glucose': MockGlucose
            };
            createController = function() {
                $injector.get('$controller')("GlucoseInterventionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:glucoseInterventionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
