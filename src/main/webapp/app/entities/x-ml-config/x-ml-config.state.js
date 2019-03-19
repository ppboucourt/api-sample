(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('x-ml-config', {
            parent: 'entity',
            url: '/x-ml-config',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'XMLConfigs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/x-ml-config/x-ml-configs.html',
                    controller: 'XMLConfigController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('x-ml-config-detail', {
            parent: 'entity',
            url: '/x-ml-config/{id}',
            data: {
                authorities: ['ROLE_ADMIN'],
                pageTitle: 'XMLConfig'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/x-ml-config/x-ml-config-detail.html',
                    controller: 'XMLConfigDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'XMLConfig', function($stateParams, XMLConfig) {
                    return XMLConfig.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'x-ml-config',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('x-ml-config-detail.edit', {
            parent: 'x-ml-config-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/x-ml-config/x-ml-config-dialog.html',
                    controller: 'XMLConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['XMLConfig', function(XMLConfig) {
                            return XMLConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('x-ml-config.new', {
            parent: 'x-ml-config',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/x-ml-config/x-ml-config-dialog.html',
                    controller: 'XMLConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                file: null,
                                fileContentType: null,
                                xType: null,
                                byDefault: false,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('x-ml-config', null, { reload: 'x-ml-config' });
                }, function() {
                    $state.go('x-ml-config');
                });
            }]
        })
        .state('x-ml-config.edit', {
            parent: 'x-ml-config',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/x-ml-config/x-ml-config-dialog.html',
                    controller: 'XMLConfigDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['XMLConfig', function(XMLConfig) {
                            return XMLConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('x-ml-config', null, { reload: 'x-ml-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('x-ml-config.delete', {
            parent: 'x-ml-config',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/x-ml-config/x-ml-config-delete-dialog.html',
                    controller: 'XMLConfigDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['XMLConfig', function(XMLConfig) {
                            return XMLConfig.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('x-ml-config', null, { reload: 'x-ml-config' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
