(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CountryStateController', CountryStateController);

    CountryStateController.$inject = ['$scope', '$state', 'CountryState', 'CountryStateSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function CountryStateController ($scope, $state, CountryState, CountryStateSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.countryStates = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  CountryState.query(function(result) {
                  vm.countryStates = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.countryStates, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('state').withTitle('State'),
              DTColumnBuilder.newColumn('stateCode').withTitle('StateCode'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            CountryState.query(function(result) {
                vm.countryStates = result;
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
