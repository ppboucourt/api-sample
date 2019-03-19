(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionDialogController', PatientActionDialogController);

    PatientActionDialogController.$inject = ['$uibModalInstance', 'patientActionPre', 'patientAction', 'PatientAction', '$uibModal', 'CoreService', 'ROLES'];

    function PatientActionDialogController($uibModalInstance, patientActionPre, patientAction, PatientAction, $uibModal, CoreService, ROLES) {
        var vm = this;

        vm.patientActionPre = patientActionPre;


        vm.patientAction = patientAction;
        vm.clear = clear;
        vm.canSign = canSign;
        vm.CoreService = CoreService;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function onSaveSuccess(result) {

        }

        function onSaveError() {
            vm.isSaving = false;
        }

        function canSign() {//Only Doctors

            vm.patientAction.employee = CoreService.getCurrentEmployee();

            for (var i = 0; i < vm.patientAction.employee.user.authorities.length; i++) {
                if ((vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
                    ) && (vm.patientAction.physicianId == CoreService.getCurrentEmployee().id)) {
                    return true;
                }
            }
            return false;
        }


        vm.sign = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-action/patient-sign-dialog.html',
                controller: 'PatientActionSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientAction: PatientAction.get({id: data.id}),
                }
            }).result.then(function (result) {

                if (result != 'cancel') {
                    console.log(result);

                    var actions = [];
                    actions.push(result.id);

                    if (actions.length > 0) {
                        vm.isSaving = true;
                        PatientAction.signActions({ids: actions}, function (data) {
                            vm.dtInstance.reloadData();
                            vm.isSaving = false;
                        }, function (error) {
                            vm.isSaving = false;
                            console.log(error);
                        })
                    }
                }

            }, function () {
            });

        }
    }
})();
