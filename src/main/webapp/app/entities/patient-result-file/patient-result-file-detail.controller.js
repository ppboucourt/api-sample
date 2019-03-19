(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultFileDetailController', PatientResultFileDetailController);

    PatientResultFileDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientResultFile', 'PatientResult'];

    function PatientResultFileDetailController($scope, $rootScope, $stateParams, previousState, entity, PatientResultFile, PatientResult) {
        var vm = this;

        vm.patientResultFile = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:patientResultFileUpdate', function(event, result) {
            vm.patientResultFile = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
