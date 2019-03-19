(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-medication-routes', {
            parent: 'entity',
            url: '/type-medication-routes',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMedicationRoutes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes.html',
                    controller: 'TypeMedicationRoutesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-medication-routes-detail', {
            parent: 'entity',
            url: '/type-medication-routes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypeMedicationRoutes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes-detail.html',
                    controller: 'TypeMedicationRoutesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypeMedicationRoutes', function($stateParams, TypeMedicationRoutes) {
                    return TypeMedicationRoutes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-medication-routes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-medication-routes-detail.edit', {
            parent: 'type-medication-routes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes-dialog.html',
                    controller: 'TypeMedicationRoutesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMedicationRoutes', function(TypeMedicationRoutes) {
                            return TypeMedicationRoutes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-medication-routes.new', {
            parent: 'type-medication-routes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes-dialog.html',
                    controller: 'TypeMedicationRoutesDialogController',
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
                    $state.go('type-medication-routes', null, { reload: 'type-medication-routes' });
                }, function() {
                    $state.go('type-medication-routes');
                });
            }]
        })
        .state('type-medication-routes.edit', {
            parent: 'type-medication-routes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes-dialog.html',
                    controller: 'TypeMedicationRoutesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypeMedicationRoutes', function(TypeMedicationRoutes) {
                            return TypeMedicationRoutes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-medication-routes', null, { reload: 'type-medication-routes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-medication-routes.delete', {
            parent: 'type-medication-routes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-medication-routes/type-medication-routes-delete-dialog.html',
                    controller: 'TypeMedicationRoutesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypeMedicationRoutes', function(TypeMedicationRoutes) {
                            return TypeMedicationRoutes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-medication-routes', null, { reload: 'type-medication-routes' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
