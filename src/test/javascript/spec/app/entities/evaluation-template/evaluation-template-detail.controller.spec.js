'use strict';

describe('Controller Tests', function() {

    describe('EvaluationTemplate Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluationTemplate, MockTypePatientProcess, MockTypeEvaluation, MockEvaluationContent, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluationTemplate = jasmine.createSpy('MockEvaluationTemplate');
            MockTypePatientProcess = jasmine.createSpy('MockTypePatientProcess');
            MockTypeEvaluation = jasmine.createSpy('MockTypeEvaluation');
            MockEvaluationContent = jasmine.createSpy('MockEvaluationContent');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EvaluationTemplate': MockEvaluationTemplate,
                'TypePatientProcess': MockTypePatientProcess,
                'TypeEvaluation': MockTypeEvaluation,
                'EvaluationContent': MockEvaluationContent,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("EvaluationTemplateDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:evaluationTemplateUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
