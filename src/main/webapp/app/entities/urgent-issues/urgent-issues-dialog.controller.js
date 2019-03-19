(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UrgentIssuesDialogController', UrgentIssuesDialogController);

    UrgentIssuesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'urgentIssue', 'UrgentIssues',
        'chart', 'Employee', 'CoreService'];

    function UrgentIssuesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, urgentIssue, UrgentIssues,
                                           chart, Employee, CoreService) {
        var vm = this;

        vm.urgentIssues = urgentIssue;
        vm.urgentIssues.chart = {};
        vm.urgentIssues.employees = [];
        vm.clear = clear;
        vm.save = save;
        // vm.charts = Chart.query();
        // vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.urgentIssues.facility = CoreService.getCurrentFacility();
            if (vm.urgentIssues.id !== null) {
                UrgentIssues.update(vm.urgentIssues, onSaveSuccess, onSaveError);
            } else {
                vm.urgentIssues.chart = chart;
                vm.urgentIssues.employees.push(CoreService.getCurrentEmployee());
                UrgentIssues.save(vm.urgentIssues, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:urgentIssuesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
