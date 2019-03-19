(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationsScheduleController', MedicationsScheduleController);

    MedicationsScheduleController.$inject = ['$sessionStorage', 'PatientMedicationTake', '$rootScope', '$uibModal', '$compile',
        '$q', '$scope', 'GUIUtils', 'PatientMedication', 'chart', 'DTOptionsBuilder', 'DTColumnBuilder', 'DateUtils'];

    function MedicationsScheduleController($sessionStorage, PatientMedicationTake, $rootScope, $uibModal, $compile, $q, $scope,
                                           GUIUtils, PatientMedication, chart, DTOptionsBuilder, DTColumnBuilder, DateUtils) {
        var vm = this;

        vm.medications = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.dtInstanceAsNeeded = {};
        vm.barCode = {};

        vm.selected = {};
        vm.selectAll = false;
        vm.toggleAll = toggleAll;
        vm.toggleOne = toggleOne;

        var titleHtml = '<input type="checkbox" ng-model="vm.selectAll" ng-change="vm.toggleAll()">';

        vm.rmConfig1 = {
            mondayStart: false,
            initState: "month",
            maxState: "decade",
            minState: "month",
            decadeSize: 12,
            monthSize: 42,
            min: new Date("2016-01-01"),
            max: null,
            format: "yyyy-MM-dd"
        };

        vm.oDate1 = new Date();
        vm.select = function () {
            vm.dtInstance.reloadData();
        };

        //Event when clicked in Mars Tab
        $scope.$on('$destroy', $rootScope.$on('bluebookApp:clickedMarsTab', function (event, data) {
            if (data && vm.dtInstance) {
                search();
            }
        }));

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            vm.medications = [];
            PatientMedication.today_medications({
                id: chart.id,
                date: DateUtils.convertLocalDateToServer(vm.oDate1)
            }, function (result) {
                vm.medications = result.sort(function (a, b) {
                    // Turn your strings into dates, and then subtract them
                    // to get a value that is either negative, positive, or zero.
                    return new Date(a.scheduleDate).getTime() - new Date(b.scheduleDate).getTime();
                });


                defer.resolve(result);

                var c = 0;

                for (var i = 0; i < result.length; i++) {
                    if (!result[i].collected) {
                        c += 1;
                    }
                }

                $rootScope.$broadcast('bluebookApp:medications', {count: c});

                vm.selected = {};
                vm.selectAll = false;
            });
            return defer.promise;
        }).withBootstrap().withDOM('tip')
            .withOption('createdRow', function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            })
            .withOption('headerCallback', function (header) {
                if (!vm.headerCompiled) {
                    vm.headerCompiled = true;
                    $compile(angular.element(header).contents())($scope);
                }
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Medication').renderWith(function (data, type, full) {
                return data.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Dose').withOption('width', '100px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.dose ? data.dose : '-';
                }),
            DTColumnBuilder.newColumn(null).withTitle('Route').withOption('width', '110px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.route ? data.route : '-';
                }),

            DTColumnBuilder.newColumn(null).withTitle('Time').withOption('width', '50px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.scheduleDate ? '<span class="label label-success"><strong><i class="fa fa-clock-o"></i> ' +
                        moment(data.scheduleDate).format("hh:mm a") + '</strong></span>' : '-';
                    // return data.scheduleDate ? '<strong>' + moment(data.scheduleDate).format("hh:mm a") + '</strong>' : '-';
                }),

            DTColumnBuilder.newColumn(null).withTitle('Status').withOption('width', '70px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.medicationTakeStatus ? data.medicationTakeStatus : 'SCHEDULED';
                }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '80px').notSortable()
                .renderWith(medicationHtml)
        ];

        vm.dtOptionsAsNeeded = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            PatientMedication.as_needed_medication({id: chart.id}, function (result) {
                vm.as_needed_medications = result;
                defer.resolve(result);
            });
            return defer.promise;
        }).withBootstrap().withDOM('tip')
            .withOption('createdRow', function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            });

        vm.dtColumnsAsNeeded = [
            DTColumnBuilder.newColumn(null).withTitle('As Needed').renderWith(function (data, type, full) {
                return data.medications.medication;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Take').withOption('width', '100px').notSortable()
                .renderWith(asNeededHtml)
        ];

        vm.printLabels = function () {
            var medications = [];
            for (var id in vm.selected) {
                if (vm.selected[id]) {
                    medications.push(id);
                }
            }

            if (medications.length > 0) {
                PatientMedication.collect({
                    ids: medications,
                    date: DateUtils.convertLocalDateToServer(vm.oDate1)
                }, function () {
                    vm.dtInstance.reloadData();
                }, function () {
                })
            }
        }

        function medicationHtml(data, type, full, meta) {
            var stButtons = '';

            if (data.medicationTakeStatus == 'ADMINISTERED' || data.medicationTakeStatus == 'OBSERVED' || data.medicationTakeStatus == 'REJECTED') {

                stButtons += '<a class="btn btn-sm btn-primary" title="Details" ng-click="vm.details({id:' + data.id + '})" >' +
                    '   <i class="glyphicon glyphicon-info-sign"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-success"  title="Administer" ng-click="vm.administer({id:' + data.id + '})"  >' +
                    '   <i class="glyphicon glyphicon-transfer"></i></a>&nbsp;';

                stButtons += '<a class="btn btn-sm btn-primary"     title="Observer" ng-click="vm.observer({id:' + data.id + '})" >' +
                    '   <i class="glyphicon glyphicon-eye-open"></i></a>&nbsp;';

                stButtons += '<a class="btn btn-sm btn-danger"     title="Rejecter" ng-click="vm.rejecter({id:' + data.id + '})" >' +
                    '   <i class="glyphicon glyphicon-ban-circle"></i></a>&nbsp;';

            }

            return stButtons;
        }

        function asNeededHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn-xs btn-primary" ng-click="vm.addAsNeeded(' + full.id + ')">' +
                '   <i class="fa fa-plus"></i></a>&nbsp;';

            return stButtons;
        }

        function toggleAll() {
            for (var id in vm.selected) {
                if (vm.selected.hasOwnProperty(id)) {
                    vm.selected[id] = vm.selectAll;
                }
            }
        }

        function toggleOne() {
            for (var id in vm.selected) {
                if (vm.selected.hasOwnProperty(id)) {
                    if (!vm.selected[id]) {
                        vm.selectAll = false;
                        return;
                    }
                }
            }
            vm.selectAll = true;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function dateSortDesc(o1, o2) {
            // This is a comparison function that will result in dates being sorted in
            // DESCENDING order.
            if (o1.scheduleDate > o2.scheduleDate) return -1;
            if (o1.scheduleDate < o2.scheduleDate) return 1;
            return 0;
        };

        vm.addAsNeeded = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-as-needed-dialog.html',
                controller: 'PatientAsNeededDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
            }).result.then(function (result) {

                if (result) {
                    PatientMedication.add_needed_medication({patientId: id, zonedDateTime: result.date}, function () {
                        vm.dtInstance.reloadData();
                    });
                }

            }, function () {
            });
        };


        vm.administer = function (data) {

            $sessionStorage.takeTitle = "Administer";
            $sessionStorage.takeStatus = "ADMINISTERED";

            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-medication-take-doaction-dialog.html',
                controller: 'PatientMedicationTakeDoActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedicationTake: PatientMedicationTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


        vm.observer = function (data) {

            $sessionStorage.takeTitle = "Observer";
            $sessionStorage.takeStatus = "OBSERVED";

            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-medication-take-doaction-dialog.html',
                controller: 'PatientMedicationTakeDoActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedicationTake: PatientMedicationTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }

        vm.rejecter = function (data) {

            $sessionStorage.takeTitle = "Rejecter";
            $sessionStorage.takeStatus = "REJECTED";

            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-medication-take-doaction-dialog.html',
                controller: 'PatientMedicationTakeDoActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientMedicationTake: PatientMedicationTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


        vm.details = function (data) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-medication-take-details-dialog.html',
                controller: 'PatientMedicationTakeDetailsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: data.notes ? 'md' : 'sm',
                resolve: {
                    patientMedicationTake: PatientMedicationTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }

    }
})();
