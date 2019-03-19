(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionController', GroupSessionController);

    GroupSessionController.$inject = ['$scope', '$state', 'GroupSession', 'GroupSessionSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'moment', 'CoreService'];

    function GroupSessionController ($scope, $state, GroupSession, GroupSessionSearch, $q, DTColumnBuilder,
                                     DTOptionsBuilder, GUIUtils, $filter, moment, CoreService ) {
        var vm = this;

        vm.groupSessions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        vm.title = 'Group Session';
        vm.entityClassHumanized = 'Group Session';
        vm.entityStateName = 'group-session';

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  GroupSession.byfacility({id: CoreService.getCurrentFacility().id}, function(result) {
                  vm.groupSessions = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.groupSessions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');
        // moment(data[value].date_prescription).format('hh:mm:ss a')
        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn(null).withTitle('Days of Week').renderWith(function (data, type, full) {
                return data.dayWeek ? GUIUtils.expandWeekCodeString(data.dayWeek, ',') : "Empty";
              }),
              DTColumnBuilder.newColumn(null).withTitle('Start Time').renderWith(function (data, type, full) {
                  return data.startTime ? moment(data.startTime).format('hh:mm:ss a') : "Empty";
              }),
              DTColumnBuilder.newColumn(null).withTitle('End Time').renderWith(function (data, type, full) {
                  return data.endTime ? moment(data.endTime).format('hh:mm:ss a') : "Empty";
              }),
              // DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('enabled').withTitle('Enabled'),
              DTColumnBuilder.newColumn('billable').withTitle('Billable'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            GroupSession.query(function(result) {
                vm.groupSessions = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" ui-sref="group-session-detail({id:' + data.id + '})" href="#/group-session/' + data.id + '">' +
                '<i class="fa fa-eye"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ui-sref="group-session.delete({id:' + data.id + '})"  href="#/group-session/' + data.id + '/delete">' +
                '<i class="fa fa-trash"></i></a>';
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
