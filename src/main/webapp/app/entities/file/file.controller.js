(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FileController', FileController);

    FileController.$inject = ['$scope', '$state', 'File', 'FileSearch'];

    function FileController ($scope, $state, File, FileSearch) {
        var vm = this;

        vm.files = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            File.query(function(result) {
                vm.files = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            FileSearch.query({query: vm.searchQuery}, function(result) {
                vm.files = result;
            });
        }    }
})();
