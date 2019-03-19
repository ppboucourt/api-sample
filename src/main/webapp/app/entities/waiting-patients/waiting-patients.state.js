(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('waiting-patients', {
            parent: 'entity',
            url: '/waiting-patients',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Waiting Patients'
            },
            ncyBreadcrumb: {
                label: 'Waiting Patient'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/waiting-patients/waiting-patients.html',
                    controller: 'WaitingPatientController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                chart : function() {
                    return {
                        first_name: null,
                        last_name: null,
                        mr_no: null,
                        dischargeDateFrom: null,
                        dischargeDateTo: null,
                        admissionDateFrom: null,
                        admissionDateTo: null,
                        typePatientProgram: {}
                    }
                }
            }
        }).state('waiting-details', {
            parent: 'entity',
            url: '/waiting-details',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Waiting Details'
            },
            ncyBreadcrumb: {
                label: 'Waiting Details'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/waiting-patients/waiting-details.html',
                    controller: 'WaitingDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                chart : function() {
                    return {
                        first_name: null,
                        last_name: null,
                        mr_no: null,
                        dischargeDateFrom: null,
                        dischargeDateTo: null,
                        admissionDateFrom: null,
                        admissionDateTo: null,
                        typePatientProgram: {}
                    }
                }
            }
        });


    }

})();
