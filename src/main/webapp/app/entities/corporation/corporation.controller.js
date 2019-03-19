(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CorporationController', CorporationController);

    CorporationController.$inject = ['$scope', '$state', 'Corporation', 'CorporationSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function CorporationController ($scope, $state, Corporation, CorporationSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Corporations';
        vm.entityClassHumanized = 'Corporation';
        vm.entityStateName = 'corporation';

        vm.corporations = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Corporation.query(function(result) {
                  vm.corporations = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.corporations, vm.searchQuery, undefined));
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
            // Corporation.query(function(result) {
            //     vm.corporations = result;
            //     vm.searchQuery = null;
            // });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['update']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
