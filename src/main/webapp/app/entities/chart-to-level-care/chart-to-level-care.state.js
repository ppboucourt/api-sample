(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-level-care', {
            parent: 'entity',
            url: '/chart-to-level-care',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToLevelCares'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-cares.html',
                    controller: 'ChartToLevelCareController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-level-care-detail', {
            parent: 'entity',
            url: '/chart-to-level-care/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToLevelCare'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-care-detail.html',
                    controller: 'ChartToLevelCareDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToLevelCare', function($stateParams, ChartToLevelCare) {
                    return ChartToLevelCare.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-level-care',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-level-care-detail.edit', {
            parent: 'chart-to-level-care-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-care-dialog.html',
                    controller: 'ChartToLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToLevelCare', function(ChartToLevelCare) {
                            return ChartToLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-level-care.new', {
            parent: 'chart-to-level-care',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-care-dialog.html',
                    controller: 'ChartToLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-to-level-care', null, { reload: 'chart-to-level-care' });
                }, function() {
                    $state.go('chart-to-level-care');
                });
            }]
        })
        .state('chart-to-level-care.edit', {
            parent: 'chart-to-level-care',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-care-dialog.html',
                    controller: 'ChartToLevelCareDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToLevelCare', function(ChartToLevelCare) {
                            return ChartToLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-level-care', null, { reload: 'chart-to-level-care' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-level-care.delete', {
            parent: 'chart-to-level-care',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-level-care/chart-to-level-care-delete-dialog.html',
                    controller: 'ChartToLevelCareDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToLevelCare', function(ChartToLevelCare) {
                            return ChartToLevelCare.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-level-care', null, { reload: 'chart-to-level-care' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
