(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabProfileNewController', LabProfileNewController);

    LabProfileNewController.$inject = ['$timeout', '$scope', 'entity', 'LabProfile', 'LabCompendium', '$state'];

    function LabProfileNewController ($timeout, $scope, entity, LabProfile, LabCompendium, $state) {
        var vm = this;

        //Funtion
        vm.save = save;

        //Variable
        vm.labProfile = entity;
        vm.labcompendiums = LabCompendium.query();
        vm.compendiums = LabCompendium.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            LabProfile.save(vm.labProfile, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labProfileUpdate', result);
            vm.isSaving = false;
            $state.go('lab-profile', null, { reload: 'lab-profile' });
        }

        function onSaveError () {
            vm.isSaving = false;
            $state.go('lab-profile', null, { reload: 'lab-profile' });
        }


    }
})();
