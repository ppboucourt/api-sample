(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider', 'ROLES'];

    function stateConfig($stateProvider, ROLES) {
        $stateProvider
            .state('group-session', {
                parent: 'entity',
                url: '/group-session',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'GroupSessions'
                },
                ncyBreadcrumb: {
                    label: 'Group Session',
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/group-session/group-sessions.html',
                        controller: 'GroupSessionController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                }
            })
            .state('group-session-detail', {
                parent: 'entity',
                url: '/group-session/{id}',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE],
                    pageTitle: 'GroupSession'
                },
                ncyBreadcrumb: {
                    label: 'Details',
                    parent: 'group-session'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/group-session/group-session-detail.html',
                        controller: 'GroupSessionDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'GroupSession', function($stateParams, GroupSession) {
                        return GroupSession.get({id : $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        var currentStateData = {
                            name: $state.current.name || 'group-session',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('group-session-detail.edit', {
                parent: 'group-session-detail',
                url: '/detail/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session/group-session-new.html',
                        controller: 'GroupSessionDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['GroupSession', function(GroupSession) {
                                return GroupSession.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('^', {}, { reload: false });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('group-session.new', {
                parent: 'group-session',
                url: '/new',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                ncyBreadcrumb: {
                    label: 'New',
                    parent: 'group-session'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/group-session/group-session-new.html',
                        controller: 'GroupSessionNewController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    entity:  [function () {
                        return {
                            name: null,
                            dayWeek: null,
                            startTime: null,
                            endTime: null,
                            status: 'ACT',
                            enabled: false,
                            billable: false,
                            id: null
                        };
                    }]

                }
            })
            .state('group-session.edit', {
                parent: 'group-session',
                url: '/{id}/edit',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session/group-session-new.html',
                        controller: 'GroupSessionDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            entity: ['GroupSession', function(GroupSession) {
                                return GroupSession.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('group-session', null, { reload: 'group-session' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('group-session.delete', {
                parent: 'group-session',
                url: '/{id}/delete',
                data: {
                    authorities: [ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE]
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/group-session/group-session-delete-dialog.html',
                        controller: 'GroupSessionDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['GroupSession', function(GroupSession) {
                                return GroupSession.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('group-session', null, { reload: 'group-session' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }

})();
