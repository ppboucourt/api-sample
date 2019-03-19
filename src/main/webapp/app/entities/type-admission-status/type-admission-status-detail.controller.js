(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeAdmissionStatusDetailController', TypeAdmissionStatusDetailController);

    TypeAdmissionStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeAdmissionStatus', 'Chart', 'Facility'];

    function TypeAdmissionStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeAdmissionStatus, Chart, Facility) {
        var vm = this;

        vm.typeAdmissionStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeAdmissionStatusUpdate', function(event, result) {
            vm.typeAdmissionStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
