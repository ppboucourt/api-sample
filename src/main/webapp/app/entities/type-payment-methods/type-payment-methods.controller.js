(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePaymentMethodsController', TypePaymentMethodsController);

    TypePaymentMethodsController.$inject = ['$scope', '$state', 'TypePaymentMethods', 'TypePaymentMethodsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypePaymentMethodsController ($scope, $state, TypePaymentMethods, TypePaymentMethodsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typePaymentMethods = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypePaymentMethods.query(function(result) {
                  vm.typePaymentMethods = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typePaymentMethods, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('category').withTitle('Category'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypePaymentMethods.query(function(result) {
                vm.typePaymentMethods = result;
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
