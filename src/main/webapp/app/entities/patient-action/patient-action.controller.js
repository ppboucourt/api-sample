(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionController', PatientActionController);

    PatientActionController.$inject = ['PatientActionPre', 'CoreService', 'ROLES', '$uibModal', 'chart', '$compile', '$scope', 'PatientAction', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'moment'];

    function PatientActionController(PatientActionPre, CoreService, ROLES, $uibModal, chart, $compile, $scope, PatientAction, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, moment) {
        var vm = this;

        vm.chart = chart;
        vm.patientActions = [];
        vm.search = search;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientAction.getAllByChartVo({id: vm.chart.id}, function (result) {
                    vm.patientActions = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.orders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('patientActionPresAction').withTitle('Action'),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return data.startDate ? moment(data.startDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return data.endDate ? moment(data.endDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('AsNeeded').renderWith(function (data, type, full) {
                return data.asNeeded ? GUIUtils.getStatusTemplate('Yes', 'success') : GUIUtils.getStatusTemplate('No', 'danger');
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];


        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn btn-sm btn-primary" title="Details"  ng-click="vm.view({patientActionId:' + data.patientActionId + ', patientActionPresId:' + data.patientActionPresId + '})">' +
                '   <i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;';

            if (data.signed || !canSign(data)) {
                stButtons += '<a class="btn btn-sm btn-success"  title="Signed or Not Allowed"  data-ng-disabled="true"  >' +
                    '   <i class="glyphicon glyphicon-edit"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-success"  title="Sign"    ng-click="vm.sign({id:' + data.patientActionId + '})" >' +
                    '   <i class="glyphicon glyphicon-edit"></i></a>&nbsp;';
            }


            if ((data.patientActionStatus == 'CANCELED') || (data.patientActionStatus == 'FINISHED' || (!canDiscontinue(data)) )) {
                stButtons += '<a class="btn btn-sm btn-danger"  title="Disabled" data-ng-disabled="true"  >' +
                    '   <i class="glyphicon glyphicon-trash"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-danger"  title="Discount"   ng-click="vm.discount({id:' + data.patientActionId + '})" >' +
                    '   <i class="glyphicon glyphicon-trash"></i></a>&nbsp;';
            }


            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }


        function canSign(data) {//Only Doctors

            data.employee = CoreService.getCurrentEmployee();

            if (!data.signed && (data.signedById == CoreService.getCurrentEmployee().id)) {
                return true;
            }

            return false;
        }


        function canDiscontinue(data) {//Admin,Doctor, Nursing

            vm.patientAction = data;
            vm.patientAction.employee = CoreService.getCurrentEmployee();

            for (var i = 0; i < vm.patientAction.employee.user.authorities.length; i++) {
                if ((vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_DIRECTOR_NURSE ||
                        vm.patientAction.employee.user.authorities[i].name == ROLES.ROLE_REGISTER_NURSE
                    )) {
                    return true;
                }
            }
            return false;
        }


        /**************************************************************************/

        vm.addPatientActionsPre = function () {
            var test = {
                staringDate: null,
                id: null,
                patientActionTakes: [],
                icd10s: [],
                asNeeded: false
            };

            vm.popupTest(test);
        };

        vm.popupTest = function (patientActionPre) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-action-pre/patient-action-pre-new-dialog.html',
                controller: 'PatientActionPreNewDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientAction: vm.patientAction,
                    patientActionPre: patientActionPre,
                    problems: {problems: vm.problems},
                    chart: chart
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });
        };


        vm.view = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-action/patient-action-details.html',
                controller: 'PatientActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientActionPre: PatientActionPre.get({id: data.patientActionPresId}),
                    patientAction: PatientAction.get({id: data.patientActionId}),
                }
            }).result.then(function (result) {
                console.log(result);
            }, function () {
            });
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

        vm.discount = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-action/patient-action-cancel-dialog.html',
                controller: 'PatientActionCancelController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientAction: PatientAction.get({id: data.id}),
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


    }
})();
