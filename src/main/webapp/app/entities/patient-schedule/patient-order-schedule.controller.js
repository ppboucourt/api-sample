(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderScheduleController', PatientOrderScheduleController);

    PatientOrderScheduleController.$inject = ['$sessionStorage', 'hasPrinter', '$rootScope', 'CoreService', '$compile',
        '$q', '$scope', 'GUIUtils', 'PatientOrder', 'DTOptionsBuilder', 'DTColumnBuilder', 'DateUtils', 'DYMO', '$uibModal',
        '$filter', 'toastr'];

    function PatientOrderScheduleController($sessionStorage, hasPrinter, $rootScope, CoreService, $compile, $q, $scope,
                                            GUIUtils, PatientOrder, DTOptionsBuilder, DTColumnBuilder, DateUtils, DYMO,
                                            $uibModal, $filter, toastr) {
        var vm = this;

        vm.hasPrinter = hasPrinter;
        vm.facility = CoreService.getCurrentFacility();
        vm.orders = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.barCode = {};

        vm.selected = {};
        vm.selectAll = false;
        vm.toggleAll = toggleAll;
        vm.toggleOne = toggleOne;
        vm.editOrder = false;

        $sessionStorage.ourl = "lab-requisitions";

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
        }


        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {

                PatientOrder.dashboardSchedule({
                    id: vm.facility.id,
                    date: DateUtils.convertLocalDateToServer(vm.oDate1)
                }, function (result) {
                    vm.orders = result;
                    defer.resolve(result);
                    if (vm.hasPrinter) {
                        var orders = getSelectedIds();

                        if (orders.length > 0) {
                            PatientOrder.getBarcodes({
                                ids: orders,
                                date: DateUtils.convertLocalDateToServer(vm.oDate1)
                            }, function (result) {
                                var barcodes = [];

                                for (var l = 0; l < result.length; l++) {
                                    if (barcodes.indexOf(result[l].barcode) == -1) {
                                        barcodes.push(result[l].barcode);
                                    }

                                    DYMO.printPatientOrderLabel({
                                        barcode: result[l].barcode,
                                        name: result[l].patientName,
                                        dob: result[l].dob,
                                        collectedDate: result[l].date,
                                        tube: result[l].tube,
                                        account: result[l].account
                                    });
                                }
                            });
                        }
                    }

                    vm.selected = {};
                    vm.selectAll = false;

                    $rootScope.$broadcast('bluebookApp:collectedPatientOrders');
                });
            } else {
                defer.resolve($filter('filter')(vm.orders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withBootstrap().withDOM('tip')
            .withOption('createdRow', function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            })
            .withOption('aaSorting', [[1, 'asc']])
            .withOption('headerCallback', function (header) {
                if (!vm.headerCompiled) {
                    vm.headerCompiled = true;
                    $compile(angular.element(header).contents())($scope);
                }
            });


        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle(titleHtml).withOption('width', '25px').notSortable().renderWith(function (data, type, full, meta) {
                vm.selected[full.orderId] = false;

                return '<input type="checkbox" ng-model="vm.selected[' + data.orderId + ']" ng-click="vm.toggleOne()">';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Patient Name').renderWith(function (data, type, full) {
                return data.lastName + ', ' + data.firstName + (data.middleName ? (' ' + data.middleName) : '');
            }),
            DTColumnBuilder.newColumn(null).withTitle('D.O.B').withOption('width', '110px').renderWith(function (data, type, full) {
                return moment(data.dob).format("MM/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Bar Code').renderWith(function (data, type, full) {
                return data.barcode ? data.barcode : '';
            }),

            DTColumnBuilder.newColumn(null).withTitle('Physician').renderWith(function (data, type, full) {
                return "" + data.pFirstName + ' ' + data.pLastName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Collected').withOption('width', '80px').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data.collected);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '100px').notSortable()
                .renderWith(actionsHtml)
        ];


        vm.printLabels = function () {
            var orders = getSelectedIds();

            if (orders.length > 0) {
                PatientOrder.collect({ids: orders, date: DateUtils.convertLocalDateToServer(vm.oDate1)}, function () {
                    vm.searchQuery = '';
                    vm.dtInstance.reloadData();
                    toastr.success('The barcode were collected successfully.')
                    $rootScope.$broadcast('bluebookApp:collectedPatientOrders');
                }, function (error) {
                    console.log("error print label", error);
                })
            }
        }

        vm.changeDrawDay = function () {
            $uibModal.open({
                templateUrl: 'app/entities/window-picker/date-picker-dialog.html',
                controller: 'DatePickerDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    date: new Date()
                }
            }).result.then(function (result) {
                var orders = getSelectedIds();

                if (orders.length > 0) {
                    PatientOrder.changeDrawDay({date: vm.oDate1, newDate: result.date, ids: orders}, function () {
                        vm.dtInstance.reloadData();
                    }, function () {
                        alert("Error change draw days orders");
                    });
                }
            }, function () {
            });
        }


        function actionsHtml(data, type, full, meta) {
            var stButtons = '';
            stButtons += '<a class="btn-xs btn-primary" ' +
                'ng-click="vm.updateOrder(' + data.patientId + ', ' + data.orderId + ')">' +
                '   <i class="fa fa-eye"></i></a>&nbsp;';

            return stButtons;
        }

        vm.updateOrder = updateOrder;

        /**
         * Update order
         * @param patientId
         * @param orderId
         */
        function updateOrder(patientId, orderId) {
            PatientOrder.get({id: orderId}).$promise
                .then(function (order) {
                    $rootScope.patientOrder = order;
                    $rootScope.patientOrderChartId = patientId;
                    vm.editOrder = true;
                });
        }

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:orderEdited', function (event, data) {
            vm.editOrder = false;
            vm.search();
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:cancelPatientOrder', function (event, data) {
            vm.editOrder = false;
            vm.search();
        }));

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


        function getSelectedIds() {
            var orders = [];
            for (var id in vm.selected) {
                if (vm.selected[id]) {
                    orders.push(id);
                }
            }

            return orders;
        }


    }
})();
