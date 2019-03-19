(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('vitals', {
            parent: 'entity',
            url: '/vitals',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Vitals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vitals/vitals.html',
                    controller: 'VitalsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('vitals-detail', {
            parent: 'entity',
            url: '/vitals/{id}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Vitals'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/vitals/vitals-detail.html',
                    controller: 'VitalsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Vitals', function($stateParams, Vitals) {
                    return Vitals.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'vitals',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('vitals-detail.edit', {
            parent: 'vitals-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vitals/vitals-dialog.html',
                    controller: 'VitalsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vitals', function(Vitals) {
                            return Vitals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-mars.vitals', {
            parent: 'patient-mars',
            url: '/vitals',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vitals/vitals-dialog.html',
                    controller: 'VitalsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                time: null,
                                bp_systolic: null,
                                bp_diastolic: null,
                                temperature: null,
                                pulse: null,
                                respiration: null,
                                o2_saturation: null,
                                status: null,
                                id: null,
                                chartId: $stateParams.id
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('patient-mars', null, { reload: 'patient-mars' });
                }, function() {
                    $state.go('patient-mars');
                });
            }]
        })
        .state('vitals.edit', {
            parent: 'vitals',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vitals/vitals-dialog.html',
                    controller: 'VitalsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Vitals', function(Vitals) {
                            return Vitals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vitals', null, { reload: 'vitals' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('vitals.delete', {
            parent: 'vitals',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/vitals/vitals-delete-dialog.html',
                    controller: 'VitalsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Vitals', function(Vitals) {
                            return Vitals.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('vitals', null, { reload: 'vitals' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
