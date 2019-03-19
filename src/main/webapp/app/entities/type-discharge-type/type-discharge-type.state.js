(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-discharge-type', {
            parent: 'entity',
            url: '/type-discharge-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeDischargeTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-types.html',
                    controller: 'TypeDischargeTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-discharge-type-detail', {
            parent: 'entity',
            url: '/type-discharge-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeDischargeType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-type-detail.html',
                    controller: 'TypeDischargeTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeDischargeType', function($stateParams, TypeDischargeType) {
                    return TypeDischargeType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-discharge-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-discharge-type-detail.edit', {
            parent: 'type-discharge-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-type-dialog.html',
                    controller: 'TypeDischargeTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeDischargeType', function(TypeDischargeType) {
                            return TypeDischargeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-discharge-type.new', {
            parent: 'type-discharge-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-type-dialog.html',
                    controller: 'TypeDischargeTypeDialogController',
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
                    $state.go('type-discharge-type', null, { reload: 'type-discharge-type' });
                }, function() {
                    $state.go('type-discharge-type');
                });
            }]
        })
        .state('type-discharge-type.edit', {
            parent: 'type-discharge-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-type-dialog.html',
                    controller: 'TypeDischargeTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeDischargeType', function(TypeDischargeType) {
                            return TypeDischargeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-discharge-type', null, { reload: 'type-discharge-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-discharge-type.delete', {
            parent: 'type-discharge-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-discharge-type/type-discharge-type-delete-dialog.html',
                    controller: 'TypeDischargeTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeDischargeType', function(TypeDischargeType) {
                            return TypeDischargeType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-discharge-type', null, { reload: 'type-discharge-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
