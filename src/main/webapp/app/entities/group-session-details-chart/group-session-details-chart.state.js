(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('group-session-details-chart', {
            parent: 'entity',
            url: '/group-session-details-chart',
            data: {
                authorities: [
                    'ROLE_USER'
                ],
                pageTitle: 'GroupSessionDetailsCharts'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-charts.html',
                    controller: 'GroupSessionDetailsChartController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('group-session-details-chart-detail', {
            parent: 'entity',
            url: '/group-session-details-chart/{id}',
            data: {
                authorities: [
                    'ROLE_USER'
                ],
                pageTitle: 'GroupSessionDetailsChart'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-detail.html',
                    controller: 'GroupSessionDetailsChartDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'GroupSessionDetailsChart', function($stateParams, GroupSessionDetailsChart) {
                    return GroupSessionDetailsChart.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'group-session-details-chart',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('group-session-details-chart-detail.edit', {
            parent: 'group-session-details-chart-detail',
            url: '/detail/edit',
            data: {
                authorities: [
                    'ROLE_USER'
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-dialog.html',
                    controller: 'GroupSessionDetailsChartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupSessionDetailsChart', function(GroupSessionDetailsChart) {
                            return GroupSessionDetailsChart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('group-session-details-chart.new', {
            parent: 'group-session-details-chart',
            url: '/new',
            data: {
                authorities: ['ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-dialog.html',
                    controller: 'GroupSessionDetailsChartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                notes: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('group-session-details-chart', null, { reload: 'group-session-details-chart' });
                }, function() {
                    $state.go('group-session-details-chart');
                });
            }]
        })
        .state('group-session-details-chart.edit', {
            parent: 'group-session-details-chart',
            url: '/{id}/edit',
            data: {
                authorities: [
                'ROLE_USER'
                ]
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-dialog.html',
                    controller: 'GroupSessionDetailsChartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['GroupSessionDetailsChart', function(GroupSessionDetailsChart) {
                            return GroupSessionDetailsChart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-session-details-chart', null, { reload: 'group-session-details-chart' });
                }, function() {
                    $state.go('^');
                });
            }]
        })

        .state('group-session-details-chart.delete', {
            parent: 'group-session-details-chart',
            url: '/{id}/delete2',
            data: {
                authorities: ['ROLES.ROLE_SUPER_ADMIN, ROLES.ROLE_PROGRAM_DIRECTOR, ROLES.ROLE_CASE_MANAGER, ROLES.ROLE_DIRECTOR_NURSE, ROLES.ROLE_REGISTER_NURSE, ROLES.ROLE_LICENSE_PRACTITIONER_NURSE, ROLES.ROLE_PRIMARY_THERAPIST, ROLES.ROLE_OTHER_THERAPIST, ROLES.ROLE_BHT']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-delete-dialog.html',
                    controller: 'GroupSessionDetailsChartDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['GroupSessionDetailsChart', function(GroupSessionDetailsChart) {
                            return GroupSessionDetailsChart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('group-session-details-chart', null, { reload: 'group-session-details-chart' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
