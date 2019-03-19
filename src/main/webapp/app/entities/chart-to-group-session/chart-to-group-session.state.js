(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-group-session', {
            parent: 'entity',
            url: '/chart-to-group-session',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToGroupSessions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-sessions.html',
                    controller: 'ChartToGroupSessionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-group-session-detail', {
            parent: 'entity',
            url: '/chart-to-group-session/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToGroupSession'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-session-detail.html',
                    controller: 'ChartToGroupSessionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToGroupSession', function($stateParams, ChartToGroupSession) {
                    return ChartToGroupSession.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-group-session',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-group-session-detail.edit', {
            parent: 'chart-to-group-session-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-session-dialog.html',
                    controller: 'ChartToGroupSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToGroupSession', function(ChartToGroupSession) {
                            return ChartToGroupSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-group-session.new', {
            parent: 'chart-to-group-session',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-session-dialog.html',
                    controller: 'ChartToGroupSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-to-group-session', null, { reload: 'chart-to-group-session' });
                }, function() {
                    $state.go('chart-to-group-session');
                });
            }]
        })
        .state('chart-to-group-session.edit', {
            parent: 'chart-to-group-session',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-session-dialog.html',
                    controller: 'ChartToGroupSessionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToGroupSession', function(ChartToGroupSession) {
                            return ChartToGroupSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-group-session', null, { reload: 'chart-to-group-session' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-group-session.delete', {
            parent: 'chart-to-group-session',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-group-session/chart-to-group-session-delete-dialog.html',
                    controller: 'ChartToGroupSessionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToGroupSession', function(ChartToGroupSession) {
                            return ChartToGroupSession.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-group-session', null, { reload: 'chart-to-group-session' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
