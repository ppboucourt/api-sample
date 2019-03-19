(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsDialogController', GroupSessionDetailsDialogController);

    GroupSessionDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'GroupSessionDetails', 'Employee', 'GroupSessionDetailsChart', 'GroupSession', 'Signature'];

    function GroupSessionDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, GroupSessionDetails, Employee, GroupSessionDetailsChart, GroupSession, Signature) {
        var vm = this;

        vm.groupSessionDetails = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.employees = Employee.query();
        vm.groupsessiondetailscharts = GroupSessionDetailsChart.query();
        vm.groupsessions = GroupSession.query();
        vm.leadersignatures = Signature.query({filter: 'groupsessiondetails-is-null'});
        $q.all([vm.groupSessionDetails.$promise, vm.leadersignatures.$promise]).then(function() {
            if (!vm.groupSessionDetails.leaderSignature || !vm.groupSessionDetails.leaderSignature.id) {
                return $q.reject();
            }
            return Signature.get({id : vm.groupSessionDetails.leaderSignature.id}).$promise;
        }).then(function(leaderSignature) {
            vm.leadersignatures.push(leaderSignature);
        });
        vm.revisersignatures = Signature.query({filter: 'groupsessiondetails-is-null'});
        $q.all([vm.groupSessionDetails.$promise, vm.revisersignatures.$promise]).then(function() {
            if (!vm.groupSessionDetails.reviserSignature || !vm.groupSessionDetails.reviserSignature.id) {
                return $q.reject();
            }
            return Signature.get({id : vm.groupSessionDetails.reviserSignature.id}).$promise;
        }).then(function(reviserSignature) {
            vm.revisersignatures.push(reviserSignature);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.groupSessionDetails.id !== null) {
                GroupSessionDetails.update(vm.groupSessionDetails, onSaveSuccess, onSaveError);
            } else {
                GroupSessionDetails.save(vm.groupSessionDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:groupSessionDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.finalized = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
