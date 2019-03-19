(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('patient-medication-pres', {
            parent: 'entity',
            url: '/patient-medication-pres',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientMedicationPres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres.html',
                    controller: 'PatientMedicationPresController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('patient-medication-pres-detail', {
            parent: 'entity',
            url: '/patient-medication-pres/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'PatientMedicationPres'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-detail.html',
                    controller: 'PatientMedicationPresDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'PatientMedicationPres', function($stateParams, PatientMedicationPres) {
                    return PatientMedicationPres.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'patient-medication-pres',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('patient-medication-pres-detail.edit', {
            parent: 'patient-medication-pres-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-dialog.html',
                    controller: 'PatientMedicationPresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientMedicationPres', function(PatientMedicationPres) {
                            return PatientMedicationPres.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-medication-pres.new', {
            parent: 'patient-medication-pres',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-dialog.html',
                    controller: 'PatientMedicationPresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                staringDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-medication-pres', null, { reload: 'patient-medication-pres' });
                }, function() {
                    $state.go('patient-medication-pres');
                });
            }]
        })
        .state('patient-medication-pres.edit', {
            parent: 'patient-medication-pres',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-dialog.html',
                    controller: 'PatientMedicationPresDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PatientMedicationPres', function(PatientMedicationPres) {
                            return PatientMedicationPres.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-medication-pres', null, { reload: 'patient-medication-pres' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-medication-pres.delete', {
            parent: 'patient-medication-pres',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/patient-medication-pres/patient-medication-pres-delete-dialog.html',
                    controller: 'PatientMedicationPresDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PatientMedicationPres', function(PatientMedicationPres) {
                            return PatientMedicationPres.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('patient-medication-pres', null, { reload: 'patient-medication-pres' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
