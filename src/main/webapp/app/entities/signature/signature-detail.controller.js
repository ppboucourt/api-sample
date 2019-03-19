(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('SignatureDetailController', SignatureDetailController);

    SignatureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Signature'];

    function SignatureDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Signature) {
        var vm = this;

        vm.signature = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:signatureUpdate', function(event, result) {
            vm.signature = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
