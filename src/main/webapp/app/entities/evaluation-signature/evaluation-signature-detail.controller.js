(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationSignatureDetailController', EvaluationSignatureDetailController);

    EvaluationSignatureDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluationSignature', 'Evaluation', 'Employee', 'Signature'];

    function EvaluationSignatureDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluationSignature, Evaluation, Employee, Signature) {
        var vm = this;

        vm.evaluationSignature = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:evaluationSignatureUpdate', function(event, result) {
            vm.evaluationSignature = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
