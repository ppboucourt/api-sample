(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('actions', {
            parent: 'entity',
            url: '/actions',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Actions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/actions/actions.html',
                    controller: 'ActionsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('actions-detail', {
            parent: 'entity',
            url: '/actions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Actions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/actions/actions-detail.html',
                    controller: 'ActionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Actions', function($stateParams, Actions) {
                    return Actions.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'actions',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('actions-detail.edit', {
            parent: 'actions-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actions/actions-dialog.html',
                    controller: 'ActionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Actions', function(Actions) {
                            return Actions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('actions.new', {
            parent: 'actions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actions/actions-dialog.html',
                    controller: 'ActionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                action: null,
                                show_in_mars: null,
                                prn: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('actions', null, { reload: 'actions' });
                }, function() {
                    $state.go('actions');
                });
            }]
        })
        .state('actions.edit', {
            parent: 'actions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actions/actions-dialog.html',
                    controller: 'ActionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Actions', function(Actions) {
                            return Actions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('actions', null, { reload: 'actions' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('actions.delete', {
            parent: 'actions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/actions/actions-delete-dialog.html',
                    controller: 'ActionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Actions', function(Actions) {
                            return Actions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('actions', null, { reload: 'actions' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
