(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VendorsController', VendorsController);

    VendorsController.$inject = ['$scope', '$state', 'Vendors', 'VendorsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function VendorsController ($scope, $state, Vendors, VendorsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.vendors = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Vendors.query(function(result) {
                  vm.vendors = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.vendors, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('company_name').withTitle('Company_name'),
              DTColumnBuilder.newColumn('use_this_vendor').withTitle('Use_this_vendor'),
              DTColumnBuilder.newColumn('contact_name').withTitle('Contact_name'),
              DTColumnBuilder.newColumn('contact_phone').withTitle('Contact_phone'),
              DTColumnBuilder.newColumn('address').withTitle('Address'),
              //DTColumnBuilder.newColumn('address_two').withTitle('Address_two'),
              //DTColumnBuilder.newColumn('city').withTitle('City'),
              //DTColumnBuilder.newColumn('state').withTitle('State'),
              //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
              //DTColumnBuilder.newColumn('company_phone').withTitle('Company_phone'),
              //DTColumnBuilder.newColumn('company_fax').withTitle('Company_fax'),
              //DTColumnBuilder.newColumn('notes').withTitle('Notes'),
              //DTColumnBuilder.newColumn('fax_order_number').withTitle('Fax_order_number'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Vendors.query(function(result) {
                vm.vendors = result;
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
