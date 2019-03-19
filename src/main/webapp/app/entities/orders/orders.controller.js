(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrdersController', OrdersController);

    OrdersController.$inject = ['$scope', '$state', 'Orders', 'OrdersSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function OrdersController ($scope, $state, Orders, OrdersSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.orders = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Orders.query(function(result) {
                  vm.orders = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.orders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('type_order').withTitle('Type_order'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('start_date').withTitle('Start_date'),
              DTColumnBuilder.newColumn('end_date').withTitle('End_date'),
              DTColumnBuilder.newColumn('order_by').withTitle('Order_by'),
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
            Orders.query(function(result) {
                vm.orders = result;
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
