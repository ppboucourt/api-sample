'use strict';

describe('Controller Tests', function() {

    describe('TypeAdmissionStatus Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTypeAdmissionStatus, MockChart, MockFacility;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTypeAdmissionStatus = jasmine.createSpy('MockTypeAdmissionStatus');
            MockChart = jasmine.createSpy('MockChart');
            MockFacility = jasmine.createSpy('MockFacility');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TypeAdmissionStatus': MockTypeAdmissionStatus,
                'Chart': MockChart,
                'Facility': MockFacility
            };
            createController = function() {
                $injector.get('$controller')("TypeAdmissionStatusDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:typeAdmissionStatusUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});