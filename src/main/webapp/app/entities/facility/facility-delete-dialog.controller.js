(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FacilityDeleteController',FacilityDeleteController);

    FacilityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Facility', '$rootScope'];

    function FacilityDeleteController($uibModalInstance, entity, Facility, $rootScope) {
        var vm = this;

        vm.facility = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Facility.delete({id: id},
                function () {
                    $rootScope.$broadcast('bluebookApp:deleteFacilities', id);
                    $uibModalInstance.close(true);
                });
        }
    }
})();
