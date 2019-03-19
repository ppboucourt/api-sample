(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientToShiftDetailController', PatientToShiftDetailController);

    PatientToShiftDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientToShift', 'Shifts', 'Chart'];

    function PatientToShiftDetailController($scope, $rootScope, $stateParams, previousState, entity, PatientToShift, Shifts, Chart) {
        var vm = this;

        vm.patientToShift = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:patientToShiftUpdate', function(event, result) {
            vm.patientToShift = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
