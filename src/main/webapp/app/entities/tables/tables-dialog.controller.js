(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TablesDialogController', TablesDialogController);

    TablesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tables', 'Fields'];

    function TablesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tables, Fields) {
        var vm = this;

        vm.tables = entity;
        vm.clear = clear;
        vm.save = save;
        vm.fields = Fields.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tables.id !== null) {
                Tables.update(vm.tables, onSaveSuccess, onSaveError);
            } else {
                Tables.save(vm.tables, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:tablesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
