(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabProfileDetailController', LabProfileDetailController);

    LabProfileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState',
        'entity', 'LabProfile', 'LabCompendium', '$state'];

    function LabProfileDetailController($scope, $rootScope, $stateParams, previousState,
                                        entity, LabProfile, LabCompendium, $state) {
        var vm = this;

        //Functions
        vm.changeEditingState = changeEditingState;
        vm.save = save;

        //Variable
        vm.labProfile = entity;
        vm.previousState = previousState.name;
        vm.compendiums = LabCompendium.query();

        var unsubscribe = $rootScope.$on('bluebookApp:labProfileUpdate', function(event, result) {
            vm.labProfile = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        function save () {
            vm.isSaving = true;
            LabProfile.update(vm.labProfile, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labProfileUpdate', result);
            vm.isSaving = false;
            $state.go('lab-profile', null, { reload: 'lab-profile' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
