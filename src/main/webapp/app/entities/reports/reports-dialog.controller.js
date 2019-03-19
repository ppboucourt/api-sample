(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportsDialogController', ReportsDialogController);

    ReportsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Reports'];

    function ReportsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Reports) {
        var vm = this;

        vm.reports = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.reports.id !== null) {
                Reports.update(vm.reports, onSaveSuccess, onSaveError);
            } else {
                Reports.save(vm.reports, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:reportsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.start_date = false;
        vm.datePickerOpenStatus.end_date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
