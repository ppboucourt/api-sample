(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DiagnosisDialogController', DiagnosisDialogController);

    DiagnosisDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity'];

    function DiagnosisDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity) {
        var vm = this;

        vm.diagnoses = entity.icd10s;
        vm.clear = clear;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
