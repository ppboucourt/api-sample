(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeDetailsDialogController', PatientActionTakeDetailsDialogController);

    PatientActionTakeDetailsDialogController.$inject = ['$timeout', 'CoreService', '$uibModalInstance', 'patientActionTake'];

    function PatientActionTakeDetailsDialogController($timeout,  CoreService, $uibModalInstance, patientActionTake) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;

        $timeout(function(){
            vm.patientActionTake = patientActionTake;
            vm.patientActionTake.collectedDateString = CoreService.parseToDateTime(vm.patientActionTake.collectedDate);

        }, 250);

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
