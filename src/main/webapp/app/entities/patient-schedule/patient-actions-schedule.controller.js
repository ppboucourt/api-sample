(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionsScheduleController', ActionsScheduleController);

    ActionsScheduleController.$inject = ['PatientActionTake', '$sessionStorage', '$uibModal', '$rootScope', '$state', '$compile', '$q',
        '$scope', 'GUIUtils', 'PatientAction', 'chart', 'DTOptionsBuilder', 'DTColumnBuilder', 'DateUtils'];

    function ActionsScheduleController(PatientActionTake, $sessionStorage, $uibModal, $rootScope, $state, $compile, $q,
                                       $scope, GUIUtils, PatientAction, chart, DTOptionsBuilder, DTColumnBuilder, DateUtils) {
        var vm = this;

        vm.actions = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.dtInstanceAsNeeded = {};
        vm.barCode = {};

        vm.selected = {};
        vm.selectAll = false;
        vm.toggleAll = toggleAll;
        vm.toggleOne = toggleOne;

        var titleHtml = '<input type="checkbox" ng-model="vm.selectAll" ng-change="vm.toggleAll()">';

        //Event when clicked in Mars Tab
        $scope.$on('$destroy', $rootScope.$on('bluebookApp:clickedMarsTab', function (event, data) {
            if (data && vm.dtInstance) {
                search();
            }
        }));

        vm.rmConfig1 = {
            mondayStart: false,
            initState: "month",
            maxState: "decade",
            minState: "month",
            decadeSize: 12,
            monthSize: 42,
            min: new Date("2016-01-01"),
            max: null,
            format: "MM/dd/yyyy"
        };

        vm.oDate1 = new Date();
        vm.select = function () {
            vm.dtInstance.reloadData();
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            PatientAction.today_actions_vo({
                id: chart.id,
                date: DateUtils.convertLocalDateToServer(vm.oDate1)
            }, function (result) {
                vm.actions = result.sort(function (a, b) {
                    // Turn your strings into dates, and then subtract them
                    // to get a value that is either negative, positive, or zero.
                    return new Date(a.collectedDate).getTime() - new Date(b.collectedDate).getTime();
                });
                defer.resolve(result);
                var c = 0;

                for (var i = 0; i < result.length; i++) {
                    if (!result[i].collected) {
                        c += 1;
                    }
                }

                $rootScope.$broadcast('bluebookApp:actions', {count: c});

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
            DTColumnBuilder.newColumn(null).withTitle('Action').renderWith(function (data, type, full) {
                return data.actionName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Occurred').renderWith(function (data, type, full) {
                return data.collected ? 'Yes' : 'No';//     GUIUtils.colorHtmlYesNo(data.collected);
            }),

            DTColumnBuilder.newColumn(null).withTitle('Time').withOption('width', '50px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.collectedDate ? '<span class="label label-success"><strong><i class="fa fa-clock-o"></i> ' +
                        moment(data.collectedDate).format("hh:mm a") + '</strong></span>' : '-';
                }),

            DTColumnBuilder.newColumn(null).withTitle('Needed').renderWith(function (data, type, full) {
                return data.asNeeded ? 'Yes' : 'No';//    GUIUtils.colorHtmlYesNo(data.asNeeded);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '80px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            if (data.actionTakeStatus == 'OCCURRED' || data.actionTakeStatus == 'CANCELED' || data.actionTakeStatus == 'REJECTED') {
                stButtons += '<a class="btn btn-sm btn-primary"     title="Details" ng-click="vm.details({id:' + data.patientActionTakeId + '})" >' +
                    '   <i class="glyphicon glyphicon-info-sign"></i></a>&nbsp;';
            } else {
                stButtons += '<a class="btn btn-sm btn-primary"     title="Occur" ng-click="vm.ocurrer({id:' + data.patientActionTakeId + '})" >' +
                    '   <i class="glyphicon glyphicon-transfer"></i></a>&nbsp;';

                stButtons += '<a class="btn btn-sm btn-danger"     title="Rejecter" ng-click="vm.rejecter({id:' + data.patientActionTakeId + '})" >' +
                    '   <i class="glyphicon glyphicon-ban-circle"></i></a>&nbsp;';
            }
            return stButtons;
        }

        vm.dtOptionsAsNeeded = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();

            PatientAction.as_needed_actions({id: chart.id}, function (result) {
                vm.as_needed_actions = result;
                defer.resolve(result);
            });
            return defer.promise;
        }).withBootstrap().withDOM('tip')
            .withOption('createdRow', function (row, data, dataIndex) {
                $compile(angular.element(row).contents())($scope);
            });

        vm.dtColumnsAsNeeded = [
            DTColumnBuilder.newColumn(null).withTitle('As Needed').renderWith(function (data, type, full) {
                return data.action;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Take').withOption('width', '100px').notSortable()
                .renderWith(asNeededHtml)
        ];


        function asNeededHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn-xs btn-primary" ng-click="vm.addAsNeeded(' + full.patientActionId + ')">' +
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

        vm.goSchedule = function (order) {
            $state.go('clinic-schedule.pat-detail', {oid: order, date: moment(vm.oDate1).format("Y-MM-DD")});
        }

        vm.addAsNeeded = function (id) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-as-needed-dialog.html',
                controller: 'PatientAsNeededDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md'
            }).result.then(function (result) {
                if (result) {
                    PatientAction.add_needed_action({patientId: id, dateTime: result.date}, function () {
                        vm.dtInstance.reloadData();
                    })
                }
            }, function () {
            });
        };


        vm.rejecter = function (data) {

            $sessionStorage.takeTitle = "Rejecter";
            $sessionStorage.takeStatus = "REJECTED";

            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-actions-take-doaction-dialog.html',
                controller: 'PatientActionTakeDoActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientActionTake: PatientActionTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


        vm.ocurrer = function (data) {
            $sessionStorage.takeTitle = "Occur";
            $sessionStorage.takeStatus = "OCCURRED";

            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-actions-take-doaction-dialog.html',
                controller: 'PatientActionTakeDoActionDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: 'md',
                resolve: {
                    patientActionTake: PatientActionTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


        vm.details = function (data) {
            $uibModal.open({
                templateUrl: 'app/entities/patient-schedule/patient-actions-take-details-dialog.html',
                controller: 'PatientActionTakeDetailsDialogController',
                controllerAs: 'vm',
                backdrop: 'static',
                size: data.notes ? 'md' : 'sm',
                resolve: {
                    patientActionTake: PatientActionTake.get({id: data.id})
                }
            }).result.then(function (result) {
                vm.dtInstance.reloadData();
            }, function () {
            });

        }


    }
})();
