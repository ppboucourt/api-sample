(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderNewController', PatientOrderNewController);

    PatientOrderNewController.$inject = ['ROLES', '$scope', '$compile', '$rootScope', 'patientOrder', 'chart', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', 'GUIUtils', 'CoreService', 'PatientOrder', 'Via', 'Employee', '$uibModal', 'toastr'];

    function PatientOrderNewController(ROLES, $scope, $compile, $rootScope, patientOrder, chart, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils,
                                       CoreService, PatientOrder, Via, Employee, $uibModal, toastr) {

        var vm = this;
        vm.newOrder = false;

        vm.chart = chart;
        vm.patientOrder = patientOrder;
        vm.patientOrder.chart = vm.chart;
        vm.patientOrder.employee = CoreService.getCurrentEmployee();
        vm.patientOrder.employeeId = vm.patientOrder.employee.id;
        vm.dtInstance = {};

        for (var i = 0; i < vm.patientOrder.employee.user.authorities.length; i++) {
            if (vm.patientOrder.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                vm.patientOrder.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                vm.patientOrder.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
            ) {
                vm.patientOrder.signedBy = vm.patientOrder.employee;
                vm.patientOrder.signed = true;
            }
        }

        vm.problems = vm.chart.icd10S;
        vm.patientOrder.ordStatus = 'SCHEDULED';

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        vm.employees = Employee.employeesCorpPhysician();

        vm.orderfrequencies = [];

        function save() {
            vm.isSaving = true;
            vm.patientOrder.chartId = vm.chart.id;
            vm.patientOrder.signedById = vm.patientOrder.signedBy.id; //Physician
            PatientOrder.save(vm.patientOrder, onSaveSuccess, onSaveError);
        }

        vm.cancelOrder = function () {
            $scope.$emit('bluebookApp:orderCancelled');
            resetNewOrder();
        }

        function onSaveSuccess() {
            vm.isSaving = false;
            toastr.success('The order was added successfully.');
            $scope.$emit('bluebookApp:orderAdded', vm.patientOrder);
            resetNewOrder();
            // $state.go("patient-orders");
        }

        function resetNewOrder() {
            vm.patientOrder = angular.extend({}, vm.patientOrder, {
                signatureDate: null,
                signed: null,
                endDate: null,
                id: null,
                patientOrderTests: []
            });
        }

        function onSaveError() {
            toastr.error('There was a problem to add this order.');
            vm.isSaving = false;
            resetNewOrder();
        }

        vm.addPatientOrderTest = function () {
            var test = {
                staringDate: null,
                id: null,
                orderFrequency: vm.orderfrequencies[0],
                patientOrderItems: [],
                icd10s: []
            };
            vm.popupTest(test);
        };

        vm.editPatientOrderTest = function (index) {
            vm.popupTest(vm.patientOrder.patientOrderTests[index]);
        };

        vm.deletePatientOrderTest = function (index) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-order-test/patient-order-test-delete-dialog.html',
                controller: 'PatientOrderTestDeleteController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    entity: {id: index}
                }
            }).result.then(function () {
                vm.patientOrder.patientOrderTests.splice(index, 1);

                vm.dtInstance.reloadData();
            }, function () {
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
                    entity: vm.patientOrder.patientOrderTests[index]
                }
            });
        };

        vm.popupTest = function (patientOrderTest) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-order-test/patient-order-test-new-dialog.html',
                controller: 'PatientOrderTestNewDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    // entity: entity,
                    patientOrder: vm.patientOrder,
                    patientOrderTest: patientOrderTest,
                    problems: {problems: vm.problems}
                }
            }).result.then(function (result) {
                if (result.is_new) {
                    vm.patientOrder.patientOrderTests.push(result.patientOrderTest);
                }
                vm.dtInstance.reloadData();
            }, function () {
            });
        };

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if ($rootScope.newOrder) {
                resetNewOrder();
                delete $rootScope.newOrder;
            }
            defer.resolve(vm.patientOrder.patientOrderTests);

            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Test').renderWith(function (data, type, full) {
                return data.labCompendium.code;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Description').renderWith(function (data, type, full) {
                return data.labCompendium.description;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Diagnosis (ICD10)').renderWith(function (data, type, full, meta) {
                return data.icd10s != null && data.icd10s.length > 0 ? data.icd10s[0].code + showICD10Html(meta.row) : "";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return moment(data.staringDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return moment(data.endDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ng-click="vm.editPatientOrderTest(' + meta.row + ')">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';
            stButtons += '<a class="btn-sm btn-danger" ng-click="vm.deletePatientOrderTest(' + meta.row + ')">' +
                '   <i class="fa fa-trash"></i></a>';

            return stButtons;
        };

        function showICD10Html(row) {
            return '&nbsp;&nbsp;<a class="btn-sm btn-info" ng-click="vm.showICD10(' + row + ')">' +
                '   <i class="fa fa-paper-plane"></i></a>';
        };

    }
})();
