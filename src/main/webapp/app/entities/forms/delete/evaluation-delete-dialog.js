/**
 * Created by PpTMUnited on 5/5/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationDeleteController',EvaluationDeleteController);

    EvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Evaluation'];

    function EvaluationDeleteController($uibModalInstance, entity, Evaluation) {
        var vm = this;

        vm.evaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss();
        }

        function confirmDelete (id) {
            Evaluation.delete({id: id},
                function () {
                    $uibModalInstance.dismiss(id);
                });
        }
    }
})();

