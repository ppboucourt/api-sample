(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BuildingController', BuildingController);

    BuildingController.$inject = ['$scope', '$state', 'Building', 'BuildingSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder',
        'GUIUtils', '$filter', '$compile', 'CoreService'];

    function BuildingController ($scope, $state, Building, BuildingSearch, $q, DTColumnBuilder, DTOptionsBuilder,
                                 GUIUtils, $filter, $compile, CoreService ) {
        var vm = this;
        vm.title = 'Buildings';
        vm.entityClassHumanized = 'Building';
        vm.entityStateName = 'building';

        vm.buildings = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if(CoreService.getCurrentFacility()){
                if (!vm.searchQuery || vm.searchQuery == '') {
                    Building.byfacility({id: CoreService.getCurrentFacility().id}, function(result) {
                        vm.buildings = result;
                        defer.resolve(result);
                    });
                }else {
                    defer.resolve($filter('filter')(vm.buildings, vm.searchQuery, undefined));
                }
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              // DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Building.query(function(result) {
                vm.buildings = result;
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
