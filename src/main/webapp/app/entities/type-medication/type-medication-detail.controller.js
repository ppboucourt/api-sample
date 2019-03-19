(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationDetailController', TypeMedicationDetailController);

    TypeMedicationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeMedication'];

    function TypeMedicationDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeMedication) {
        var vm = this;

        vm.typeMedication = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeMedicationUpdate', function(event, result) {
            vm.typeMedication = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
