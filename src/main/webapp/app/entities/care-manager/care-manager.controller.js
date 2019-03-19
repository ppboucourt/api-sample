(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CareManagerController', CareManagerController);

    CareManagerController.$inject = ['$scope', '$state', 'CareManager', 'CareManagerSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function CareManagerController ($scope, $state, CareManager, CareManagerSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.careManagers = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  CareManager.query(function(result) {
                  vm.careManagers = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.careManagers, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('full_name').withTitle('Full_name'),
              DTColumnBuilder.newColumn('phone').withTitle('Phone'),
              DTColumnBuilder.newColumn('insurance_company').withTitle('Insurance_company'),
              DTColumnBuilder.newColumn('notes').withTitle('Notes'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            CareManager.query(function(result) {
                vm.careManagers = result;
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
