(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactsRelationshipDialogController', TypePatientContactsRelationshipDialogController);

    TypePatientContactsRelationshipDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePatientContactsRelationship'];

    function TypePatientContactsRelationshipDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePatientContactsRelationship) {
        var vm = this;

        vm.typePatientContactsRelationship = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typePatientContactsRelationship.id !== null) {
                TypePatientContactsRelationship.update(vm.typePatientContactsRelationship, onSaveSuccess, onSaveError);
            } else {
                TypePatientContactsRelationship.save(vm.typePatientContactsRelationship, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePatientContactsRelationshipUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
