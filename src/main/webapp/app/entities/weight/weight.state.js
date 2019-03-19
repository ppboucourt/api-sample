(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('weight', {
            parent: 'entity',
            url: '/weight',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Weights'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weight/weights.html',
                    controller: 'WeightController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('weight-detail', {
            parent: 'entity',
            url: '/weight/{id}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Weight'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/weight/weight-detail.html',
                    controller: 'WeightDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Weight', function($stateParams, Weight) {
                    return Weight.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'weight',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('weight-detail.edit', {
            parent: 'weight-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weight/weight-dialog.html',
                    controller: 'WeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weight', function(Weight) {
                            return Weight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('patient-mars.weight', {
            parent: 'patient-mars',
            url: '/weight',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weight/weight-dialog.html',
                    controller: 'WeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                weight: null,
                                height: null,
                                bmi: null,
                                status: null,
                                id: null,
                                date:null,
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
        .state('weight.edit', {
            parent: 'weight',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weight/weight-dialog.html',
                    controller: 'WeightDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Weight', function(Weight) {
                            return Weight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weight', null, { reload: 'weight' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('weight.delete', {
            parent: 'weight',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/weight/weight-delete-dialog.html',
                    controller: 'WeightDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Weight', function(Weight) {
                            return Weight.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('weight', null, { reload: 'weight' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
