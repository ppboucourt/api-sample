(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-form-rules', {
            parent: 'entity',
            url: '/type-form-rules',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeFormRules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-form-rules/type-form-rules.html',
                    controller: 'TypeFormRulesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-form-rules-detail', {
            parent: 'entity',
            url: '/type-form-rules/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeFormRules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-form-rules/type-form-rules-detail.html',
                    controller: 'TypeFormRulesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeFormRules', function($stateParams, TypeFormRules) {
                    return TypeFormRules.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-form-rules',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-form-rules-detail.edit', {
            parent: 'type-form-rules-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-form-rules/type-form-rules-dialog.html',
                    controller: 'TypeFormRulesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeFormRules', function(TypeFormRules) {
                            return TypeFormRules.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-form-rules.new', {
            parent: 'type-form-rules',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-form-rules/type-form-rules-dialog.html',
                    controller: 'TypeFormRulesDialogController',
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
                    $state.go('type-form-rules', null, { reload: 'type-form-rules' });
                }, function() {
                    $state.go('type-form-rules');
                });
            }]
        })
        .state('type-form-rules.edit', {
            parent: 'type-form-rules',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-form-rules/type-form-rules-dialog.html',
                    controller: 'TypeFormRulesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeFormRules', function(TypeFormRules) {
                            return TypeFormRules.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-form-rules', null, { reload: 'type-form-rules' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-form-rules.delete', {
            parent: 'type-form-rules',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-form-rules/type-form-rules-delete-dialog.html',
                    controller: 'TypeFormRulesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeFormRules', function(TypeFormRules) {
                            return TypeFormRules.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-form-rules', null, { reload: 'type-form-rules' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
