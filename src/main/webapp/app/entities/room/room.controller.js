(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('RoomController', RoomController);

    RoomController.$inject = ['$scope', '$state', 'Room', 'RoomSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils',
        '$filter', '$compile', 'CoreService'];

    function RoomController ($scope, $state, Room, RoomSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils,
                             $filter, $compile, CoreService ) {
        var vm = this;
        vm.title = 'Rooms';
        vm.entityClassHumanized = 'Room';
        vm.entityStateName = 'room';

        vm.rooms = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Room.byfacility({id: CoreService.getCurrentFacility().id}, function(result) {
                    vm.rooms = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.rooms, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('building').withTitle('Building').renderWith(function (data, type, full) {
                return data ? data.name : "Unassigned";
            }),
            DTColumnBuilder.newColumn('sex').withTitle('Sex'),
            DTColumnBuilder.newColumn('notes').withTitle('Notes'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            Room.query(function(result) {
                vm.rooms = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['update', 'delete']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
