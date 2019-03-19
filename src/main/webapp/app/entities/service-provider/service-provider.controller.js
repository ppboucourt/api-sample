(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ServiceProviderController', ServiceProviderController);

    ServiceProviderController.$inject = ['$filter', 'GUIUtils', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', '$scope', '$state', 'ServiceProvider', 'ServiceProviderSearch'];

    function ServiceProviderController ($filter, GUIUtils, $q, DTColumnBuilder, DTOptionsBuilder, $scope, $state, ServiceProvider, ServiceProviderSearch) {
        var vm = this;


        vm.search = search;
        vm.loadAll = loadAll;


        /************************************************/
        vm.serviceProviders = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                ServiceProvider.query(function (result) { //                ServiceProviderSearch.query(function (result) { //
                    vm.serviceProviders = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.serviceProviders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('phone').withTitle('Phone'),
            DTColumnBuilder.newColumn('fax').withTitle('Fax'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            ServiceProvider.query(function(result) {
                vm.serviceProviders = result;
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
