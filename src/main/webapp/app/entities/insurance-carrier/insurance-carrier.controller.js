(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceCarrierController', InsuranceCarrierController);

    InsuranceCarrierController.$inject = ['$scope', '$state', 'InsuranceCarrier', 'InsuranceCarrierSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function InsuranceCarrierController ($scope, $state, InsuranceCarrier, InsuranceCarrierSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Insurance Carriers';
        vm.insuranceCarriers = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  InsuranceCarrier.query(function(result) {
                  vm.insuranceCarriers = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.insuranceCarriers, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('acctno').withTitle('Acctno'),
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('address').withTitle('Address'),
              DTColumnBuilder.newColumn('address2').withTitle('Address2'),
              DTColumnBuilder.newColumn('city').withTitle('City'),
              //DTColumnBuilder.newColumn('state').withTitle('State'),
              //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
              //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
              //DTColumnBuilder.newColumn('iType').withTitle('IType'),
              //DTColumnBuilder.newColumn('etin').withTitle('Etin'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            InsuranceCarrier.query(function(result) {
                vm.insuranceCarriers = result;
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
