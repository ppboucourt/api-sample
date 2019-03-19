(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TablesDetailController', TablesDetailController);

    TablesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tables', 'Fields'];

    function TablesDetailController($scope, $rootScope, $stateParams, previousState, entity, Tables, Fields) {
        var vm = this;

        vm.tables = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:tablesUpdate', function(event, result) {
            vm.tables = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
