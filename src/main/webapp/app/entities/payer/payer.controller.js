(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PayerController', PayerController);

    PayerController.$inject = ['$scope', '$state', 'Payer', 'PayerSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function PayerController ($scope, $state, Payer, PayerSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.payers = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Payer.query(function(result) {
                  vm.payers = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.payers, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('insurance_company').withTitle('Insurance_company'),
              DTColumnBuilder.newColumn('address').withTitle('Address'),
              DTColumnBuilder.newColumn('address_two').withTitle('Address_two'),
              DTColumnBuilder.newColumn('city').withTitle('City'),
              DTColumnBuilder.newColumn('state').withTitle('State'),
              //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
              //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Payer.query(function(result) {
                vm.payers = result;
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
