(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('evaluation-template', {
                parent: 'entity',
                url: '/evaluation-template',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'EvaluationTemplates'
                },
                ncyBreadcrumb: {
                    label: 'Evaluations'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evaluation-template/evaluation-templates.html',
                        controller: 'EvaluationTemplateController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            .state('evaluation-template-detail', {
                parent: 'entity',
                url: '/evaluation-template/{id}',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE
                    ],
                    pageTitle: 'EvaluationTemplate'
                },
                ncyBreadcrumb: {
                    label: 'Detail Evaluation',
                    parent: 'evaluation-template'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-form.html',
                        controller: 'EvaluationTemplateFormController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['EvaluationTemplate', '$stateParams', function(EvaluationTemplate, $stateParams) {
                        return EvaluationTemplate.get({id : $stateParams.id}).$promise;
                    }]
                }
            })

            .state('evaluation-template.new', {
                parent: 'evaluation-template',
                url: '/new',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE
                    ]
                },
                ncyBreadcrumb: {
                    label: 'New Evaluation',
                    parent: 'evaluation-template'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-form.html',
                        controller: 'EvaluationTemplateFormController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            enabled: false,
                            patientSignature: false,
                            onlyOne: false,
                            billable: false,
                            forceStaffSignature: false,
                            forceStaffReviewSignature: false,
                            staffSignatureAuthority:[],
                            staffReviewSignatureAuthorities: [],
                            loadAutomatic: false,
                            jsonTemplate: {},
                            id: null
                        };
                    }
                }
            })

            .state('evaluation-template.delete', {
                parent: 'evaluation-template',
                url: '/{id}/delete',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE
                    ]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/evaluation-template/evaluation-template-delete-dialog.html',
                        controller: 'EvaluationTemplateDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['EvaluationTemplate', function(EvaluationTemplate) {
                                return EvaluationTemplate.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('evaluation-template', null, { reload: 'evaluation-template' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }

})();
