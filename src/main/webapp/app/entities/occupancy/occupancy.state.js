(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('occupancy', {
                parent: 'entity',
                url: '/occupancy',
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
                        ROLES.ROLE_BHT
                    ],
                    pageTitle: 'Occupancy'
                },
                ncyBreadcrumb: {
                    label: 'Occupancy'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/occupancy/occupancy.html',
                        controller: 'OccupancyController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            // .state('occupancy.edit', {
            //     parent: 'occupancy',
            //     url: '/{id}/edit',
            //     data: {
            //         authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
            //     },
            //     onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
            //         $uibModal.open({
            //             templateUrl: 'app/entities/occupancy/occupancy-edit.html',
            //             controller: 'OccupancyEditController',
            //             controllerAs: 'vm',
            //             animation: true,
            //             backdrop: 'static',
            //             keyboard: false,
            //             size: 'lg',
            //             resolve: {
            //                 entity: ['Bed', function(Bed) {
            //                     return Bed.getWithChart({id : $stateParams.id}).$promise;
            //                 }]
            //             }
            //         }).result.then(function() {
            //             $state.go('occupancy', null, { reload: 'occupancy' });
            //         }, function() {
            //             $state.go('^');
            //         });
            //     }]
            // })
            .state('occupancy.assign-patient', {
                parent: 'occupancy',
                url: '/{id}/assign-patient',
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
                        ROLES.ROLE_BHT
                    ]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/occupancy/assign-patient.html',
                        controller: 'OccupancyAssignController',
                        controllerAs: 'vm',
                        animation: true,
                        backdrop: 'static',
                        keyboard: false,
                        size: 'lg',
                        resolve: {
                            entity: ['Chart', function(Chart) {
                                return Chart.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('occupancy', null, { reload: 'occupancy' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }

})();
