'use strict';

describe('Controller Tests', function() {

    describe('ReportDetails Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockReportDetails, MockReports, MockTables, MockFields;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockReportDetails = jasmine.createSpy('MockReportDetails');
            MockReports = jasmine.createSpy('MockReports');
            MockTables = jasmine.createSpy('MockTables');
            MockFields = jasmine.createSpy('MockFields');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ReportDetails': MockReportDetails,
                'Reports': MockReports,
                'Tables': MockTables,
                'Fields': MockFields
            };
            createController = function() {
                $injector.get('$controller')("ReportDetailsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'bluebookApp:reportDetailsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
