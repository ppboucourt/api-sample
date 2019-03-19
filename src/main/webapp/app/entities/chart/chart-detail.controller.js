(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartDetailController', ChartDetailController);

    ChartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Chart', 'Patient', 'CareManager', 'Bed', 'ChartToGroupSession', 'ChartToIcd10', 'ConcurrentReviews', 'ChartToMedications', 'Contacts', 'Insurance', 'Orders', 'Vitals', 'Weight', 'LabRequisition', 'Employee', 'TypePatientPrograms', 'TypeLevelCare', 'Facility', 'Glucose', 'TypeAdmissionStatus', 'ChartToAction', 'GroupSessionDetailsChart', 'Shifts'];

    function ChartDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Chart, Patient, CareManager, Bed, ChartToGroupSession, ChartToIcd10, ConcurrentReviews, ChartToMedications, Contacts, Insurance, Orders, Vitals, Weight, LabRequisition, Employee, TypePatientPrograms, TypeLevelCare, Facility, Glucose, TypeAdmissionStatus, ChartToAction, GroupSessionDetailsChart, Shifts) {
        var vm = this;

        vm.chart = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:chartUpdate', function(event, result) {
            vm.chart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
