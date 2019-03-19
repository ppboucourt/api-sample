(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('evaluation-signature', {
            parent: 'entity',
            url: '/evaluation-signature',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT],
                pageTitle: 'EvaluationSignatures'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signatures.html',
                    controller: 'EvaluationSignatureController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('evaluation-signature-detail', {
            parent: 'entity',
            url: '/evaluation-signature/{id}',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT],
                pageTitle: 'EvaluationSignature'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signature-detail.html',
                    controller: 'EvaluationSignatureDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'EvaluationSignature', function($stateParams, EvaluationSignature) {
                    return EvaluationSignature.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'evaluation-signature',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('evaluation-signature-detail.edit', {
            parent: 'evaluation-signature-detail',
            url: '/detail/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signature-dialog.html',
                    controller: 'EvaluationSignatureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationSignature', function(EvaluationSignature) {
                            return EvaluationSignature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-signature.new', {
            parent: 'evaluation-signature',
            url: '/new',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signature-dialog.html',
                    controller: 'EvaluationSignatureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                role: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('evaluation-signature', null, { reload: 'evaluation-signature' });
                }, function() {
                    $state.go('evaluation-signature');
                });
            }]
        })
        .state('evaluation-signature.edit', {
            parent: 'evaluation-signature',
            url: '/{id}/edit',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signature-dialog.html',
                    controller: 'EvaluationSignatureDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EvaluationSignature', function(EvaluationSignature) {
                            return EvaluationSignature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-signature', null, { reload: 'evaluation-signature' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('evaluation-signature.delete', {
            parent: 'evaluation-signature',
            url: '/{id}/delete',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/evaluation-signature/evaluation-signature-delete-dialog.html',
                    controller: 'EvaluationSignatureDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EvaluationSignature', function(EvaluationSignature) {
                            return EvaluationSignature.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('evaluation-signature', null, { reload: 'evaluation-signature' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
