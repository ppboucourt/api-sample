/**
 * Created by PpTMUnited on 1/13/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PastGroupController', PastGroupController);

    PastGroupController.$inject = ['$scope', '$state', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder',
        'GUIUtils', '$filter', 'CoreService', 'Chart', '$sessionStorage', '$compile', 'GroupSession', 'moment'];

    function PastGroupController ($scope, $state, $q, DTColumnBuilder, DTOptionsBuilder,
                                          GUIUtils, $filter, CoreService, Chart, $sessionStorage, $compile, GroupSession, moment ) {
        var vm = this;
        //Functions
        vm.groupSession = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        //Variable

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                GroupSession.byfacilityend({id: CoreService.getCurrentFacility().id}, function(result) {
                    vm.groupSession = result;
                    defer.resolve(result);

                });
            }else {
                defer.resolve($filter('filter')(vm.groupSession, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');
        vm.dtColumns = [
             DTColumnBuilder.newColumn('name').withTitle('GroupName'),
             DTColumnBuilder.newColumn('dayWeek').withTitle('Week Day').renderWith(function (data, type, full) {
                 return data ? GUIUtils.expandWeekCodeString(data, ',') : "";
             }),
             DTColumnBuilder.newColumn('startTime').withTitle('Start Time').renderWith(function (data, type, full) {
                 return CoreService.parseToTime(data);
             }),
             DTColumnBuilder.newColumn('endTime').withTitle('End Time').renderWith(function (data, type, full) {
                    return CoreService.parseToTime(data);

               }),
            //DTColumnBuilder.newColumn('order_via').withTitle('Order_via'),
            //DTColumnBuilder.newColumn('doctor_signature').withTitle('Doctor_signature'),
            //DTColumnBuilder.newColumn('reviewed_by').withTitle('Reviewed_by'),
            //DTColumnBuilder.newColumn('review_date').withTitle('Review_date'),
            //DTColumnBuilder.newColumn('frequency').withTitle('Frequency'),
            //DTColumnBuilder.newColumn('on_admission').withTitle('On_admission'),
            //DTColumnBuilder.newColumn('until_discharge').withTitle('Until_discharge'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];
        function loadAll() {
            GroupSession.query(function(result) {
                vm.groupSession = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" title="View Event" ' +
                'href="#/past-group-session/'+ data.id +'/past-group-session-details-view">' +
                '<i class="fa fa-eye"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();

