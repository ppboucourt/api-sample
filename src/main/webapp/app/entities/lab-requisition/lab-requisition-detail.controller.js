(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionDetailController', LabRequisitionDetailController);

    LabRequisitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LabRequisition', 'LabCompendium', 'LabRequisitionsComponents', 'Chart'];

    function LabRequisitionDetailController($scope, $rootScope, $stateParams, previousState, entity, LabRequisition, LabCompendium, LabRequisitionsComponents, Chart) {
        var vm = this;

        vm.labRequisition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:labRequisitionUpdate', function(event, result) {
            vm.labRequisition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
