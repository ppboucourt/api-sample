(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UrgentIssuesEmployeeController',UrgentIssuesEmployeeController);

    UrgentIssuesEmployeeController.$inject = ['$uibModalInstance', 'urgentIssue', 'DTOptionsBuilder', 'DTColumnBuilder', '$q'];

    function UrgentIssuesEmployeeController($uibModalInstance, urgentIssue, DTOptionsBuilder, DTColumnBuilder, $q) {
        var vm = this;

        vm.employees = urgentIssue.employees;

        vm.clear = clear;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
    }
})();
