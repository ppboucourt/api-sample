(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('archive-patients', {
            parent: 'entity',
            url: '/archive-patients',
            data: {
                authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_CLINICAL_DIRECTOR, ROLES.ROLE_PHYSICIAN_ASSISTANCE, ROLES.ROLE_MD, ROLES.ROLE_BHT, ROLES.ROLE_LAB],
                pageTitle: 'Archive Patients'
            },
            ncyBreadcrumb: {
                label: 'Archive Patient'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archive-patients/archive-patients.html',
                    controller: 'ArchivePatientController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                chart : function() {
                    return {
                        firstName: null,
                        lastName: null,
                        mrNo: null,
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
