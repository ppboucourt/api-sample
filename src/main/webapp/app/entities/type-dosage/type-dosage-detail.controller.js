(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDosageDetailController', TypeDosageDetailController);

    TypeDosageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeDosage'];

    function TypeDosageDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeDosage) {
        var vm = this;

        vm.typeDosage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeDosageUpdate', function(event, result) {
            vm.typeDosage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
