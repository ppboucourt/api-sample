(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationTemplateDetailController', EvaluationTemplateDetailController);

    EvaluationTemplateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'EvaluationTemplate', 'TypePatientProcess', 'TypeEvaluation', 'EvaluationContent'];

    function EvaluationTemplateDetailController($scope, $rootScope, $stateParams, previousState, entity, EvaluationTemplate, TypePatientProcess, TypeEvaluation, EvaluationContent) {
        var vm = this;

        vm.evaluationTemplate = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:evaluationTemplateUpdate', function(event, result) {
            vm.evaluationTemplate = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
