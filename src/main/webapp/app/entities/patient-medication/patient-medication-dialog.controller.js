(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationDialogController', PatientMedicationDialogController);

    PatientMedicationDialogController.$inject = ['$timeout', '$uibModalInstance', 'patientMedicationPres', 'patientMedication', 'PatientMedicationPres', 'PatientMedication', '$uibModal', 'CoreService', 'ROLES'];

    function PatientMedicationDialogController ($timeout,  $uibModalInstance, patientMedicationPres, patientMedication, PatientMedicationPres, PatientMedication, $uibModal, CoreService, ROLES) {
        var vm = this;

        vm.patientMedicationPres = patientMedicationPres;
        vm.patientMedication = patientMedication;
        vm.clear = clear;
        vm.canSign = canSign;
        vm.CoreService = CoreService;




        function clear () {
            $uibModalInstance.dismiss('cancel');
        }


        $timeout(function () {
            PatientMedicationPres.medicationDetails({id:  vm.patientMedicationPres.id}, function (data) {
                vm.medicationDetails = data;
                console.log(vm.medicationDetails)
            });

        }, 500);


        function onSaveSuccess (result) {

        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function canSign() {//Only Doctors

            vm.patientMedication.employee = CoreService.getCurrentEmployee();

            for (var i = 0; i < vm.patientMedication.employee.user.authorities.length; i++) {
                if ((vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
                    ) && (vm.patientMedication.physicianId == CoreService.getCurrentEmployee().id)) {
                    return true;
                }
            }
            return false;
        }


        vm.sign = function(data){

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/medication-sign-dialog.html',
                controller: 'MedicationSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedication: PatientMedication.getPatientMedicationByPress({id:vm.patientMedication.id}),
                }
            }).result.then(function(result) {

                if(result !='cancel'){
                    console.log(result);

                    var orders = [];
                    orders.push(result.id);

                    if (orders.length > 0) {
                        vm.isSaving = true;
                        PatientMedication.signMedications({ids: orders}, function (data) {
                            vm.isSaving = false;
                        }, function (error) {
                            vm.isSaving = false;
                            console.log(error);
                        })
                    }
                }

            }, function() {
            });

        }



        vm.eFax = function(){

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/patient-medication-sendfax-dialog.html',
                controller: 'PatientMedicationSendFaxDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedication: PatientMedication.getPatientMedicationByPress({id:vm.patientMedication.id}),
                }
            }).result.then(function(result) {
                console.log(result);
            }, function() {
            });

        }


    }
})();
