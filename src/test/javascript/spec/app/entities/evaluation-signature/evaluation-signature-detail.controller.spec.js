'use strict';

describe('Controller Tests', function() {

    describe('EvaluationSignature Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockEvaluationSignature, MockEvaluation, MockEmployee, MockSignature;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockEvaluationSignature = jasmine.createSpy('MockEvaluationSignature');
            MockEvaluation = jasmine.createSpy('MockEvaluation');
            MockEmployee = jasmine.createSpy('MockEmployee');
            MockSignature = jasmine.createSpy('MockSignature');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'EvaluationSignature': MockEvaluationSignature,
                'Evaluation': MockEvaluation,
                'Employee': MockEmployee,
                'Signature': MockSignature
            };
            createController = function() {
                $injector.get('$controller')("EvaluationSignatureDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:evaluationSignatureUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
