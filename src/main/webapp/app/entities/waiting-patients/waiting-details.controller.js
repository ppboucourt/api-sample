(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('WaitingDetailController', WaitingDetailController);

    WaitingDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Waiting', 'Employee', '$state', 'CoreService'];

    function WaitingDetailController($scope, $rootScope, $stateParams, previousState, entity, Waiting, Employee, $state, CoreService) {
        var vm = this;
        vm.changeEditingState = changeEditingState;
        vm.save = save;
        vm.form ={};
        vm.Waiting = entity;
        vm.previousState = previousState.name;

        Waiting.currentPatients({id: CoreService.getCurrentFacility().id}).$promise.then(function (data) {
                vm.currentPatients = data;
            }, function (reason) {
                console.log('Failed getting employee: ' + reason);
            }
        );


        var unsubscribe = $rootScope.$on('bluebookApp:WaitingUpdate', function(event, result) {
            vm.waiting = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function changeEditingState() {
            vm.editing = !vm.editing;
        }
        function save () {
            vm.isSaving = true;
            Waiting.update(vm.waiting, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labProfileUpdate', result);
            vm.isSaving = false;
            $state.go('waiting', null, { reload: 'waiting' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
