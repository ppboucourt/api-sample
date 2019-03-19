(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-type', {
            parent: 'entity',
            url: '/insurance-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceTypes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-type/insurance-types.html',
                    controller: 'InsuranceTypeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-type-detail', {
            parent: 'entity',
            url: '/insurance-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceType'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-type/insurance-type-detail.html',
                    controller: 'InsuranceTypeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceType', function($stateParams, InsuranceType) {
                    return InsuranceType.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-type-detail.edit', {
            parent: 'insurance-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-type/insurance-type-dialog.html',
                    controller: 'InsuranceTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceType', function(InsuranceType) {
                            return InsuranceType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-type.new', {
            parent: 'insurance-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-type/insurance-type-dialog.html',
                    controller: 'InsuranceTypeDialogController',
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
                    $state.go('insurance-type', null, { reload: 'insurance-type' });
                }, function() {
                    $state.go('insurance-type');
                });
            }]
        })
        .state('insurance-type.edit', {
            parent: 'insurance-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-type/insurance-type-dialog.html',
                    controller: 'InsuranceTypeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceType', function(InsuranceType) {
                            return InsuranceType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-type', null, { reload: 'insurance-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-type.delete', {
            parent: 'insurance-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-type/insurance-type-delete-dialog.html',
                    controller: 'InsuranceTypeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceType', function(InsuranceType) {
                            return InsuranceType.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-type', null, { reload: 'insurance-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
