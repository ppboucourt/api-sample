(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('chart-to-medications', {
            parent: 'entity',
            url: '/chart-to-medications',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToMedications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications.html',
                    controller: 'ChartToMedicationsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('chart-to-medications-detail', {
            parent: 'entity',
            url: '/chart-to-medications/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ChartToMedications'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications-detail.html',
                    controller: 'ChartToMedicationsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ChartToMedications', function($stateParams, ChartToMedications) {
                    return ChartToMedications.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'chart-to-medications',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('chart-to-medications-detail.edit', {
            parent: 'chart-to-medications-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications-dialog.html',
                    controller: 'ChartToMedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToMedications', function(ChartToMedications) {
                            return ChartToMedications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-medications.new', {
            parent: 'chart-to-medications',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications-dialog.html',
                    controller: 'ChartToMedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                taken: null,
                                notes: null,
                                status: null,
                                date_prescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('chart-to-medications', null, { reload: 'chart-to-medications' });
                }, function() {
                    $state.go('chart-to-medications');
                });
            }]
        })
        .state('chart-to-medications.edit', {
            parent: 'chart-to-medications',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications-dialog.html',
                    controller: 'ChartToMedicationsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ChartToMedications', function(ChartToMedications) {
                            return ChartToMedications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-medications', null, { reload: 'chart-to-medications' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('chart-to-medications.delete', {
            parent: 'chart-to-medications',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/chart-to-medications/chart-to-medications-delete-dialog.html',
                    controller: 'ChartToMedicationsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ChartToMedications', function(ChartToMedications) {
                            return ChartToMedications.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('chart-to-medications', null, { reload: 'chart-to-medications' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
