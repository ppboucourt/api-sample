(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('XMLConfigDetailController', XMLConfigDetailController);

    XMLConfigDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'XMLConfig', 'Corporation'];

    function XMLConfigDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, XMLConfig, Corporation) {
        var vm = this;

        vm.xMLConfig = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:xMLConfigUpdate', function(event, result) {
            vm.xMLConfig = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
