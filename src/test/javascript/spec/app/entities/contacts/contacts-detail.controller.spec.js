'use strict';

describe('Controller Tests', function() {

    describe('Contacts Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockContacts, MockChart, MockTypePatientContactTypes;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockContacts = jasmine.createSpy('MockContacts');
            MockChart = jasmine.createSpy('MockChart');
            MockTypePatientContactTypes = jasmine.createSpy('MockTypePatientContactTypes');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Contacts': MockContacts,
                'Chart': MockChart,
                'TypePatientContactTypes': MockTypePatientContactTypes
            };
            createController = function() {
                $injector.get('$controller')("ContactsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:contactsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
