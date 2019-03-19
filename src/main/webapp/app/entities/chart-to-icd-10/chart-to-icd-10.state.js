(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-icd-10', {
            parent: 'entity',
            url: '/chart-to-icd-10',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToIcd10S'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-s.html',
                    controller: 'ChartToIcd10Controller',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-icd-10-detail', {
            parent: 'entity',
            url: '/chart-to-icd-10/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToIcd10'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-detail.html',
                    controller: 'ChartToIcd10DetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToIcd10', function($stateParams, ChartToIcd10) {
                    return ChartToIcd10.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-icd-10',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-icd-10-detail.edit', {
            parent: 'chart-to-icd-10-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-dialog.html',
                    controller: 'ChartToIcd10DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToIcd10', function(ChartToIcd10) {
                            return ChartToIcd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-icd-10.new', {
            parent: 'chart-to-icd-10',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-dialog.html',
                    controller: 'ChartToIcd10DialogController',
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
                    $state.go('chart-to-icd-10', null, { reload: 'chart-to-icd-10' });
                }, function() {
                    $state.go('chart-to-icd-10');
                });
            }]
        })
        .state('chart-to-icd-10.edit', {
            parent: 'chart-to-icd-10',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-dialog.html',
                    controller: 'ChartToIcd10DialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToIcd10', function(ChartToIcd10) {
                            return ChartToIcd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-icd-10', null, { reload: 'chart-to-icd-10' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-icd-10.delete', {
            parent: 'chart-to-icd-10',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-icd-10/chart-to-icd-10-delete-dialog.html',
                    controller: 'ChartToIcd10DeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToIcd10', function(ChartToIcd10) {
                            return ChartToIcd10.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-icd-10', null, { reload: 'chart-to-icd-10' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
