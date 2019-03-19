'use strict';

describe('Controller Tests', function() {

    describe('Diet Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockDiet, MockChart, MockTypeFoodDiets;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockDiet = jasmine.createSpy('MockDiet');
            MockChart = jasmine.createSpy('MockChart');
            MockTypeFoodDiets = jasmine.createSpy('MockTypeFoodDiets');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Diet': MockDiet,
                'Chart': MockChart,
                'TypeFoodDiets': MockTypeFoodDiets
            };
            createController = function() {
                $injector.get('$controller')("DietDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:dietUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
