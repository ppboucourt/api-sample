(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-page', {
            parent: 'entity',
            url: '/type-page',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePages'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-page/type-pages.html',
                    controller: 'TypePageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-page-detail', {
            parent: 'entity',
            url: '/type-page/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePage'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-page/type-page-detail.html',
                    controller: 'TypePageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePage', function($stateParams, TypePage) {
                    return TypePage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-page',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-page-detail.edit', {
            parent: 'type-page-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-page/type-page-dialog.html',
                    controller: 'TypePageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePage', function(TypePage) {
                            return TypePage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-page.new', {
            parent: 'type-page',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-page/type-page-dialog.html',
                    controller: 'TypePageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-page', null, { reload: 'type-page' });
                }, function() {
                    $state.go('type-page');
                });
            }]
        })
        .state('type-page.edit', {
            parent: 'type-page',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-page/type-page-dialog.html',
                    controller: 'TypePageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePage', function(TypePage) {
                            return TypePage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-page', null, { reload: 'type-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-page.delete', {
            parent: 'type-page',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-page/type-page-delete-dialog.html',
                    controller: 'TypePageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePage', function(TypePage) {
                            return TypePage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-page', null, { reload: 'type-page' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
