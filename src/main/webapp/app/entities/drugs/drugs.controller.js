(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DrugsController', DrugsController);

    DrugsController.$inject = ['$scope', '$state', 'Drugs', 'DrugsSearch'];

    function DrugsController ($scope, $state, Drugs, DrugsSearch) {
        var vm = this;

        vm.drugs = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Drugs.query(function(result) {
                vm.drugs = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            DrugsSearch.query({query: vm.searchQuery}, function(result) {
                vm.drugs = result;
            });
        }    }
})();
