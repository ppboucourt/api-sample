(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('today-group-session', {
                parent: 'entity',
                url: '/today-group-session',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT],
                    pageTitle: 'Group Session'
                },
                ncyBreadcrumb: {
                    label: 'Group Session Today'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/schedule/today-group-session.html',
                        controller: 'ScheduleController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            }) ;
    }

})();
