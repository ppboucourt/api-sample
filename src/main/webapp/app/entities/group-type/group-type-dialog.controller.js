(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupTypeDialogController', GroupTypeDialogController);

    GroupTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupType', 'GroupSession'];

    function GroupTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GroupType, GroupSession) {
        var vm = this;

        vm.groupType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.groupsessions = GroupSession.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.groupType.id !== null) {
                GroupType.update(vm.groupType, onSaveSuccess, onSaveError);
            } else {
                GroupType.save(vm.groupType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:groupTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
