(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-action', {
            parent: 'entity',
            url: '/chart-to-action',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToActions'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-action/chart-to-actions.html',
                    controller: 'ChartToActionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-action-detail', {
            parent: 'entity',
            url: '/chart-to-action/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToAction'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-action/chart-to-action-detail.html',
                    controller: 'ChartToActionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToAction', function($stateParams, ChartToAction) {
                    return ChartToAction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-action',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-action-detail.edit', {
            parent: 'chart-to-action-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-action/chart-to-action-dialog.html',
                    controller: 'ChartToActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToAction', function(ChartToAction) {
                            return ChartToAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-action.new', {
            parent: 'chart-to-action',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-action/chart-to-action-dialog.html',
                    controller: 'ChartToActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                status: null,
                                notes: null,
                                archived: null,
                                date_prescription: null,
                                taken: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-to-action', null, { reload: 'chart-to-action' });
                }, function() {
                    $state.go('chart-to-action');
                });
            }]
        })
        .state('chart-to-action.edit', {
            parent: 'chart-to-action',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-action/chart-to-action-dialog.html',
                    controller: 'ChartToActionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToAction', function(ChartToAction) {
                            return ChartToAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-action', null, { reload: 'chart-to-action' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-action.delete', {
            parent: 'chart-to-action',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-action/chart-to-action-delete-dialog.html',
                    controller: 'ChartToActionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToAction', function(ChartToAction) {
                            return ChartToAction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-action', null, { reload: 'chart-to-action' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
