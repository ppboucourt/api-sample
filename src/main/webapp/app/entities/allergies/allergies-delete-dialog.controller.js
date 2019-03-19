(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllergiesDeleteController',AllergiesDeleteController);

    AllergiesDeleteController.$inject = ['$scope', '$uibModalInstance', 'entity', 'Allergies'];

    function AllergiesDeleteController($scope, $uibModalInstance, entity, Allergies) {
        var vm = this;

        vm.allergies = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Allergies.delete({id: id},
                function () {
                    $scope.$emit('allergiesDeleted', {
                        id: id
                    })
                    $uibModalInstance.close(true);
                });
        }
    }
})();
