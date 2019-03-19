(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        .state('lab-requisitions', {
            parent: 'entity',
            url: '/lab-requisitions',
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
                pageTitle: 'Lab Requisitions'
            },
            ncyBreadcrumb: {
                label: 'Lab Requisitions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/patient-schedule/patient-order-schedule.html',
                    controller: 'PatientOrderScheduleController',
                    controllerAs: 'vm'
                },
                //Inline edit order
                'edit-order@lab-requisitions': {
                    templateUrl: 'app/entities/patient-order/patient-order-update.html',
                    controller: 'PatientOrderUpdateController',
                    controllerAs: 'vm'
                },
                'edit-order-tests@lab-requisitions': {
                    templateUrl: 'app/entities/patient-order-test/patient-order-tests.html',
                    controller: 'PatientOrderTestController',
                    controllerAs: 'vm',
                    resolve: {
                        canCancel: function () {
                            return true;
                        }
                    }
                },
            },
            resolve: {
                hasPrinter: ['DYMO', function (DYMO) {
                    return DYMO.loadPrinters();
                }],
                chart: function () {
                    return null;
                }
            }
        });
    }
})();
