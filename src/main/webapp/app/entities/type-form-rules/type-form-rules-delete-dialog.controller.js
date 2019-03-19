(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFormRulesDeleteController',TypeFormRulesDeleteController);

    TypeFormRulesDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeFormRules'];

    function TypeFormRulesDeleteController($uibModalInstance, entity, TypeFormRules) {
        var vm = this;

        vm.typeFormRules = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeFormRules.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
