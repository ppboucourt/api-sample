(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabCompendiumDetailController', LabCompendiumDetailController);

    LabCompendiumDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'LabCompendium', 'LabRequisition', 'LabRequisitionsComponents', 'Orders', 'LabProfile'];

    function LabCompendiumDetailController($scope, $rootScope, $stateParams, previousState, entity, LabCompendium, LabRequisition, LabRequisitionsComponents, Orders, LabProfile) {
        var vm = this;

        vm.labCompendium = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:labCompendiumUpdate', function(event, result) {
            vm.labCompendium = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
