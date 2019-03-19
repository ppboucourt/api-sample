(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeDetailsDialogController', PatientMedicationTakeDetailsDialogController);

    PatientMedicationTakeDetailsDialogController.$inject = ['$timeout', 'CoreService', '$uibModalInstance', 'patientMedicationTake', 'PatientMedicationTake'];

    function PatientMedicationTakeDetailsDialogController($timeout,  CoreService, $uibModalInstance, patientMedicationTake, PatientMedicationTake) {
        var vm = this;

        vm.clear = clear;
        vm.save = save;


        $timeout(function(data){
            vm.patientMedicationTake = patientMedicationTake;
            console.log(vm.patientMedicationTake);
            vm.patientMedicationTake.collectedDateString = CoreService.parseToDateTime(vm.patientMedicationTake.collectedDate);
          //  alert(vm.patientMedicationTake.collectedDateString)


        }, 250);




        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
