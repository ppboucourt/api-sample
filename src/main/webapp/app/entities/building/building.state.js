(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('building', {
            parent: 'entity',
            url: '/building',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Buildings'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/buildings.html',
                    controller: 'BuildingController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('building-detail', {
            parent: 'entity',
            url: '/building/{id}',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Building'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/building/building-detail.html',
                    controller: 'BuildingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Building', function($stateParams, Building) {
                    return Building.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'building',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('building-detail.edit', {
            parent: 'building-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building.new', {
            parent: 'building',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
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
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('building');
                });
            }]
        })
        .state('building.edit', {
            parent: 'building',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-dialog.html',
                    controller: 'BuildingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('building.delete', {
            parent: 'building',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/building/building-delete-dialog.html',
                    controller: 'BuildingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Building', function(Building) {
                            return Building.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('building', null, { reload: 'building' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
