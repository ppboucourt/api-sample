(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'app',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: 'error.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error.html'
                    }
                }
            })
            .state('accessdenied', {
                parent: 'app',
                url: '/accessdenied',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/accessdenied.html'
                    }
                }
            })
            .state('maintenance', {
                parent: 'app',
                url: '/maintenance',
                ncyBreadcrumb: {
                    label: 'Maintenance'
                },
                data: {
                    authorities: [],
                    pageTitle: 'Under Construction'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/construction.html',
                        controller: 'ConstructionController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    previousState: ["$state", function ($state) {
                        return {
                            name: $state.current.name || 'maintenance',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                    }]
                }
            });
    }
})();
