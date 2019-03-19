(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientAsNeededDialogController', PatientAsNeededDialogController);

    PatientAsNeededDialogController.$inject = ['$uibModalInstance', 'PatientAction'];

    function PatientAsNeededDialogController($uibModalInstance, PatientAction) {
        var vm = this;

        vm.date = new Date();

        vm.form = {};
        vm.formProblem = {};
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.staringDate = false;

        vm.openCalendar = openCalendar;
        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.accept = function () {
            $uibModalInstance.close({date: vm.date});
        }

        vm.clear = function () {
            $uibModalInstance.close(null);
        }
    }
})();
