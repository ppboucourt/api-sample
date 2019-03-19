(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderFinishController', PatientOrderFinishController);

    PatientOrderFinishController.$inject = ['$uibModalInstance', 'patientOrder', 'PatientOrder'];

    function PatientOrderFinishController($uibModalInstance, patientOrder, PatientOrder) {
        var vm = this;

        vm.patientOrder = patientOrder;
        vm.clear = clear;
        vm.confirmFinish = confirmFinish;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmFinish(id) {
            PatientOrder.finish({id: id},
                function () {
                    $uibModalInstance.close(true);
                }
            );
        }
    }
})();
