(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('via', {
            parent: 'entity',
            url: '/via',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Vias'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/via/vias.html',
                    controller: 'ViaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('via-detail', {
            parent: 'entity',
            url: '/via/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Via'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/via/via-detail.html',
                    controller: 'ViaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Via', function($stateParams, Via) {
                    return Via.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'via',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('via-detail.edit', {
            parent: 'via-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/via/via-dialog.html',
                    controller: 'ViaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Via', function(Via) {
                            return Via.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('via.new', {
            parent: 'via',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/via/via-dialog.html',
                    controller: 'ViaDialogController',
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
                    $state.go('via', null, { reload: 'via' });
                }, function() {
                    $state.go('via');
                });
            }]
        })
        .state('via.edit', {
            parent: 'via',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/via/via-dialog.html',
                    controller: 'ViaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Via', function(Via) {
                            return Via.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('via', null, { reload: 'via' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('via.delete', {
            parent: 'via',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/via/via-delete-dialog.html',
                    controller: 'ViaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Via', function(Via) {
                            return Via.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('via', null, { reload: 'via' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
