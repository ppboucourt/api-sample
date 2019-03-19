(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationRoutesDetailController', TypeMedicationRoutesDetailController);

    TypeMedicationRoutesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeMedicationRoutes'];

    function TypeMedicationRoutesDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeMedicationRoutes) {
        var vm = this;

        vm.typeMedicationRoutes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeMedicationRoutesUpdate', function(event, result) {
            vm.typeMedicationRoutes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
