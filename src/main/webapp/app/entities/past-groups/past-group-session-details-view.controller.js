(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsViewController', GroupSessionDetailsViewController);

    GroupSessionDetailsViewController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'GroupSessionDetails', 'Employee',
        'GroupSessionDetailsChart', '$state', 'Chart', 'CoreService', 'DTOptionsBuilder', 'DTColumnBuilder',
        '$sessionStorage', '$uibModal', '$q', '$compile', 'ChartToGroupSession'];

    function GroupSessionDetailsViewController ($timeout, $scope, $stateParams, entity, GroupSessionDetails, Employee,
                                               GroupSessionDetailsChart, $state, Chart, CoreService, DTOptionsBuilder, DTColumnBuilder,
                                               $sessionStorage, $uibModal, $q, $compile, ChartToGroupSession) {
        var vm = this;

        vm.groupSessionDetails = entity;
        vm.chartGroupSessionDetails = {};
        vm.form = {};
        vm.datePickerOpenStatus = {};
        vm.currentPatients = Chart.currentPatient({id: CoreService.getCurrentFacility().id});
        vm.groupDetailsChart = {};
        vm.groupDetailsChartList = [];
        $sessionStorage.groupDetailsChartList = [];

        vm.cancel = cancel;
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.deleteGroupSessionChart = deleteGroupSessionChart;
        vm.addChartForGroupSession = addChartForGroupSession;
        vm.changeEditingState = changeEditingState;
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            // vm.groupSessionDetails.groupSessionDetailsCharts = $sessionStorage.groupDetailsChartList;
            vm.groupSessionDetails.reviewBy = CoreService.getCurrentEmployee();
            vm.groupSessionDetails.reviewDate = new Date();
            vm.review = true;
            GroupSessionDetails.update(vm.groupSessionDetails, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {

            // GroupSessionDetailsChart.save();
            $scope.$emit('bluebookApp:groupSessionDetailsUpdate', result);
            $state.go('past-group-session', null, { reload: 'past-group-session' });
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function cancel() {
            $state.go('past-group-session', null, { reload: 'past-group-session' });
        }

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        vm.datePickerOpenStatus.reviewDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        function addChartForGroupSession() {
            vm.groupDetailsChart.count = $sessionStorage.groupDetailsChartList.length;
            $sessionStorage.groupDetailsChartList.push(vm.groupDetailsChart);
            vm.groupDetailsChartList = $sessionStorage.groupDetailsChartList;
            vm.dtInstance.reloadData();
            vm.groupDetailsChart = {};
        }

        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                GroupSessionDetailsChart.byGroupSessionDetails({gsId: vm.groupSessionDetails.id}, function (result) {
                    if(result){
                        vm.chartGroupSessionDetails = result;
                        defer.resolve(result);
                    }
                });
            }else {
                defer.resolve($filter('filter')(vm.groupDetailsChartList, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Name').withOption('width', '155px').notSortable()
                .renderWith(function (data, type, full) {
                    return data.chart.patient ? (data.chart.patient.firstName + ' ' + data.chart.patient.lastName) : 'Empty';
                }),
            DTColumnBuilder.newColumn(null).withTitle('Notes').renderWith(function (data, type, full) {
                return data.notes ;
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
            //     .renderWith(function (data, type, full) {
            //         return actionsHtml(data);
            //     })
        ];

        // function actionsHtml(data, type, full, meta){
        //     return '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class="btn-sm btn-danger" title="Review Event" ' +
        //         'ng-click="vm.deleteGroupSessionChart('+ data.count +')">' +
        //         '<i class="fa fa-trash"></i></a>';
        // }

        var modalInstance = null;

        function deleteGroupSessionChart(count) {
            if (modalInstance !== null) return;
            modalInstance = $uibModal.open({
                animation: true,
                backdrop: 'static',
                keyboard: false,
                templateUrl: 'app/entities/group-session-details/group-session-details-chart-new-delete.html',
                controller: 'GroupSessionDetailsChartDeleteController',
                controllerAs: 'vm',
                size: 'md',
                resolve: {
                    count: count
                }
            });
            modalInstance.result.then(
                resetModalDelete,
                resetModalDelete
            );
        }

        var resetModalDelete = function () {
            modalInstance = null;
            vm.groupDetailsChartList = $sessionStorage.groupDetailsChartList;
            vm.dtInstance.reloadData();
        };

    }
})();
