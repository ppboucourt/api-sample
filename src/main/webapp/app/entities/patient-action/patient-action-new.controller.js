(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionNewController', PatientActionNewController);

    PatientActionNewController.$inject = ['ROLES', '$scope', '$compile', '$state', 'ChartToIcd10', 'patientAction', 'chart', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', 'GUIUtils',
        'CoreService', 'PatientAction', 'Via', 'Employee', '$uibModal'];

    function PatientActionNewController (ROLES, $scope, $compile, $state, ChartToIcd10, patientAction, chart, $q, DTColumnBuilder,
                                             DTOptionsBuilder, GUIUtils,
                                             CoreService, PatientAction, Via, Employee, $uibModal) {
        var vm = this;

        vm.chart = chart;
        vm.patientAction = patientAction;
        vm.patientAction.chart = vm.chart;
        vm.patientAction.employee = CoreService.getCurrentEmployee();

        for (var i = 0; i < vm.patientAction.employee.user.authorities.length; i++){
            if (vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
            ) {
                vm.patientAction.signedBy = vm.patientOrder.employee;
                vm.patientAction.signed = true;
            }
        }

        vm.problems = vm.chart.icd10S;
        vm.patientAction.actionStatus = 'SCHEDULED';

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

        vm.orderfrequencies = [];

        function save() {
            vm.isSaving = true;
            PatientAction.save(vm.patientAction, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess() {
            vm.isSaving = false;
            $state.go("patient-orders");
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.addPatientActionsPre = function () {
            var test = {
                staringDate: null,
                id: null,
                patientActionTakes: [],
                icd10s : [],
                asNeeded: false
            };

            vm.popupTest(test);
        };

        vm.editActionPres = function (index) {
            vm.popupTest(vm.patientAction.patientActionPres[index]);
        };

        vm.deleteActionPres = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-action-pre/patient-action-pre-delete-dialog.html',
                controller: 'PatientActionPreDeleteController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: {id: index}
                }
            }).result.then(function() {
                vm.patientAction.patientActionPres.splice(index, 1);

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
                        icd10s: vm.patientAction.patientActionPres[index].icd10s
                    }
                }
            });
        };

        vm.popupTest = function(patientActionPre) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-action-pre/patient-action-pre-new-dialog.html',
                controller: 'PatientActionPreNewDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientAction: vm.patientAction,
                    patientActionPre: patientActionPre,
                    problems: {problems: vm.problems}
                }
            }).result.then(function(result) {
                if (result.is_new) {
                    vm.patientAction.patientActionPres.push(result.patientActionPre);
                }
                vm.dtInstance.reloadData();
            }, function() {
            });
        };

        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            defer.resolve(vm.patientAction.patientActionPres);

            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Action').renderWith(function (data, type, full) {
                return data.action;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Diagnosis (ICD10)').renderWith(function (data, type, full, meta) {
                return data.icd10s != null && data.icd10s.length > 0 ? data.icd10s[0].code + showICD10Html(meta.row): "";
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Takes').renderWith(function (data, type, full, meta) {
            //     return data.patientActionTakes == null ? 0 : data.patientActionTakes.length;
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

            stButtons += '<a class="btn-sm btn-warning" ng-click="vm.editActionPres(' + meta.row + ')">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';
            stButtons += '<a class="btn-sm btn-danger" ng-click="vm.deleteActionPres(' + meta.row + ')">' +
                '   <i class="fa fa-trash"></i></a>';

            return stButtons;
        };

        function showICD10Html(row) {
            return '&nbsp;&nbsp;<a class="btn-sm btn-info" ng-click="vm.showICD10(' + row + ')">' +
                '   <i class="fa fa-paper-plane"></i></a>';
        };
    }
})();
