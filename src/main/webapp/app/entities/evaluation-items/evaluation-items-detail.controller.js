(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationItemsDetailController', EvaluationItemsDetailController);

    EvaluationItemsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluationItems', 'Evaluation'];

    function EvaluationItemsDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluationItems, Evaluation) {
        var vm = this;

        vm.evaluationItems = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:evaluationItemsUpdate', function(event, result) {
            vm.evaluationItems = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
