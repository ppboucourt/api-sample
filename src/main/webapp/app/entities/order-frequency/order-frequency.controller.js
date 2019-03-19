(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderFrequencyController', OrderFrequencyController);

    OrderFrequencyController.$inject = ['$scope', '$state', 'OrderFrequency', 'OrderFrequencySearch'];

    function OrderFrequencyController ($scope, $state, OrderFrequency, OrderFrequencySearch) {
        var vm = this;

        vm.orderFrequencies = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            OrderFrequency.query(function(result) {
                vm.orderFrequencies = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            OrderFrequencySearch.query({query: vm.searchQuery}, function(result) {
                vm.orderFrequencies = result;
            });
        }    }
})();
