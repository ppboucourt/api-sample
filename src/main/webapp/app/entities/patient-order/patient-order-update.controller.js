(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderUpdateController', PatientOrderUpdateController);

    PatientOrderUpdateController.$inject = ['$state', '$rootScope', '$stateParams',
        'CoreService', 'PatientOrder', 'Via', 'Employee', 'chart', '$sessionStorage', 'lodash', '$scope', '$uibModal',
        'toastr', 'Chart'];

    function PatientOrderUpdateController($state, $rootScope, $stateParams, CoreService, PatientOrder, Via, Employee,
                                          chart, $sessionStorage, _, $scope, $uibModal, toastr, Chart) {
        var vm = this;
        vm.$sessionStorage = $sessionStorage;
        vm.signedBy = {};
        var chartId = ($rootScope.patientOrderChartId) ? $rootScope.patientOrderChartId : null;
        vm.chart = (!chartId) ? chart : Chart.get({id: chartId});
        $scope.$on('$destroy', function () {
            delete $rootScope.patientOrder;
            delete $rootScope.patientOrderChartId;
        });
        vm.patientOrder = _.clone($rootScope.patientOrder, true);
        vm.patientOrder.employee = CoreService.getCurrentEmployee();
        vm.patientOrder.employeeId = vm.patientOrder.employee.id;

        vm.canCancel = true;
        vm.cancelOrder = cancelOrder;

        vm.hidePanel = hidePanel;

        function hidePanel() {
            $scope.$emit('bluebookApp:cancelPatientOrder');
        }

        PatientOrder.getSignedBy({id: vm.patientOrder.id}, function (data) {
            vm.signedBy = data;

            if (!vm.patientOrder.signed && vm.signedBy && CoreService.getCurrentEmployee().id == vm.signedBy.id) {
                vm.canSign = true;
            }
        });

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

        function save() {
            vm.isSaving = true;
            vm.patientOrder.chartId = vm.chart.id;
            vm.patientOrder.signedById = vm.patientOrder.signedBy.id; //Physician
            PatientOrder.update(vm.patientOrder, onSaveSuccess, onSaveError);
        }

        function cancelOrder() {
            $uibModal.open({
                templateUrl: 'app/entities/patient-order/patient-order-cancel-dialog.html',
                controller: 'PatientOrderCancelController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    patientOrder: ['PatientOrder', '$stateParams', '$sessionStorage', function (PatientOrder, $stateParams, $sessionStorage) {
                        return PatientOrder.get({id: vm.patientOrder.id}).$promise;
                    }]
                }
            }).result.then(function () {
                // $state.go($sessionStorage.ourl, {"id": $stateParams.id}, {reload: true});
                toastr.success('The order was cancelled successfully.');
                $scope.$emit('bluebookApp:cancelPatientOrder');
            }, function () {
            });
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            // $state.go(vm.$sessionStorage.ourl? vm.$sessionStorage.ourl:'lab-requisitions', {"id": $stateParams.id}, {reload: true});
            $scope.$emit('bluebookApp:orderEdited');
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.signIn = signIn;

        function signIn() {
            if (vm.canSign) {
                vm.patientOrder.signed = true;
            }

            vm.isSaving = true;
            PatientOrder.update(vm.patientOrder, onSaveSuccess, onSaveError);
        }
    }
})();
