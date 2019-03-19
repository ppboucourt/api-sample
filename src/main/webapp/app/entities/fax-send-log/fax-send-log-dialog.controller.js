(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FaxSendLogDialogController', FaxSendLogDialogController);

    FaxSendLogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FaxSendLog'];

    function FaxSendLogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FaxSendLog) {
        var vm = this;

        vm.faxSendLog = entity;
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
            if (vm.faxSendLog.id !== null) {
                FaxSendLog.update(vm.faxSendLog, onSaveSuccess, onSaveError);
            } else {
                FaxSendLog.save(vm.faxSendLog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:faxSendLogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.sendDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
