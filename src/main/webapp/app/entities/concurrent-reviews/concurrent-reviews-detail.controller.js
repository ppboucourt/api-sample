(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConcurrentReviewsDetailController', ConcurrentReviewsDetailController);

    ConcurrentReviewsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ConcurrentReviews', 'Chart'];

    function ConcurrentReviewsDetailController($scope, $rootScope, $stateParams, previousState, entity, ConcurrentReviews, Chart) {
        var vm = this;

        vm.concurrentReviews = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:concurrentReviewsUpdate', function(event, result) {
            vm.concurrentReviews = result;

        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
