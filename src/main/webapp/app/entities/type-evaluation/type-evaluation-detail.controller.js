(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEvaluationDetailController', TypeEvaluationDetailController);

    TypeEvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeEvaluation'];

    function TypeEvaluationDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeEvaluation) {
        var vm = this;

        vm.typeEvaluation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeEvaluationUpdate', function(event, result) {
            vm.typeEvaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
