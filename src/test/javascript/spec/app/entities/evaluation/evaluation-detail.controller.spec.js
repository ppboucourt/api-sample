'use strict';

describe('Controller Tests', function() {

    describe('Evaluation Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluation, MockTypePatientProcess, MockTypeEvaluation, MockEvaluationContent, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluation = jasmine.createSpy('MockEvaluation');
            MockTypePatientProcess = jasmine.createSpy('MockTypePatientProcess');
            MockTypeEvaluation = jasmine.createSpy('MockTypeEvaluation');
            MockEvaluationContent = jasmine.createSpy('MockEvaluationContent');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Evaluation': MockEvaluation,
                'TypePatientProcess': MockTypePatientProcess,
                'TypeEvaluation': MockTypeEvaluation,
                'EvaluationContent': MockEvaluationContent,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("EvaluationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:evaluationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
