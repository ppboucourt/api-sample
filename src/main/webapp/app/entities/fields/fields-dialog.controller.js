(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FieldsDialogController', FieldsDialogController);

    FieldsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Fields', 'Tables', 'ReportDetails'];

    function FieldsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Fields, Tables, ReportDetails) {
        var vm = this;

        vm.fields = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tables = Tables.query();
        vm.reportdetails = ReportDetails.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.fields.id !== null) {
                Fields.update(vm.fields, onSaveSuccess, onSaveError);
            } else {
                Fields.save(vm.fields, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:fieldsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
