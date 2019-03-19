(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePreAdmissionStatusDetailController', TypePreAdmissionStatusDetailController);

    TypePreAdmissionStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePreAdmissionStatus'];

    function TypePreAdmissionStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePreAdmissionStatus) {
        var vm = this;

        vm.typePreAdmissionStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePreAdmissionStatusUpdate', function(event, result) {
            vm.typePreAdmissionStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
