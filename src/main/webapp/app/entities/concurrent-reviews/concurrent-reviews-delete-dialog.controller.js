(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConcurrentReviewsDeleteController',ConcurrentReviewsDeleteController);

    ConcurrentReviewsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ConcurrentReviews', '$sessionStorage', 'TAB'];

    function ConcurrentReviewsDeleteController($uibModalInstance, entity, ConcurrentReviews, $sessionStorage, TAB) {
        var vm = this;

        vm.concurrentReviews = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete () {
            ConcurrentReviews.delete({id: vm.concurrentReviews.id},
                function () {
                    // $sessionStorage.activePatientTab = {activeTab: TAB.CONCURRENT_REVIEW};
                    $sessionStorage.activePatientTab = {activeTab: 0};

                    $uibModalInstance.close(true);
                });
        }
    }
})();
