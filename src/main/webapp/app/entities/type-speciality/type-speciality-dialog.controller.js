(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeSpecialityDialogController', TypeSpecialityDialogController);

    TypeSpecialityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeSpeciality', 'Chart'];

    function TypeSpecialityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeSpeciality, Chart) {
        var vm = this;

        vm.typeSpecialitys = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeSpecialitys.id !== null) {
                TypeSpeciality.update(vm.typeSpecialitys, onSaveSuccess, onSaveError);
            } else {
                TypeSpeciality.save(vm.typeSpecialitys, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeSpecialitysUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
