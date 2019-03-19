(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationNewController', PatientMedicationNewController);

    PatientMedicationNewController.$inject = ['ROLES', '$scope', '$compile', '$state', 'ChartToIcd10', 'patientMedication', 'chart', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', 'GUIUtils',
        'CoreService', 'PatientMedication', 'Via', 'Employee', '$uibModal'];

    function PatientMedicationNewController (ROLES, $scope, $compile, $state, ChartToIcd10, patientMedication, chart, $q, DTColumnBuilder,
                                             DTOptionsBuilder, GUIUtils,
                                             CoreService, PatientMedication, Via, Employee, $uibModal) {
        var vm = this;

        vm.chart = chart;
        vm.patientMedication = patientMedication;
        vm.patientMedication.chart = vm.chart;
        vm.patientMedication.employee = CoreService.getCurrentEmployee();

        for (var i = 0; i < vm.patientMedication.employee.user.authorities.length; i++){
            if (vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
            ) {
                vm.patientMedication.signedBy = vm.patientOrder.employee;
                vm.patientMedication.signed = true;
            }
        }

        vm.problems = vm.chart.icd10S;
        vm.patientMedication.medicationStatus = 'SCHEDULED';

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

        vm.orderfrequencies = [];

        function save() {
            vm.isSaving = true;
            PatientMedication.save(vm.patientMedication, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess() {
            vm.isSaving = false;
            $state.go("patient-orders");
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.addPatientOrderTest = function () {
            var test = {
                staringDate: null,
                id: null,
                patientMedicationTakes: [],
                icd10s : [],
                asNeeded: false
            };

            vm.popupTest(test);
        };

        vm.editMedicationPres = function (index) {
            vm.popupTest(vm.patientMedication.patientMedicationPress[index]);
        };

        vm.deleteMedicationPres = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-delete-dialog.html',
                controller: 'PatientMedicationPresDeleteController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: {id: index}
                }
            }).result.then(function() {
                vm.patientMedication.patientMedicationPress.splice(index, 1);

                vm.dtInstance.reloadData();
            }, function() {
            });
        };

        vm.showICD10 = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/icd-10/diagnosis-dialog.html',
                controller: 'DiagnosisDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: {
                        icd10s: vm.patientMedication.patientMedicationPress[index].icd10S
                    }
                }
            });
        };

        vm.popupTest = function(patientMedicationPres) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-new-dialog.html',
                controller: 'PatientMedicationPresNewDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    patientMedication: vm.patientMedication,
                    patientMedicationPres: patientMedicationPres,
                    problems: {problems: vm.problems}
                }
            }).result.then(function(result) {
                if (result.is_new) {
                    vm.patientMedication.patientMedicationPress.push(result.patientMedicationPres);
                }
                vm.dtInstance.reloadData();
            }, function() {
            });
        };

        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            defer.resolve(vm.patientMedication.patientMedicationPress);

            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Medication').renderWith(function (data, type, full) {
                return data.medications.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Diagnosis (ICD10)').renderWith(function (data, type, full, meta) {
                return data.icd10s != null && data.icd10s.length > 0 ? data.icd10s[0].code + showICD10Html(meta.row): "";
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Takes').renderWith(function (data, type, full, meta) {
            //     return data.patientMedicationTakes == null ? 0 : data.patientMedicationTakes.length;
            // }),
            DTColumnBuilder.newColumn(null).withTitle('As Needed').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data.asNeeded);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return data.staringDate ? moment(data.staringDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return data.endDate ?  moment(data.endDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ng-click="vm.editMedicationPres(' + meta.row + ')">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';
            stButtons += '<a class="btn-sm btn-danger" ng-click="vm.deleteMedicationPres(' + meta.row + ')">' +
                '   <i class="fa fa-trash"></i></a>';

            return stButtons;
        };

        function showICD10Html(row) {
            return '&nbsp;&nbsp;<a class="btn-sm btn-info" ng-click="vm.showICD10(' + row + ')">' +
                '   <i class="fa fa-paper-plane"></i></a>';
        };
    }
})();
