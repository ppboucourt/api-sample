(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CareManagerDetailController', CareManagerDetailController);

    CareManagerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CareManager', 'Chart'];

    function CareManagerDetailController($scope, $rootScope, $stateParams, previousState, entity, CareManager, Chart) {
        var vm = this;

        vm.careManager = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:careManagerUpdate', function(event, result) {
            vm.careManager = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
