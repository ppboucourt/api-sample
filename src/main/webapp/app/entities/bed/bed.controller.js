(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BedController', BedController);

    BedController.$inject = ['$scope', '$state', 'Bed', 'BedSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter',
        'CoreService'];

    function BedController ($scope, $state, Bed, BedSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter,
                            CoreService) {
        var vm = this;

        vm.title = 'Beds';
        vm.entityClassHumanized = 'Bed';
        vm.entityStateName = 'bed';

        vm.beds = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Bed.byFacility({id: CoreService.getCurrentFacility().id}, function(result) {
                    vm.beds = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.beds, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('room').withTitle('Room').renderWith(function (data, type, full) {
                return data ? data.name : 'Unassigned';
            }),
            DTColumnBuilder.newColumn('notes').withTitle('Notes'),
            DTColumnBuilder.newColumn(null).withTitle('Actual Chart').renderWith(function (data, type, full) {
                return data.actualChart ? 'Reserved' : 'Unassigned';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            Bed.query(function(result) {
                vm.beds = result;
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
