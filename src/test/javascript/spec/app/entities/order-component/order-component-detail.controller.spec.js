'use strict';

describe('Controller Tests', function() {

    describe('OrderComponent Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockOrderComponent, MockAllOrders, MockRoutes;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockOrderComponent = jasmine.createSpy('MockOrderComponent');
            MockAllOrders = jasmine.createSpy('MockAllOrders');
            MockRoutes = jasmine.createSpy('MockRoutes');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'OrderComponent': MockOrderComponent,
                'AllOrders': MockAllOrders,
                'Routes': MockRoutes
            };
            createController = function() {
                $injector.get('$controller')("OrderComponentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:orderComponentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
