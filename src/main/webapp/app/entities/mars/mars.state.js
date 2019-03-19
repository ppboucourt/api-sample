(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('patient-mars', {
                parent: 'patient',
                url: '/mars',
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
                    pageTitle: 'Patient-Mars'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/mars/mars-patient.html',
                        controller: 'MarsPatientController',
                        controllerAs: 'vm'
                    },
                    'vitals@patient-mars': {
                        templateUrl: 'app/entities/vitals/vitals.html',
                        controller: 'VitalsController',
                        controllerAs: 'vm'
                    },
                    'glucoses@patient-mars': {
                        templateUrl: 'app/entities/glucose/glucoses.html',
                        controller: 'GlucoseController',
                        controllerAs: 'vm'
                    },
                    'weight@patient-mars': {
                        templateUrl: 'app/entities/weight/weights.html',
                        controller: 'WeightController',
                        controllerAs: 'vm'
                    },
                    'actions@patient-mars': {
                        templateUrl: 'app/entities/patient-schedule/patient-actions-schedule.html',
                        controller: 'ActionsScheduleController',
                        controllerAs: 'vm'
                    },
                    'medications@patient-mars': {
                        templateUrl: 'app/entities/patient-schedule/patient-medications-schedule.html',
                        controller: 'MedicationsScheduleController',
                        controllerAs: 'vm'
                    },
                    'result@patient-mars': {
                        templateUrl: 'app/entities/patient-result/patient-result-by-patient.html',
                        controller: 'PatientResultByPatientController',
                        controllerAs: 'vm'
                    }
                }
            });
    }

})();
