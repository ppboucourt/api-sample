(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HospitalizationDetailController', HospitalizationDetailController);

    HospitalizationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Hospitalization', 'Chart'];

    function HospitalizationDetailController($scope, $rootScope, $stateParams, previousState, entity, Hospitalization, Chart) {
        var vm = this;

        vm.hospitalization = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:hospitalizationUpdate', function(event, result) {
            vm.hospitalization = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
