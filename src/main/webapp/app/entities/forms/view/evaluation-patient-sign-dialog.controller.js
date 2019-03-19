(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationPatientSignController', EvaluationPatientSignController);

    EvaluationPatientSignController.$inject = ['$uibModalInstance', 'CoreService', 'JSignature'];

    function EvaluationPatientSignController($uibModalInstance, CoreService, JSignature) {
        var vm = this;

        vm.clear = clear;
        vm.confirmSign = confirmSign;

        vm.result = {
            agree: false,
            signed: false
        }

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSign() {

            if (!JSignature.getData(JSignature.exportTypes.NATIVE).length) {
                alert("Please sign and then press finish!!!");
            } else {
                var sign = JSignature.getData(JSignature.exportTypes.IMAGE_PNG_BASE64);

                $uibModalInstance.close(sign);
            }

        }
    }
})();
