(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceRelationDialogController', InsuranceRelationDialogController);

    InsuranceRelationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceRelation', 'Insurance', 'Corporation'];

    function InsuranceRelationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceRelation, Insurance, Corporation) {
        var vm = this;

        vm.insuranceRelation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.insurances = Insurance.query();
        vm.corporations = Corporation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insuranceRelation.id !== null) {
                InsuranceRelation.update(vm.insuranceRelation, onSaveSuccess, onSaveError);
            } else {
                InsuranceRelation.save(vm.insuranceRelation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceRelationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
