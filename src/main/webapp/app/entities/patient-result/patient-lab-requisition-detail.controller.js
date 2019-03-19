(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientLabRequisitionDetailController', PatientLabRequisitionDetailController);

    PatientLabRequisitionDetailController.$inject = ['$scope', '$uibModalInstance', 'entity', 'DateUtils', '$http', 'lodash'];

    function PatientLabRequisitionDetailController($scope, $uibModalInstance, entity, DateUtils, $http, _) {

        var vm = this;

        var optionsTemplate = {
            interpolate: /{{([\s\S]+?)}}/g
        };

        vm.entity = entity;
        var chart = entity.chart;
        vm.orderedLabTests = '';
        vm.patientDiagnoses = '';
        vm.medicalStatement = '';
        vm.loadMessages = function () {
            $http.get('app/entities/patient-result/json/lab_requisition.json')
                .success(function (data) {
                    vm.messages = data;
                    if (entity.patient) {
                        var compileObject = {
                            age: DateUtils.getAge(entity.patient.dateBirth),
                            gender: entity.patient.sex.toLowerCase(),
                            prescribedMedications: vm.prescribedMedications,
                        };
                        if (vm.messages['medical.statement']) {
                            var compileMsg = _.template(vm.messages['medical.statement'], optionsTemplate);
                            vm.medicalStatement = compileMsg(compileObject);
                        }
                    }

                });
        };
        vm.loadMessages();

        if (entity.icd10s) {
            vm.patientDiagnoses = _.chain(entity.icd10s)
                .map(function (row) {
                    return row.code + ' ' + row.description;
                })
                .uniq()
                .join(', ')
                .value()
        }
        if (entity.patientMedications) {
            var patientMedication = entity.patientMedications[0];
            if (patientMedication && patientMedication.employee) {
                vm.processedBy = patientMedication.employee.firstName + " " + patientMedication.employee.lastName + " at " +
                    moment(patientMedication.createdDate).format('MM/DD/YYYY hh:mm a');
            }

            vm.prescribedMedications = (patientMedication) ? _.chain(patientMedication.patientMedicationPress)
                .map(function (row) {
                    return row.medications.medication;
                })
                .uniq()
                .join(', ')
                .value() :
                ''
        }
        vm.clear = clear;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }


    }
})();
