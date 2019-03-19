(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
        /*.state('group-session-details', {
         parent: 'entity',
         url: '/group-session-details',
         data: {
         authorities: [ROLE_USER'],
         pageTitle: 'GroupSessionDetails'
         },

         views: {
         'content@': {
         templateUrl: 'app/entities/group-session-details/group-session-details.html',
         controller: 'GroupSessionDetailsController',
         controllerAs: 'vm'
         }
         },
         resolve: {
         }
         })*/

            .state('group-session-details-new', {
                parent: 'today-group-session',
                url: '/group-session-details/new/{gid}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', 'GroupSession', 'CoreService', function ($stateParams, $state, $uibModal, GroupSession, CoreService) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session-details/group-session-details-new.html',
                        controller: 'GroupSessionDetailsNewController',
                        controllerAs: 'vm',
                        size: 'lg',
                        backdrop:false,
                        resolve: {
                            entity: ['GroupSession', 'CoreService', function (GroupSession, CoreService) {
                                return {
                                    leaderEmployee: CoreService.getCurrentEmployee(),
                                    start: null,
                                    finalized: null,
                                    topic: null,
                                    id: null
                                };
                            }]
                        }

                    }).result.then(function () {
                        $state.go('today-group-session', null, {reload: 'today-group-session'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })

            .state('group-session-details-detail', {
                parent: 'today-group-session',
                url: '/group-session-details/{id}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT],
                    pageTitle: 'GroupSessionDetails'
                },
                ncyBreadcrumb: {
                    label: 'Details'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/group-session-details/group-session-details-detail.html',
                        controller: 'GroupSessionDetailsDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GroupSessionDetails', function ($stateParams, GroupSessionDetails) {
                        return GroupSessionDetails.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'today-group-session',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })

            .state('group-session-details-chart-add', {
                parent: 'group-session-details-detail',
                url: '/{id}/chart',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session-details/group-session-details-chart-new.html',
                        controller: 'GroupSessionDetailsChartAddController',
                        controllerAs: 'vm',
                        size: 'lg',
                        backdrop:false,
                        resolve: {
                            entity: ['GroupSessionDetails', function (GroupSessionDetails) {
                                return GroupSessionDetails.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('group-session-details-detail', null, {reload: 'group-session-details-detail'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })


            .state('group-session-details-sign', {
                parent: 'group-session-details-detail',
                url: '/{id}/sign',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session-details/group-session-details-sign-dialog.html',
                        controller: 'GroupSessionDetailsSignController',
                        controllerAs: 'vm',
                        size: 'sm',
                        backdrop:false,
                        resolve: {
                            entity: ['GroupSessionDetails', function (GroupSessionDetails) {
                                return GroupSessionDetails.get({id: $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('group-session-details-detail', null, {reload: 'group-session-details-detail'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })

            .state('group-session-details-detail.delete', {
                parent: 'group-session-details-detail',
                url: '/{gdid}/delete',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-delete-dialog.html',
                        controller: 'GroupSessionDetailsChartDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        backdrop:false,
                        resolve: {
                            entity: ['GroupSessionDetailsChart', function (GroupSessionDetailsChart) {
                                return GroupSessionDetailsChart.get({id: $stateParams.gdid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('group-session-details-detail', null, {reload: 'group-session-details-detail'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })

            .state('group-session-details-detail.addnote', {
                parent: 'group-session-details-detail',
                url: '/{gdid}/addnote',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-addnote-dialog.html',
                        controller: 'GroupSessionDetailsChartAddNoteController',
                        controllerAs: 'vm',
                        size: 'md',
                        backdrop:false,
                        resolve: {
                            entity: ['GroupSessionDetailsChart', function (GroupSessionDetailsChart) {
                                return GroupSessionDetailsChart.get({id: $stateParams.gdid}).$promise;
                            }]
                        }
                    }).result.then(function () {
                        $state.go('group-session-details-detail', null, {reload: 'group-session-details-detail'});
                    }, function () {
                        $state.go('^');
                    });
                }]
            })



    }

})();
