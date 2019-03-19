(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationController', PatientMedicationController);

    PatientMedicationController.$inject = [ 'ROLES',  '$uibModal', 'chart', '$compile', '$scope', 'PatientMedication', 'PatientMedicationPres', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'moment', '$parse', 'CoreService'];

    function PatientMedicationController( ROLES,  $uibModal, chart, $compile, $scope, PatientMedication, PatientMedicationPres, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, moment, $parse, CoreService) {
        var vm = this;

        vm.chart = chart;
        vm.patientMedications = [];
        vm.search = search;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientMedication.chart({id: vm.chart.id}, function (result) {

                    vm.patientMedications = result;
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
            DTColumnBuilder.newColumn(null).withTitle('Medication').renderWith(function (data, type, full) {
                return data.medication ? data.medication : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('As Needed').renderWith(function (data, type, full) {
                if (data.asNeeded) {
                    return 'Yes';
                } else {
                    return 'No';
                }
            }),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return data.startDate ? moment(data.startDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return data.endDate ? moment(data.endDate).format("M/DD/Y") : '';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Physician By').renderWith(function (data, type, full) {
                return data.physicianFirstName + ' ' + data.physicianLastName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];


        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn btn-sm btn-primary" title="Details"  ng-click="vm.view({id:' + data.id + '})">' +
                '   <i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;';

            if (data.signed || !canSign(data)) {
                stButtons += '<a class="btn btn-sm btn-success"  title="Signed or Not Allowed"  data-ng-disabled="true"  >' +
                    '   <i class="glyphicon glyphicon-edit"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-success"  title="Sign"    ng-click="vm.sign({id:' + data.id + '})" >' +
                    '   <i class="glyphicon glyphicon-edit"></i></a>&nbsp;';
            }

            stButtons += '<a class="btn btn-sm btn-warning" title="Send eFax"  ng-click="vm.eFax({id:' + data.id + '})" >' +
                '   <i class="glyphicon glyphicon-phone"></i></a>&nbsp;';


            if ((data.status == 'CANCELED') || (data.status == 'FINISHED' || (!canDiscontinue(data)) )) {
                stButtons += '<a class="btn btn-sm btn-danger"  title="Disabled" data-ng-disabled="true"  >' +
                    '   <i class="glyphicon glyphicon-trash"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-danger"  title="Discount"   ng-click="vm.discount({id:' + data.id + '})" >' +
                    '   <i class="glyphicon glyphicon-trash"></i></a>&nbsp;';
            }


            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function canSign(data) {//Only Doctors

            vm.patientMedication = data;
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

        function canDiscontinue(data) {//Admin,Doctor, Nursing

            vm.patientMedication = data;
            vm.patientMedication.employee = CoreService.getCurrentEmployee();

            for (var i = 0; i < vm.patientMedication.employee.user.authorities.length; i++) {
                if ((vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_DIRECTOR_NURSE ||
                        vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_REGISTER_NURSE
                    )) {
                    return true;
                }
            }
            return false;
        }

        /*******************************************************************************************************************************************/

        vm.form = {};
        vm.save = save;

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
                icd10s: [],
                asNeeded: false,
                signatureDate: null
            };

            vm.popupTest(test);
        };


        //Add new Patient Medication
        vm.popupTest = function (patientMedicationPres) {

            vm.patientMedication = {};
            vm.patientMedication.chart = vm.chart;
            vm.patientMedication.employee = CoreService.getCurrentEmployee();

            for (var i = 0; i < vm.patientMedication.employee.user.authorities.length; i++) {
                if (vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                    vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                    vm.patientMedication.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
                ) {
                    vm.patientMedication.signedBy = vm.patientMedication.employee;
                    vm.patientMedication.signed = true;// duda aqui
                }
            }
            //
            vm.problems = vm.chart.icd10S;
            vm.patientMedication.medicationStatus = 'SCHEDULED';


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
            }).result.then(function (result) {

                console.log(result);

                PatientMedication.save(result, function (data) {
                    vm.dtInstance.reloadData();
                }, function (error) {
                    console.log(error);
                })

            }, function () {
            });
        };


        vm.view = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/patient-medication-dialog.html',
                controller: 'PatientMedicationDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    patientMedicationPres: PatientMedicationPres.get({id: data.id}),
                    patientMedication: PatientMedication.getPatientMedicationByPress({id: data.id}),
                }
            }).result.then(function (result) {
                console.log(result);
            }, function () {
            });

        }

        vm.sign = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/medication-sign-dialog.html',
                controller: 'MedicationSignController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'lg',
                resolve: {
                    patientMedication: PatientMedication.getPatientMedicationByPress({id: data.id}),
                }
            }).result.then(function (result) {

                if (result != 'cancel') {
                    console.log(result);

                    var orders = [];
                    orders.push(result.id);

                    if (orders.length > 0) {
                        vm.isSaving = true;
                        PatientMedication.signMedications({ids: orders}, function (data) {
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


        vm.eFax = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/patient-medication-sendfax-dialog.html',
                controller: 'PatientMedicationSendFaxDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedication: PatientMedication.getPatientMedicationByPress({id: data.id}),
                }
            }).result.then(function (result) {
                console.log(result);
            }, function () {
            });

        }


        vm.discount = function (data) {

            $uibModal.open({
                templateUrl: 'app/entities/patient-medication/patient-medication-cancel-dialog.html',
                controller: 'PatientMedicationCancelController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedication: PatientMedication.getPatientMedicationByPress({id: data.id}),
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


    }
})();
