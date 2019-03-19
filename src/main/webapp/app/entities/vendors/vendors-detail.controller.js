(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VendorsDetailController', VendorsDetailController);

    VendorsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vendors'];

    function VendorsDetailController($scope, $rootScope, $stateParams, previousState, entity, Vendors) {
        var vm = this;

        vm.vendors = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:vendorsUpdate', function(event, result) {
            vm.vendors = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
