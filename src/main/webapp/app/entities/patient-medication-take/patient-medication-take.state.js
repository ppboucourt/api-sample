(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-medication-take', {
            parent: 'entity',
            url: '/patient-medication-take',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientMedicationTakes'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-takes.html',
                    controller: 'PatientMedicationTakeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-medication-take-detail', {
            parent: 'entity',
            url: '/patient-medication-take/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientMedicationTake'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-take-detail.html',
                    controller: 'PatientMedicationTakeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientMedicationTake', function($stateParams, PatientMedicationTake) {
                    return PatientMedicationTake.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-medication-take',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-medication-take-detail.edit', {
            parent: 'patient-medication-take-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-take-dialog.html',
                    controller: 'PatientMedicationTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientMedicationTake', function(PatientMedicationTake) {
                            return PatientMedicationTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-medication-take.new', {
            parent: 'patient-medication-take',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-take-dialog.html',
                    controller: 'PatientMedicationTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                collected: null,
                                collectedDate: null,
                                scheduleDate: null,
                                sent: null,
                                sentDate: null,
                                canceled: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-medication-take', null, { reload: 'patient-medication-take' });
                }, function() {
                    $state.go('patient-medication-take');
                });
            }]
        })
        .state('patient-medication-take.edit', {
            parent: 'patient-medication-take',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-take-dialog.html',
                    controller: 'PatientMedicationTakeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientMedicationTake', function(PatientMedicationTake) {
                            return PatientMedicationTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-medication-take', null, { reload: 'patient-medication-take' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-medication-take.delete', {
            parent: 'patient-medication-take',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-take/patient-medication-take-delete-dialog.html',
                    controller: 'PatientMedicationTakeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientMedicationTake', function(PatientMedicationTake) {
                            return PatientMedicationTake.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-medication-take', null, { reload: 'patient-medication-take' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
