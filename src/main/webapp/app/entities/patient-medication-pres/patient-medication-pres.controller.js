(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationPresController', PatientMedicationPresController);

    PatientMedicationPresController.$inject = ['canCancel', 'CoreService', '$compile', '$scope', 'GUIUtils', '$q', '$uibModal', 'PatientMedicationPres', 'patientMedication', 'DTOptionsBuilder', 'DTColumnBuilder'];

    function PatientMedicationPresController (canCancel, CoreService, $compile, $scope, GUIUtils, $q, $uibModal, PatientMedicationPres, patientMedication, DTOptionsBuilder, DTColumnBuilder) {
        var vm = this;

        vm.patientMedication = patientMedication;
        vm.patientMedicationPress = [];
        vm.dtInstance = {};

        vm.canCancel = false;

        if (vm.patientMedication.medicationStatus == 'SCHEDULED' && canCancel) {
            vm.canCancel = CoreService.getCurrentEmployee().id == vm.patientMedication.signedBy.id;
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            PatientMedicationPres.medication({id: vm.patientMedication.id}, function(result) {
                vm.patientMedicationPress = result;
                defer.resolve(result);
            });

            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Test').renderWith(function (data, type, full) {
                return data.medications.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Diagnosis (ICD10)').renderWith(function (data, type, full, meta) {
                return data.icd10s != null && data.icd10s.length > 0 ? data.icd10s[0].code + showICD10Html(meta.row): "";
            }),
            DTColumnBuilder.newColumn(null).withTitle('As Needed').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data.asNeeded);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return data.staringDate ? moment(data.staringDate).format("M/DD/Y HH:mm") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return data.endDate ?  moment(data.endDate).format("M/DD/Y HH:mm") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ng-click="vm.edit(' + meta.row + ')">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';

            return stButtons;
        }

        function colorHtml(value){
            if (value) {
                return GUIUtils.getStatusTemplate('Yes', 'success');
            } else {
                return GUIUtils.getStatusTemplate('No', 'danger');
            }
        }

        function showICD10Html(row) {
            return '&nbsp;&nbsp;<a class="btn-sm btn-info" ng-click="vm.showICD10(' + row + ')">' +
                '   <i class="fa fa-paper-plane"></i></a>';
        }

        vm.showICD10 = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/icd-10/diagnosis-dialog.html',
                controller: 'DiagnosisDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: {
                        icd10s: vm.patientMedicationPress[index].icd10s
                    }
                }
            });
        }

        vm.edit = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-dialog.html',
                controller: 'PatientMedicationPresDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    patientMedication: vm.patientMedication,
                    patientMedicationPres: vm.patientMedicationPress[index],
                    canCancel : vm.canCancel
                }
            }).result.then(function(result) {

            }, function() {
            });
        }
    }
})();
