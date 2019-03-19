(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('lab-results', {
                parent: 'entity',
                url: '/lab-results',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Lab Results'
                },
                ncyBreadcrumb: {
                    label: 'Lab Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-latest.html',
                        controller: 'PatientResultLatestController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    facility: ['CoreService', function (CoreService) {
                        return CoreService.getCurrentFacility();
                    }],
                    filterRes: function () {
                        return {
                            patientName: null,
                            start: null,
                            end: null,
                            status: null
                        }
                    }
                }
            })
            .state('patient-result', {
                parent: 'patient-abs',
                url: '/platest-result',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Patient Results'
                },
                ncyBreadcrumb: {
                    label: 'Results',
                    parent: 'patient-detail'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-by-patient.html',
                        controller: 'PatientResultByPatientController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            patientName: null,
                            start: null,
                            end: null,
                            abnormal: null,
                            status: null
                        }
                    }
                }
            })
            .state('dashboard-final-result', {
                parent: 'entity',
                url: '/dfresult',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Final Results'
                },
                ncyBreadcrumb: {
                    label: 'Final Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-final-partial-dashboard.html',
                        controller: 'DashboardResultController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            status: 'final',
                            title: 'Final Results'
                        }
                    }
                }
            })
            .state('dashboard-partial-result', {
                parent: 'entity',
                url: '/dpresult',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Partial Results'
                },
                ncyBreadcrumb: {
                    label: 'Partial Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-final-partial-dashboard.html',
                        controller: 'DashboardResultController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            status: 'partial',
                            title: 'Partial Results'
                        }
                    }
                }
            })
            .state('dashboard-critical-result', {
                parent: 'entity',
                url: '/dcresult',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Critical Results'
                },
                ncyBreadcrumb: {
                    label: 'Critical Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-final-partial-dashboard.html',
                        controller: 'DashboardResultController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            status: 'critical',
                            title: 'Critical Results'
                        }
                    }
                }
            })
            .state('dashboard-non-perform-result', {
                parent: 'entity',
                url: '/dnonperform',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Non Perform Results'
                },
                ncyBreadcrumb: {
                    label: 'Non Perform Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-final-partial-dashboard.html',
                        controller: 'DashboardResultController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            status: 'nonperform',
                            title: 'Non Perform Results'
                        }
                    }
                }
            })
            .state('unassigned-result', {
                parent: 'lab-results',
                url: '/unassigned',
                data: {
                    authorities: [
                        ROLES.ROLE_SUPER_ADMIN,
                        ROLES.ROLE_PROGRAM_DIRECTOR,
                        ROLES.ROLE_CASE_MANAGER,
                        ROLES.ROLE_DIRECTOR_NURSE,
                        ROLES.ROLE_REGISTER_NURSE,
                        ROLES.ROLE_LICENSE_PRACTITIONER_NURSE,
                        ROLES.ROLE_MD,
                        ROLES.ROLE_CLINICAL_DIRECTOR,
                        ROLES.ROLE_PHYSICIAN_ASSISTANCE,
                        ROLES.ROLE_LAB,
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Unassigned Results'
                },
                ncyBreadcrumb: {
                    label: 'Final Results',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/patient-result/patient-result-unassigned.html',
                        controller: 'UnassignedResultController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    filterRes: function () {
                        return {
                            status: 'PARTIAL'
                        }
                    },
                }
            })
    }
})();
