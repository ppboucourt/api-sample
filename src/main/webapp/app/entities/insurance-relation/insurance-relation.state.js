(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-relation', {
            parent: 'entity',
            url: '/insurance-relation',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceRelations'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-relation/insurance-relations.html',
                    controller: 'InsuranceRelationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-relation-detail', {
            parent: 'entity',
            url: '/insurance-relation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceRelation'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-relation/insurance-relation-detail.html',
                    controller: 'InsuranceRelationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceRelation', function($stateParams, InsuranceRelation) {
                    return InsuranceRelation.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-relation',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-relation-detail.edit', {
            parent: 'insurance-relation-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-relation/insurance-relation-dialog.html',
                    controller: 'InsuranceRelationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceRelation', function(InsuranceRelation) {
                            return InsuranceRelation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-relation.new', {
            parent: 'insurance-relation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-relation/insurance-relation-dialog.html',
                    controller: 'InsuranceRelationDialogController',
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
                    $state.go('insurance-relation', null, { reload: 'insurance-relation' });
                }, function() {
                    $state.go('insurance-relation');
                });
            }]
        })
        .state('insurance-relation.edit', {
            parent: 'insurance-relation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-relation/insurance-relation-dialog.html',
                    controller: 'InsuranceRelationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceRelation', function(InsuranceRelation) {
                            return InsuranceRelation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-relation', null, { reload: 'insurance-relation' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-relation.delete', {
            parent: 'insurance-relation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-relation/insurance-relation-delete-dialog.html',
                    controller: 'InsuranceRelationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceRelation', function(InsuranceRelation) {
                            return InsuranceRelation.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-relation', null, { reload: 'insurance-relation' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
