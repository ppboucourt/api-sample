'use strict';

describe('Controller Tests', function() {

    describe('ConcurrentReviews Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockConcurrentReviews, MockChart;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockConcurrentReviews = jasmine.createSpy('MockConcurrentReviews');
            MockChart = jasmine.createSpy('MockChart');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ConcurrentReviews': MockConcurrentReviews,
                'Chart': MockChart
            };
            createController = function() {
                $injector.get('$controller')("ConcurrentReviewsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:concurrentReviewsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
