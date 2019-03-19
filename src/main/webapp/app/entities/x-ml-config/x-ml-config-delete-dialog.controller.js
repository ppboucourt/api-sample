(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('XMLConfigDeleteController',XMLConfigDeleteController);

    XMLConfigDeleteController.$inject = ['$uibModalInstance', 'entity', 'XMLConfig'];

    function XMLConfigDeleteController($uibModalInstance, entity, XMLConfig) {
        var vm = this;

        vm.xMLConfig = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            XMLConfig.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
