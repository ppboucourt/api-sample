(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-frequency', {
            parent: 'entity',
            url: '/order-frequency',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrderFrequencies'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-frequency/order-frequencies.html',
                    controller: 'OrderFrequencyController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('order-frequency-detail', {
            parent: 'entity',
            url: '/order-frequency/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrderFrequency'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-frequency/order-frequency-detail.html',
                    controller: 'OrderFrequencyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrderFrequency', function($stateParams, OrderFrequency) {
                    return OrderFrequency.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-frequency',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-frequency-detail.edit', {
            parent: 'order-frequency-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-frequency/order-frequency-dialog.html',
                    controller: 'OrderFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderFrequency', function(OrderFrequency) {
                            return OrderFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-frequency.new', {
            parent: 'order-frequency',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-frequency/order-frequency-dialog.html',
                    controller: 'OrderFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-frequency', null, { reload: 'order-frequency' });
                }, function() {
                    $state.go('order-frequency');
                });
            }]
        })
        .state('order-frequency.edit', {
            parent: 'order-frequency',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-frequency/order-frequency-dialog.html',
                    controller: 'OrderFrequencyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderFrequency', function(OrderFrequency) {
                            return OrderFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-frequency', null, { reload: 'order-frequency' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-frequency.delete', {
            parent: 'order-frequency',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-frequency/order-frequency-delete-dialog.html',
                    controller: 'OrderFrequencyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderFrequency', function(OrderFrequency) {
                            return OrderFrequency.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-frequency', null, { reload: 'order-frequency' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
