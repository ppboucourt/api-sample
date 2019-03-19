(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('evaluation-content', {
            parent: 'entity',
            url: '/evaluation-content',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EvaluationContents'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-content/evaluation-contents.html',
                    controller: 'EvaluationContentController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('evaluation-content-detail', {
            parent: 'entity',
            url: '/evaluation-content/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'EvaluationContent'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-content/evaluation-content-detail.html',
                    controller: 'EvaluationContentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EvaluationContent', function($stateParams, EvaluationContent) {
                    return EvaluationContent.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evaluation-content',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evaluation-content-detail.edit', {
            parent: 'evaluation-content-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-content/evaluation-content-dialog.html',
                    controller: 'EvaluationContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationContent', function(EvaluationContent) {
                            return EvaluationContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-content.new', {
            parent: 'evaluation-content',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-content/evaluation-content-dialog.html',
                    controller: 'EvaluationContentDialogController',
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
                    $state.go('evaluation-content', null, { reload: 'evaluation-content' });
                }, function() {
                    $state.go('evaluation-content');
                });
            }]
        })
        .state('evaluation-content.edit', {
            parent: 'evaluation-content',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-content/evaluation-content-dialog.html',
                    controller: 'EvaluationContentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationContent', function(EvaluationContent) {
                            return EvaluationContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-content', null, { reload: 'evaluation-content' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-content.delete', {
            parent: 'evaluation-content',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-content/evaluation-content-delete-dialog.html',
                    controller: 'EvaluationContentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EvaluationContent', function(EvaluationContent) {
                            return EvaluationContent.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-content', null, { reload: 'evaluation-content' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
