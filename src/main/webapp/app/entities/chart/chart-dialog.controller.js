(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartDialogController', ChartDialogController);

    ChartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Chart', 'Patient', 'CareManager', 'Bed', 'ChartToGroupSession', 'ChartToIcd10', 'ConcurrentReviews', 'ChartToMedications', 'Contacts', 'Insurance', 'Orders', 'Vitals', 'Weight', 'LabRequisition', 'Employee', 'TypePatientPrograms', 'TypeLevelCare', 'Facility', 'Glucose', 'TypeAdmissionStatus', 'ChartToAction', 'GroupSessionDetailsChart', 'Shifts'];

    function ChartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Chart, Patient, CareManager, Bed, ChartToGroupSession, ChartToIcd10, ConcurrentReviews, ChartToMedications, Contacts, Insurance, Orders, Vitals, Weight, LabRequisition, Employee, TypePatientPrograms, TypeLevelCare, Facility, Glucose, TypeAdmissionStatus, ChartToAction, GroupSessionDetailsChart, Shifts) {
        var vm = this;

        vm.chart = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.patients = Patient.query();
        vm.caremanagers = CareManager.query();
        vm.beds = Bed.query();
        vm.charttogroupsessions = ChartToGroupSession.query();
        vm.charttoicd10s = ChartToIcd10.query();
        vm.concurrentreviews = ConcurrentReviews.query();
        vm.charttomedications = ChartToMedications.query();
        vm.contacts = Contacts.query();
        vm.insurances = Insurance.query();
        vm.orders = Orders.query();
        vm.vitals = Vitals.query();
        vm.weights = Weight.query();
        vm.labrequisitions = LabRequisition.query();
        vm.employees = Employee.query();
        vm.typepatientprograms = TypePatientPrograms.query();
        vm.typelevelcares = TypeLevelCare.query();
        vm.facilities = Facility.query();
        vm.glucoses = Glucose.query();
        vm.typeadmissionstatuses = TypeAdmissionStatus.query();
        vm.charttoactions = ChartToAction.query();
        vm.groupsessiondetailscharts = GroupSessionDetailsChart.query();
        vm.shifts = Shifts.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chart.id !== null) {
                Chart.update(vm.chart, onSaveSuccess, onSaveError);
            } else {
                Chart.save(vm.chart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.admission_date = false;
        vm.datePickerOpenStatus.discharge_time = false;
        vm.datePickerOpenStatus.date_first_contact = false;
        vm.datePickerOpenStatus.sobriety_date = false;

        vm.setPicture = function ($file, chart) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        chart.picture = base64Data;
                        chart.pictureContentType = $file.type;
                    });
                });
            }
        };

        vm.setPatient_license = function ($file, chart) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        chart.patient_license = base64Data;
                        chart.patient_licenseContentType = $file.type;
                    });
                });
            }
        };
        vm.datePickerOpenStatus.dischargeDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
