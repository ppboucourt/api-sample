(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeSpecialityController', TypeSpecialityController);

    TypeSpecialityController.$inject = ['$scope', '$state', 'TypeSpeciality', 'TypeSpecialitySearch'];

    function TypeSpecialityController ($scope, $state, TypeSpeciality, TypeSpecialitySearch) {
        var vm = this;

        vm.typeSpecialitys = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            TypeSpeciality.query(function(result) {
                vm.typeSpecialitys = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            TypeSpecialitySearch.query({query: vm.searchQuery}, function(result) {
                vm.typeSpecialitys = result;
            });
        }    }
})();
