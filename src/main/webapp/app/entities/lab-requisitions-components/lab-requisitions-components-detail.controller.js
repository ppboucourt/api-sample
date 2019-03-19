(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionsComponentsDetailController', LabRequisitionsComponentsDetailController);

    LabRequisitionsComponentsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LabRequisitionsComponents', 'LabRequisition', 'LabCompendium'];

    function LabRequisitionsComponentsDetailController($scope, $rootScope, $stateParams, previousState, entity, LabRequisitionsComponents, LabRequisition, LabCompendium) {
        var vm = this;

        vm.labRequisitionsComponents = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:labRequisitionsComponentsUpdate', function(event, result) {
            vm.labRequisitionsComponents = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
