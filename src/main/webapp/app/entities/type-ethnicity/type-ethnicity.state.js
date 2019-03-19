(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-ethnicity', {
            parent: 'entity',
            url: '/type-ethnicity',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEthnicities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicities.html',
                    controller: 'TypeEthnicityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-ethnicity-detail', {
            parent: 'entity',
            url: '/type-ethnicity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeEthnicity'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicity-detail.html',
                    controller: 'TypeEthnicityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeEthnicity', function($stateParams, TypeEthnicity) {
                    return TypeEthnicity.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-ethnicity',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-ethnicity-detail.edit', {
            parent: 'type-ethnicity-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicity-dialog.html',
                    controller: 'TypeEthnicityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEthnicity', function(TypeEthnicity) {
                            return TypeEthnicity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-ethnicity.new', {
            parent: 'type-ethnicity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicity-dialog.html',
                    controller: 'TypeEthnicityDialogController',
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
                    $state.go('type-ethnicity', null, { reload: 'type-ethnicity' });
                }, function() {
                    $state.go('type-ethnicity');
                });
            }]
        })
        .state('type-ethnicity.edit', {
            parent: 'type-ethnicity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicity-dialog.html',
                    controller: 'TypeEthnicityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeEthnicity', function(TypeEthnicity) {
                            return TypeEthnicity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-ethnicity', null, { reload: 'type-ethnicity' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-ethnicity.delete', {
            parent: 'type-ethnicity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-ethnicity/type-ethnicity-delete-dialog.html',
                    controller: 'TypeEthnicityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeEthnicity', function(TypeEthnicity) {
                            return TypeEthnicity.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-ethnicity', null, { reload: 'type-ethnicity' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
