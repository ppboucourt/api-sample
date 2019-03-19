(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionController', ActionController);

    ActionController.$inject = ['$scope', '$state', 'Action', 'ActionSearch'];

    function ActionController ($scope, $state, Action, ActionSearch) {
        var vm = this;

        vm.actions = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            Action.query(function(result) {
                vm.actions = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            ActionSearch.query({query: vm.searchQuery}, function(result) {
                vm.actions = result;
            });
        }    }
})();
