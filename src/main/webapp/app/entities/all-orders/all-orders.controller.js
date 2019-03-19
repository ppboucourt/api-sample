(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllOrdersController', AllOrdersController);

    AllOrdersController.$inject = ['$scope', '$state', 'AllOrders', 'AllOrdersSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function AllOrdersController ($scope, $state, AllOrders, AllOrdersSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.allOrders = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        vm.title = 'Orders';
        vm.entityClassHumanized = 'Order';
        vm.entityStateName = 'all-orders';

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  AllOrders.query(function(result) {
                  vm.allOrders = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.allOrders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('enabled').withTitle('Enabled'),
              DTColumnBuilder.newColumn('prn').withTitle('Prn'),
              DTColumnBuilder.newColumn('indication').withTitle('Indication'),
              DTColumnBuilder.newColumn('duration_days').withTitle('Duration_days'),
              //DTColumnBuilder.newColumn('note').withTitle('Note'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            AllOrders.query(function(result) {
                vm.allOrders = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-primary" ui-sref="all-orders-detail({id:' + data.id + '})" href="#/all-orders/' + data.id + '">' +
                '<i class="fa fa-eye"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ui-sref="all-orders.delete({id:' + data.id + '})"  href="#/all-orders/' + data.id + '/delete">' +
                '<i class="fa fa-trash"></i></a>';
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
