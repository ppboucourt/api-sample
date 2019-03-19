(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('dashboard', {
            parent: 'app',
            url: '/dashboard',
            ncyBreadcrumb: {
                label: 'Dashboard',
                parent: 'app'
            },
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/dashboard.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        }).state('dcreview', {
            parent: 'app',
            url: '/dcreview',
            ncyBreadcrumb: {
                label: 'Concurrent Review in Date',
                parent: 'app'
            },
            data: {
                authorities: ['ROLE_USER']
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/concurrent-review.html',
                    controller: 'DashboardConcurrentReviewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        });
    }
})();
