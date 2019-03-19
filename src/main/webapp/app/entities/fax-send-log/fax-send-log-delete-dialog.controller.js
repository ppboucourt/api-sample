(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FaxSendLogDeleteController',FaxSendLogDeleteController);

    FaxSendLogDeleteController.$inject = ['$uibModalInstance', 'entity', 'FaxSendLog'];

    function FaxSendLogDeleteController($uibModalInstance, entity, FaxSendLog) {
        var vm = this;

        vm.faxSendLog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FaxSendLog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
