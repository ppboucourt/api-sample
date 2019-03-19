(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportDetailsDetailController', ReportDetailsDetailController);

    ReportDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ReportDetails', 'Reports', 'Tables', 'Fields'];

    function ReportDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, ReportDetails, Reports, Tables, Fields) {
        var vm = this;

        vm.reportDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:reportDetailsUpdate', function(event, result) {
            vm.reportDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
