(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evaluation-items', {
            parent: 'entity',
            url: '/evaluation-items',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EvaluationItems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-items/evaluation-items.html',
                    controller: 'EvaluationItemsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('evaluation-items-detail', {
            parent: 'entity',
            url: '/evaluation-items/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EvaluationItems'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-items/evaluation-items-detail.html',
                    controller: 'EvaluationItemsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EvaluationItems', function($stateParams, EvaluationItems) {
                    return EvaluationItems.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evaluation-items',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evaluation-items-detail.edit', {
            parent: 'evaluation-items-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-items/evaluation-items-dialog.html',
                    controller: 'EvaluationItemsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationItems', function(EvaluationItems) {
                            return EvaluationItems.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-items.new', {
            parent: 'evaluation-items',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-items/evaluation-items-dialog.html',
                    controller: 'EvaluationItemsDialogController',
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
                    $state.go('evaluation-items', null, { reload: 'evaluation-items' });
                }, function() {
                    $state.go('evaluation-items');
                });
            }]
        })
        .state('evaluation-items.edit', {
            parent: 'evaluation-items',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-items/evaluation-items-dialog.html',
                    controller: 'EvaluationItemsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationItems', function(EvaluationItems) {
                            return EvaluationItems.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-items', null, { reload: 'evaluation-items' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-items.delete', {
            parent: 'evaluation-items',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-items/evaluation-items-delete-dialog.html',
                    controller: 'EvaluationItemsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EvaluationItems', function(EvaluationItems) {
                            return EvaluationItems.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-items', null, { reload: 'evaluation-items' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
