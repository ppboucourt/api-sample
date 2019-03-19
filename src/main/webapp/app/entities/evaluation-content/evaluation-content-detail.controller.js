(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationContentDetailController', EvaluationContentDetailController);

    EvaluationContentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluationContent'];

    function EvaluationContentDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluationContent) {
        var vm = this;

        vm.evaluationContent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:evaluationContentUpdate', function(event, result) {
            vm.evaluationContent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
