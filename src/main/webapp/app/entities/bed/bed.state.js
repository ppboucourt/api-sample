(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('bed', {
            parent: 'entity',
            url: '/bed',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Beds'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bed/beds.html',
                    controller: 'BedController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('bed-detail', {
            parent: 'entity',
            url: '/bed/{id}',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'Bed'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/bed/bed-detail.html',
                    controller: 'BedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Bed', function($stateParams, Bed) {
                    return Bed.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'bed',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('bed-detail.edit', {
            parent: 'bed-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bed/bed-dialog.html',
                    controller: 'BedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bed', function(Bed) {
                            return Bed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bed.new', {
            parent: 'bed',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bed/bed-dialog.html',
                    controller: 'BedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                notes: null,
                                status: null,
                                actualChart: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('bed', null, { reload: 'bed' });
                }, function() {
                    $state.go('bed');
                });
            }]
        })
        .state('bed.edit', {
            parent: 'bed',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bed/bed-dialog.html',
                    controller: 'BedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Bed', function(Bed) {
                            return Bed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bed', null, { reload: 'bed' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('bed.delete', {
            parent: 'bed',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/bed/bed-delete-dialog.html',
                    controller: 'BedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Bed', function(Bed) {
                            return Bed.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('bed', null, { reload: 'bed' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
