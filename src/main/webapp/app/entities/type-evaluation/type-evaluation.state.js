(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-evaluation', {
            parent: 'entity',
            url: '/type-evaluation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEvaluations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-evaluation/type-evaluations.html',
                    controller: 'TypeEvaluationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-evaluation-detail', {
            parent: 'entity',
            url: '/type-evaluation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEvaluation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-evaluation/type-evaluation-detail.html',
                    controller: 'TypeEvaluationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeEvaluation', function($stateParams, TypeEvaluation) {
                    return TypeEvaluation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-evaluation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-evaluation-detail.edit', {
            parent: 'type-evaluation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-evaluation/type-evaluation-dialog.html',
                    controller: 'TypeEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEvaluation', function(TypeEvaluation) {
                            return TypeEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-evaluation.new', {
            parent: 'type-evaluation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-evaluation/type-evaluation-dialog.html',
                    controller: 'TypeEvaluationDialogController',
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
                    $state.go('type-evaluation', null, { reload: 'type-evaluation' });
                }, function() {
                    $state.go('type-evaluation');
                });
            }]
        })
        .state('type-evaluation.edit', {
            parent: 'type-evaluation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-evaluation/type-evaluation-dialog.html',
                    controller: 'TypeEvaluationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEvaluation', function(TypeEvaluation) {
                            return TypeEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-evaluation', null, { reload: 'type-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-evaluation.delete', {
            parent: 'type-evaluation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-evaluation/type-evaluation-delete-dialog.html',
                    controller: 'TypeEvaluationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeEvaluation', function(TypeEvaluation) {
                            return TypeEvaluation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-evaluation', null, { reload: 'type-evaluation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
