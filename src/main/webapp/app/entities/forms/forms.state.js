(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('forms', {
                parent: 'entity',
                url: '/forms',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Forms'
                },
                ncyBreadcrumb: {
                    label: 'Form'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/forms/forms.html',
                        controller: 'FormsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('forms-detail', {
                parent: 'entity',
                url: '/forms/{id}',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Forms'
                },
                ncyBreadcrumb: {
                    label: 'Form Details',
                    parent: 'forms'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/forms/forms-detail.html',
                        controller: 'FormsDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Forms', function ($stateParams, Forms) {
                        return Forms.get({id: $stateParams.id}).$promise;
                    }]
                }
            })
            .state('forms-detail.edit', {
                parent: 'forms-detail',
                url: '/detail/edit',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/forms/forms-dialog.html',
                        controller: 'FormsDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Forms', function (Forms) {
                                return Forms.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('^', {}, {reload: false});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('forms.new', {
                parent: 'forms',
                url: '/new',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ]
                },
                ncyBreadcrumb: {
                    label: 'New Form',
                    parent: 'forms'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/forms/forms-dialog.html',
                        controller: 'FormsDialogController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: function () {
                        return {
                            name: null,
                            loadManually: false,
                            patientSignatureRequired: false,
                            guarantorSignatureRequired: false,
                            allowAttachment: false,
                            allowRevocation: false,
                            content: null,
                            status: 'ACT',
                            enabled: true,
                            requiredLabRequisitions: false,
                            expiresDays: null,
                            onlyOnePerpatient: false,
                            expires: false,
                            loadAutomatic: false,
                            id: null
                        };
                    },
                }
            })
            .state('forms.edit', {
                parent: 'forms',
                url: '/{id}/edit',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/forms/forms-dialog.html',
                        controller: 'FormsDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['Forms', function (Forms) {
                                return Forms.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('forms', null, {reload: 'forms'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('forms.delete', {
                parent: 'forms',
                url: '/{id}/delete',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/forms/forms-delete-dialog.html',
                        controller: 'FormsDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Forms', function (Forms) {
                                return Forms.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('forms', null, {reload: 'forms'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })
            .state('patient-forms', {
                parent: 'patient',
                url: '/forms',
                abstract: true,
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Patient-Forms'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/forms/forms-patient.html',
                        controller: 'FormsPatientController',
                        controllerAs: 'vm'
                    }
                    // 'form-invoice@patient-forms': {
                    //     templateUrl: 'app/entities/forms/view/form-invoice.html',
                    //     controller: 'FormInvoiceController',
                    //     controllerAs: 'vm'
                    // }
                }
            })
            .state('form-invoice', {
                parent: 'patient-forms',
                url: '/invoice',
                ncyBreadcrumb: {
                    label: 'Consent'
                },
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Invoice Form'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/forms/view/form-invoice.html',
                        controller: 'FormInvoiceController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    chartToForm: ['$sessionStorage', 'ChartToForm', function ($sessionStorage, ChartToForm) {
                        return ChartToForm.getVO({id: $sessionStorage.consentId});
                    }],
                    patient: ['$stateParams', 'Chart', function ($stateParams, Chart) {
                        return Chart.patientByChart({id: $stateParams.id}).$promise;
                    }]
                }
            }).state('evaluation-invoice', {
                parent: 'patient-forms',
                url: '/evaluationr/{eid}',
                ncyBreadcrumb: {
                    label: 'Evaluation',
                    parent: 'patient-forms'
                },
                data: {
                    backdrop: 'Evaluation',
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_PRIMARY_THERAPIST,
                        ROLES.ROLE_OTHER_THERAPIST,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_BHT,
                        ROLES.ROLE_LAB
                    ],
                    pageTitle: 'Evaluation Form'
                },
                views: {
                    'content@patient-abs': {
                        templateUrl: 'app/entities/forms/view/evaluation-invoice.html',
                        controller: 'EvaluationInvoiceController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$sessionStorage', 'Evaluation', function ($sessionStorage, Evaluation) {
                        return Evaluation.get({id: $sessionStorage.evaluationId}).$promise;
                    }],
                    patient: ['$stateParams', 'Chart', function ($stateParams, Chart) {
                        return Chart.patientByChart({id: $stateParams.id}).$promise;
                    }]
                }
            }).state('evaluation-viewinvoice', {
            parent: 'patient-abs',
            url: '/evaluationv/{eid}',
            ncyBreadcrumb: {
                label: 'Evaluation',
                parent: 'patient-forms'
            },
            data: {
                backdrop: 'Evaluation',
                authorities: [
                    ROLES.ROLE_SUPER_ADMIN,
                    ROLES.ROLE_PROGRAM_DIRECTOR,
                    ROLES.ROLE_CASE_MANAGER,
                    ROLES.ROLE_DIRECTOR_NURSE,
                    ROLES.ROLE_REGISTER_NURSE,
                    ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                    ROLES.ROLE_PRIMARY_THERAPIST,
                    ROLES.ROLE_OTHER_THERAPIST,
                    ROLES.ROLE_CLINICAL_DIRECTOR,
                    ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                    ROLES.ROLE_MD,
                    ROLES.ROLE_BHT,
                    ROLES.ROLE_LAB
                ],
                pageTitle: 'Evaluation Form'
            },
            views: {
                'content@patient-abs': {
                    templateUrl: 'app/entities/forms/view/evaluation-viewinvoice.html',
                    controller: 'EvaluationViewInvoiceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Evaluation', function ($stateParams, Evaluation) {
                    return Evaluation.get({id: $stateParams.eid});
                }],
                patient: ['$stateParams', 'Chart', function ($stateParams, Chart) {
                    return Chart.patientByChart({id: $stateParams.id}).$promise;
                }]
            }
        });
    }

})();
