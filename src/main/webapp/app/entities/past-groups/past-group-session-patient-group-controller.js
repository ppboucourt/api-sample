/**
 * Created by PpTMUnited on 1/13/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PastGroupSessionInfoController', PastGroupSessionInfoController);

    PastGroupSessionInfoController.$inject = ['$q', 'DTColumnBuilder' , 'DTOptionsBuilder',
   '$filter', 'CoreService', 'Chart', 'ChartToGroupSession', 'entity'];

    function PastGroupSessionInfoController ( $q, DTColumnBuilder, DTOptionsBuilder,
                                          $filter, CoreService,  ChartToGroupSession, entity) {
        var vm = this;
        //Functions
        vm.chartToGroupSession = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        //Variable

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                ChartToGroupSession.byfacilityend({id: CoreService.getCurrentFacility().id}, function(result) {
                    vm.chartToGroupSession = result;
                    defer.resolve(result);

                });
            }else {
                defer.resolve($filter('filter')(vm.chartToGroupSession, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');
        vm.dtColumns = [
             DTColumnBuilder.newColumn('name').withTitle('Name'),
             DTColumnBuilder.newColumn('dayWeek').withTitle('Week Day'),
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
            ChartToGroupSession.query(function(result) {
                vm.groupSession = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" title="Review Event" ' +
                'href="#/past-group-session/'+ data.id +'/past-group-session-details-view">' +
                '<i class="fa fa-eye"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();

