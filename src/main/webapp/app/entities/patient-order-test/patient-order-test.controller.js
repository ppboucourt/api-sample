(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderTestController', PatientOrderTestController);

    PatientOrderTestController.$inject = ['canCancel', 'CoreService', '$rootScope', '$compile', '$scope', 'GUIUtils', '$q',
        '$uibModal', 'PatientOrderTest', 'DTOptionsBuilder', 'DTColumnBuilder', 'PatientOrder'];

    function PatientOrderTestController(canCancel, CoreService, $rootScope, $compile, $scope, GUIUtils, $q, $uibModal,
                                        PatientOrderTest, DTOptionsBuilder, DTColumnBuilder, PatientOrder) {
        var vm = this;

        // vm.patientOrder = patientOrder;
        $scope.$on('$destroy', function () {
            delete $rootScope.patientOrder;
        });
        vm.patientOrder = _.clone($rootScope.patientOrder, true);
        vm.patientOrderTests = [];
        vm.dtInstance = {};

        vm.patientOrderTestDetailUpdate = patientOrderTestDetailUpdate;
        vm.patientOrderTestDetailView = patientOrderTestDetailView;

        vm.canCancel = false;

        if (!vm.patientOrder.signed) {
            PatientOrder.getSignedBy({id: vm.patientOrder.id}, function (data) {
                vm.signedBy = data;
                vm.canSign = CoreService.getCurrentEmployee().id == data.id;
            });
        } else {
            vm.canSign = false;
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            PatientOrderTest.order({id: vm.patientOrder.id}, function (result) {
                vm.patientOrderTests = result;
                defer.resolve(result);
            });

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
                return moment(data.staringDate).format("MM/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return moment(data.endDate).format("MM/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            if (canCancel) {
                // stButtons += '<a class="btn-sm btn-warning" ui-sref="patient-orders-update.patient-order-test-detail({tid:' + data.id + '})">' +
                //     '   <i class="fa fa-edit"></i></a>&nbsp;';
                stButtons += '<a class="btn-sm btn-warning" ng-click="vm.patientOrderTestDetailUpdate(' + data.id + ')">' +
                    '   <i class="fa fa-edit"></i></a>&nbsp;';
            } else {
                // stButtons += '<a class="btn-sm btn-warning" ui-sref="patient-orders-view.patient-order-test-detail({tid:' + data.id + '})">' +
                //     '   <i class="fa fa-edit"></i></a>&nbsp;';
                stButtons += '<a class="btn-sm btn-warning" ng-click="vm.patientOrderTestDetailView(' + data.id + ')">' +
                    '   <i class="fa fa-edit"></i></a>&nbsp;';
            }

            return stButtons;
        }

        function patientOrderTestDetailUpdate(id) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-order-test/patient-order-test-detail-dialog.html',
                controller: 'PatientOrderTestDetailDialogController',
                controllerAs: 'vm',
                size: 'lg',
                resolve: {
                    patientOrderTest: ['PatientOrderTest', function (PatientOrderTest) {
                        return PatientOrderTest.get({id: id}).$promise;
                    }]
                }
            }).result.then(function () {
                // $state.go('patient-orders-update', null, {reload: 'patient-orders-update'});
                vm.dtInstance.reloadData();
            }, function (error) {
                // Error
            });
        }


        function patientOrderTestDetailView(id) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-order-test/patient-order-test-detail-dialog.html',
                controller: 'PatientOrderTestDetailDialogController',
                controllerAs: 'vm',
                size: 'lg',
                resolve: {
                    patientOrderTest: ['PatientOrder', function (PatientOrder) {
                        return PatientOrder.getPatientOrderTestItems({id: id}).$promise;
                    }]
                }
            }).result.then(function () {
                vm.dtInstance.reloadData();
            }, function (error) {
                //Error
            });
        }

        function colorHtml(value) {
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
                    entity: vm.patientOrderTests[index]
                }
            });
        }

        vm.edit = function (index) {

            PatientOrder.getPatientOrderTestItems({id: vm.patientOrderTests[index].id}, function (data) {

                if (data) {
                    vm.kk = {value: data};

                    $uibModal.open({
                        templateUrl: 'app/entities/patient-order-test/patient-order-test-detail-dialog.html',
                        controller: 'PatientOrderTestDetailDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            patientOrder: vm.patientOrder,
                            patientOrderTest: data.$promise,
                            canCancel: vm.canCancel
                        }
                    }).result.then(function (result) {

                    }, function () {
                    });


                }


            })


        }
    }
})();
