(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FaxSendLogDetailController', FaxSendLogDetailController);

    FaxSendLogDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'FaxSendLog'];

    function FaxSendLogDetailController($scope, $rootScope, $stateParams, previousState, entity, FaxSendLog) {
        var vm = this;

        vm.faxSendLog = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:faxSendLogUpdate', function(event, result) {
            vm.faxSendLog = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
