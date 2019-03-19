(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('type-patient-process', {
            parent: 'entity',
            url: '/type-patient-process',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'TypePatientProcesses'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-process/type-patient-processes.html',
                    controller: 'TypePatientProcessController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('type-patient-process-detail', {
            parent: 'entity',
            url: '/type-patient-process/{id}',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                pageTitle: 'TypePatientProcess'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-patient-process/type-patient-process-detail.html',
                    controller: 'TypePatientProcessDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'TypePatientProcess', function($stateParams, TypePatientProcess) {
                    return TypePatientProcess.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'type-patient-process',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('type-patient-process-detail.edit', {
            parent: 'type-patient-process-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-process/type-patient-process-dialog.html',
                    controller: 'TypePatientProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientProcess', function(TypePatientProcess) {
                            return TypePatientProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-process.new', {
            parent: 'type-patient-process',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-process/type-patient-process-dialog.html',
                    controller: 'TypePatientProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                allow_ur: false,
                                protect: false,
                                status: 'ACT',
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-patient-process', null, { reload: 'type-patient-process' });
                }, function() {
                    $state.go('type-patient-process');
                });
            }]
        })
        .state('type-patient-process.edit', {
            parent: 'type-patient-process',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-process/type-patient-process-dialog.html',
                    controller: 'TypePatientProcessDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TypePatientProcess', function(TypePatientProcess) {
                            return TypePatientProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-process', null, { reload: 'type-patient-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-patient-process.delete', {
            parent: 'type-patient-process',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_ADMIN, ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-patient-process/type-patient-process-delete-dialog.html',
                    controller: 'TypePatientProcessDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TypePatientProcess', function(TypePatientProcess) {
                            return TypePatientProcess.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-patient-process', null, { reload: 'type-patient-process' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
