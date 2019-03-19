(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderComponentNewDeleteController',OrderComponentNewDeleteController);

    OrderComponentNewDeleteController.$inject = ['$uibModalInstance', 'count', '$sessionStorage'];

    function OrderComponentNewDeleteController($uibModalInstance, count, $sessionStorage) {
        var vm = this;

        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete () {
                var data = $.grep($sessionStorage.orderComponentList, function(e){
                    return e.count != count;
                });
                $sessionStorage.orderComponentList = data;
                $uibModalInstance.close(true);
        }
    }
})();
