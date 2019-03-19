(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Icd10DetailController', Icd10DetailController);

    Icd10DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Icd10', 'Chart'];

    function Icd10DetailController($scope, $rootScope, $stateParams, previousState, entity, Icd10, Chart) {
        var vm = this;

        vm.icd10 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:icd10Update', function(event, result) {
            vm.icd10 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
