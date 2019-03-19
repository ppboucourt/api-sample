(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('service-provider', {
            parent: 'entity',
            url: '/service-provider?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceProviders'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-provider/service-providers.html',
                    controller: 'ServiceProviderController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
            }
        })
        .state('service-provider-detail', {
            parent: 'entity',
            url: '/service-provider/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceProvider'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-provider/service-provider-detail.html',
                    controller: 'ServiceProviderDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ServiceProvider', function($stateParams, ServiceProvider) {
                    return ServiceProvider.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'service-provider',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('service-provider-detail.edit', {
            parent: 'service-provider-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-provider.new', {
            parent: 'service-provider',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                providerType: null,
                                fax: null,
                                phone: null,
                                address: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('service-provider');
                });
            }]
        })
        .state('service-provider.edit', {
            parent: 'service-provider',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-dialog.html',
                    controller: 'ServiceProviderDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-provider.delete', {
            parent: 'service-provider',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-provider/service-provider-delete-dialog.html',
                    controller: 'ServiceProviderDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiceProvider', function(ServiceProvider) {
                            return ServiceProvider.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-provider', null, { reload: 'service-provider' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
