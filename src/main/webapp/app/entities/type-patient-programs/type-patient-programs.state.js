(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-patient-programs', {
            parent: 'entity',
            url: '/type-patient-programs',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientPrograms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs.html',
                    controller: 'TypePatientProgramsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-patient-programs-detail', {
            parent: 'entity',
            url: '/type-patient-programs/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'TypePatientPrograms'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs-detail.html',
                    controller: 'TypePatientProgramsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePatientPrograms', function($stateParams, TypePatientPrograms) {
                    return TypePatientPrograms.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-patient-programs',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-patient-programs-detail.edit', {
            parent: 'type-patient-programs-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs-dialog.html',
                    controller: 'TypePatientProgramsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientPrograms', function(TypePatientPrograms) {
                            return TypePatientPrograms.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-programs.new', {
            parent: 'type-patient-programs',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs-dialog.html',
                    controller: 'TypePatientProgramsDialogController',
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
                    $state.go('type-patient-programs', null, { reload: 'type-patient-programs' });
                }, function() {
                    $state.go('type-patient-programs');
                });
            }]
        })
        .state('type-patient-programs.edit', {
            parent: 'type-patient-programs',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs-dialog.html',
                    controller: 'TypePatientProgramsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientPrograms', function(TypePatientPrograms) {
                            return TypePatientPrograms.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-programs', null, { reload: 'type-patient-programs' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-programs.delete', {
            parent: 'type-patient-programs',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-programs/type-patient-programs-delete-dialog.html',
                    controller: 'TypePatientProgramsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePatientPrograms', function(TypePatientPrograms) {
                            return TypePatientPrograms.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-programs', null, { reload: 'type-patient-programs' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
