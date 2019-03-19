(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TubeController', TubeController);

    TubeController.$inject = ['$scope', '$state', 'Tube', 'TubeSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function TubeController ($scope, $state, Tube, TubeSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Tubes';
        vm.entityClassHumanized = 'Tube';
        vm.entityStateName = 'tube';

        vm.tubes = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Tube.query(function(result) {
                  vm.tubes = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.tubes, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Tube.query(function(result) {
                vm.tubes = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
