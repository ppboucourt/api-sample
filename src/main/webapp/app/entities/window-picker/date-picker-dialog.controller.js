(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DatePickerDialogController', DatePickerDialogController);

    DatePickerDialogController.$inject = ['date', '$uibModalInstance'];

    function DatePickerDialogController (date, $uibModalInstance) {
        var vm = this;

        vm.date = date;
        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.date = false;

        vm.openCalendar = openCalendar;
        function openCalendar(date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.accept = function () {
            $uibModalInstance.close({date: vm.date});
        }
    }
})();
