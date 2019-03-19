(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reports', {
            parent: 'entity',
            url: '/reports',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Reports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reports/reports.html',
                    controller: 'ReportsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('reports-detail', {
            parent: 'entity',
            url: '/reports/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Reports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reports/reports-detail.html',
                    controller: 'ReportsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Reports', function($stateParams, Reports) {
                    return Reports.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reports',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reports-detail.edit', {
            parent: 'reports-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reports/reports-dialog.html',
                    controller: 'ReportsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reports', function(Reports) {
                            return Reports.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reports.new', {
            parent: 'reports',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reports/reports-dialog.html',
                    controller: 'ReportsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                report_name: null,
                                template: null,
                                sort_direction: null,
                                date_range: null,
                                start_date: null,
                                end_date: null,
                                criteria: null,
                                selection: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reports', null, { reload: 'reports' });
                }, function() {
                    $state.go('reports');
                });
            }]
        })
        .state('reports.edit', {
            parent: 'reports',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reports/reports-dialog.html',
                    controller: 'ReportsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Reports', function(Reports) {
                            return Reports.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reports', null, { reload: 'reports' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reports.delete', {
            parent: 'reports',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reports/reports-delete-dialog.html',
                    controller: 'ReportsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Reports', function(Reports) {
                            return Reports.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reports', null, { reload: 'reports' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
