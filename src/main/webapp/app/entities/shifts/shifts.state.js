(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('shifts', {
                parent: 'entity',
                url: '/shifts',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT],
                    pageTitle: 'Shifts'
                },
                ncyBreadcrumb: {
                    label: 'Shifts'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/shifts/shifts.html',
                        controller: 'ShiftsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {}
            })
            .state('shifts-detail', {
                parent: 'entity',
                url: '/shifts/detail/{id}/{fromDetail}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT],
                    pageTitle: 'Shifts'
                },
                ncyBreadcrumb: {
                    label: 'Detail',
                    parent: 'shifts'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/shifts/shifts-detail.html',
                        controller: 'ShiftsDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Shifts', function ($stateParams, Shifts) {
                        return Shifts.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'shifts',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('shifts.new', {
                parent: 'shifts',
                url: '/new',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT]
                },
                ncyBreadcrumb: {
                    label: 'New',
                    parent: 'shifts'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/shifts/shifts-new.html',
                        controller: 'ShiftsNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: [function () {
                        return {
                            description: null
                        };
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'shifts',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }

            })
            .state('shifts-edit', {
                parent: 'entity',
                url: '/shifts/{id}/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT],
                    pageTitle: 'Shifts'
                },
                ncyBreadcrumb: {
                    label: 'Edit',
                    parent: 'shifts'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/shifts/shifts-edit.html',
                        controller: 'ShiftsEditController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Shifts', function ($stateParams, Shifts) {
                        return Shifts.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'shifts',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('shifts.delete', {
                parent: 'shifts',
                url: '/{id}/delete',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_BHT]
                },
                ncyBreadcrumb: {
                    label: 'Delete',
                    parent: 'shifts'
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/shifts/shifts-delete-dialog.html',
                        controller: 'ShiftsDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['Shifts', function (Shifts) {
                                return Shifts.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('shifts', null, {reload: 'shifts'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            });
    }

})();
