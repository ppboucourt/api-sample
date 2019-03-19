'use strict';

describe('Controller Tests', function() {

    describe('AllOrders Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAllOrders, MockTypeDosage, MockFacility, MockOrderComponent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAllOrders = jasmine.createSpy('MockAllOrders');
            MockTypeDosage = jasmine.createSpy('MockTypeDosage');
            MockFacility = jasmine.createSpy('MockFacility');
            MockOrderComponent = jasmine.createSpy('MockOrderComponent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AllOrders': MockAllOrders,
                'TypeDosage': MockTypeDosage,
                'Facility': MockFacility,
                'OrderComponent': MockOrderComponent
            };
            createController = function() {
                $injector.get('$controller')("AllOrdersDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:allOrdersUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
