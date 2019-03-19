(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartCareTeamAddController',ChartCareTeamAddController);

    ChartCareTeamAddController.$inject = ['$uibModalInstance', '$stateParams', 'entity', 'ChartCareTeam', 'Employee', 'TypeSpeciality', 'Chart'];

    function ChartCareTeamAddController($uibModalInstance, $stateParams, entity, ChartCareTeam, Employee, TypeSpeciality, Chart) {
        var vm = this;

        vm.chartCareTeamMember = entity;
        vm.clear = clear;
        vm.confirmAddEdit = confirmAddEdit;
        vm.isSaving = true;


        TypeSpeciality.query(function (result) {
            vm.typeSpecialities = result;
        });

        Employee.query(function (result) {
            vm.employees = result;
        });


        Chart.get({id:$stateParams.id}, function (result) {
            vm.currentChart = result;
            vm.chartCareTeamMember.chart = vm.currentChart;
            vm.isSaving = false;
        })

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmAddEdit () {
                vm.isSavingCareTeamMember = true;
                vm.chartCareTeamMember.chart = vm.currentChart;
                if (vm.chartCareTeamMember.employee) {
                    vm.chartCareTeamMember.employeeId = vm.chartCareTeamMember.employee.id;
                }
                if (vm.chartCareTeamMember.id !== null) {
                    ChartCareTeam.update(vm.chartCareTeamMember, onSaveCareTeamMemberSuccess, onSaveCareTeamMemberError);
                } else {
                    ChartCareTeam.save(vm.chartCareTeamMember, onSaveCareTeamMemberSuccess, onSaveCareTeamMemberError);
                }
        }

        function onSaveCareTeamMemberSuccess(result) {
            vm.editingCareTeamMember = false;
            $uibModalInstance.close(true);
        }

        function onSaveCareTeamMemberError() {
            vm.isSavingCareTeamMember = false;
        }
    }
})();
