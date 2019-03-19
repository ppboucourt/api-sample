(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-level-care', {
            parent: 'entity',
            url: '/type-level-care',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeLevelCares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-level-care/type-level-cares.html',
                    controller: 'TypeLevelCareController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-level-care-detail', {
            parent: 'entity',
            url: '/type-level-care/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeLevelCare'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-level-care/type-level-care-detail.html',
                    controller: 'TypeLevelCareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeLevelCare', function($stateParams, TypeLevelCare) {
                    return TypeLevelCare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-level-care',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-level-care-detail.edit', {
            parent: 'type-level-care-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-level-care/type-level-care-dialog.html',
                    controller: 'TypeLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeLevelCare', function(TypeLevelCare) {
                            return TypeLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-level-care.new', {
            parent: 'type-level-care',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-level-care/type-level-care-dialog.html',
                    controller: 'TypeLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-level-care', null, { reload: 'type-level-care' });
                }, function() {
                    $state.go('type-level-care');
                });
            }]
        })
        .state('type-level-care.edit', {
            parent: 'type-level-care',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-level-care/type-level-care-dialog.html',
                    controller: 'TypeLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeLevelCare', function(TypeLevelCare) {
                            return TypeLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-level-care', null, { reload: 'type-level-care' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-level-care.delete', {
            parent: 'type-level-care',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-level-care/type-level-care-delete-dialog.html',
                    controller: 'TypeLevelCareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeLevelCare', function(TypeLevelCare) {
                            return TypeLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-level-care', null, { reload: 'type-level-care' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
