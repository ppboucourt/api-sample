(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-type', {
            parent: 'entity',
            url: '/order-type',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Order_types'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-type/order-types.html',
                    controller: 'Order_typeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('order-type-detail', {
            parent: 'entity',
            url: '/order-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Order_type'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-type/order-type-detail.html',
                    controller: 'Order_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Order_type', function($stateParams, Order_type) {
                    return Order_type.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-type',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-type-detail.edit', {
            parent: 'order-type-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-type/order-type-dialog.html',
                    controller: 'Order_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Order_type', function(Order_type) {
                            return Order_type.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-type.new', {
            parent: 'order-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-type/order-type-dialog.html',
                    controller: 'Order_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                order_type_name: null,
                                status: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-type', null, { reload: 'order-type' });
                }, function() {
                    $state.go('order-type');
                });
            }]
        })
        .state('order-type.edit', {
            parent: 'order-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-type/order-type-dialog.html',
                    controller: 'Order_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Order_type', function(Order_type) {
                            return Order_type.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-type', null, { reload: 'order-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-type.delete', {
            parent: 'order-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-type/order-type-delete-dialog.html',
                    controller: 'Order_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Order_type', function(Order_type) {
                            return Order_type.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-type', null, { reload: 'order-type' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
