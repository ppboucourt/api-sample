(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('insurance-level', {
            parent: 'entity',
            url: '/insurance-level',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceLevels'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-level/insurance-levels.html',
                    controller: 'InsuranceLevelController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('insurance-level-detail', {
            parent: 'entity',
            url: '/insurance-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'InsuranceLevel'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/insurance-level/insurance-level-detail.html',
                    controller: 'InsuranceLevelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'InsuranceLevel', function($stateParams, InsuranceLevel) {
                    return InsuranceLevel.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'insurance-level',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('insurance-level-detail.edit', {
            parent: 'insurance-level-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-level/insurance-level-dialog.html',
                    controller: 'InsuranceLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceLevel', function(InsuranceLevel) {
                            return InsuranceLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-level.new', {
            parent: 'insurance-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-level/insurance-level-dialog.html',
                    controller: 'InsuranceLevelDialogController',
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
                    $state.go('insurance-level', null, { reload: 'insurance-level' });
                }, function() {
                    $state.go('insurance-level');
                });
            }]
        })
        .state('insurance-level.edit', {
            parent: 'insurance-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-level/insurance-level-dialog.html',
                    controller: 'InsuranceLevelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['InsuranceLevel', function(InsuranceLevel) {
                            return InsuranceLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-level', null, { reload: 'insurance-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('insurance-level.delete', {
            parent: 'insurance-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/insurance-level/insurance-level-delete-dialog.html',
                    controller: 'InsuranceLevelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['InsuranceLevel', function(InsuranceLevel) {
                            return InsuranceLevel.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('insurance-level', null, { reload: 'insurance-level' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
