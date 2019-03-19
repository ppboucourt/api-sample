(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderComponentController', OrderComponentController);

    OrderComponentController.$inject = ['$scope', '$state', 'OrderComponent', 'OrderComponentSearch', '$q',
        'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function OrderComponentController ($scope, $state, OrderComponent, OrderComponentSearch, $q,
                                       DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.orderComponents = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  OrderComponent.query(function(result) {
                  vm.orderComponents = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.orderComponents, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('days').withTitle('Days'),
              DTColumnBuilder.newColumn('medication').withTitle('Medication'),
              DTColumnBuilder.newColumn('dosage_form').withTitle('Dosage_form'),
              DTColumnBuilder.newColumn('dose').withTitle('Dose'),
              DTColumnBuilder.newColumn('administration_time').withTitle('Administration_time'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
              //DTColumnBuilder.newColumn('frequency').withTitle('Frequency'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            OrderComponent.query(function(result) {
                vm.orderComponents = result;
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
