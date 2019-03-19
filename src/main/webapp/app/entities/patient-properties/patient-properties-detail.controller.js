(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Patient_propertiesDetailController', Patient_propertiesDetailController);

    Patient_propertiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Patient_properties', 'Chart'];

    function Patient_propertiesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Patient_properties, Chart) {
        var vm = this;

        vm.patient_properties = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:patient_propertiesUpdate', function(event, result) {
            vm.patient_properties = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
