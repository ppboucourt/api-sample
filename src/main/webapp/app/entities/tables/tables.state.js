(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tables', {
            parent: 'entity',
            url: '/tables',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tables'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tables/tables.html',
                    controller: 'TablesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('tables-detail', {
            parent: 'entity',
            url: '/tables/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Tables'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tables/tables-detail.html',
                    controller: 'TablesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Tables', function($stateParams, Tables) {
                    return Tables.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tables',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tables-detail.edit', {
            parent: 'tables-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tables/tables-dialog.html',
                    controller: 'TablesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tables', function(Tables) {
                            return Tables.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tables.new', {
            parent: 'tables',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tables/tables-dialog.html',
                    controller: 'TablesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                table_name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tables', null, { reload: 'tables' });
                }, function() {
                    $state.go('tables');
                });
            }]
        })
        .state('tables.edit', {
            parent: 'tables',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tables/tables-dialog.html',
                    controller: 'TablesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tables', function(Tables) {
                            return Tables.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tables', null, { reload: 'tables' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tables.delete', {
            parent: 'tables',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tables/tables-delete-dialog.html',
                    controller: 'TablesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tables', function(Tables) {
                            return Tables.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tables', null, { reload: 'tables' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
