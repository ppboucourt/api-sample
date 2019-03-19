(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationDetailController', EvaluationDetailController);

    EvaluationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Evaluation', 'TypePatientProcess', 'TypeEvaluation', 'EvaluationContent', 'EvaluationItems'];

    function EvaluationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Evaluation, TypePatientProcess, TypeEvaluation, EvaluationContent, EvaluationItems) {
        var vm = this;

        vm.evaluation = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:evaluationUpdate', function(event, result) {
            vm.evaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
